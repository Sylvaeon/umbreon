package me.sylvaeon.umbreon;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.sylvaeon.umbreon.command.Command;
import me.sylvaeon.umbreon.command.Commands;
import me.sylvaeon.umbreon.music.GuildMusicManager;
import me.sylvaeon.umbreon.music.command.CommandMusic;
import me.sylvaeon.umbreon.rpg.crafting.Recipes;
import me.sylvaeon.umbreon.rpg.item.Items;
import me.sylvaeon.umbreon.rpg.world.World;
import me.sylvaeon.umbreon.rpg.world.entity.player.Players;
import me.sylvaeon.umbreon.util.DiscordTextUtil;
import me.sylvaeon.umbreon.util.DiscordVoiceUtil;
import me.sylvaeon.umbreon.util.Utility;
import net.dv8tion.jda.core.*;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.Presence;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Umbreon extends ListenerAdapter {
    private static final String TOKEN = "NDQyODE5NDMzNTQ3ODI1MTUy.DdI0wg.k76KQG6xTf2Q22NdAW5_7_zU-oY";
    private static JDA jda;
    private static AudioPlayerManager playerManager;
   	private static Map<Long, GuildMusicManager> musicManagers;
   	
   	private static Map<Long, String> welcomeMessages;
   	private static Map<Long, Long> welcomeChannels;
   	
   	private static Map<Long, Long> musicMessages;
   	private static Map<Long, Long> musicChannels;
   	
   	public static User CYNTHIA, UMBREON;

    public static void main(String[] args) throws LoginException, InterruptedException {
        jda = new JDABuilder(AccountType.BOT).setToken(TOKEN).buildBlocking();
        jda.addEventListener(new Umbreon());
        Presence presence = jda.getPresence();
        presence.setGame(Game.playing("🖤$help💛"));
        CYNTHIA = jda.getUserById(199523552850870272L);
        UMBREON = jda.getSelfUser();
        musicManagers = new HashMap<>();
        playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(playerManager);
        AudioSourceManagers.registerLocalSource(playerManager);
	
        welcomeMessages = new HashMap<>();
        welcomeChannels = new HashMap<>();
        
        musicMessages = new HashMap<>();
        musicChannels = new HashMap<>();
        
		Items.init();
        World.init();
		Recipes.init();
		Players.init();
		Commands.init();
        Players.updatePeople();

    }

	public synchronized static void quit() {
		Players.close();
		World.close();
		try {
			Object lock = new Object();
			synchronized (lock) {
				lock.wait(1000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Umbreon.jda.shutdown();
	}
	
    public static synchronized GuildMusicManager getGuildAudioPlayer(Guild guild) {
        long guildId = guild.getIdLong();
        GuildMusicManager musicManager = musicManagers.get(guildId);

        if (musicManager == null) {
            musicManager = new GuildMusicManager(playerManager);
            musicManagers.put(guildId, musicManager);
        }
	
		guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

        return musicManager;
    }

    public static String getWelcomeMessage(Guild guild, User user) {
    	long guildId = guild.getIdLong();
    	String message = welcomeMessages.get(guildId);
    	if(message == null) {
    		message = "Welcome to %g, %u!";
    		welcomeMessages.put(guildId, message);
	    }
	    message = message.replaceAll("\\%g", guild.getName());
    	message = message.replaceAll("\\%u", user.getName());
    	return message;
    }
    
    public static TextChannel getWelcomeChannel(Guild guild) {
	    long guildId = guild.getIdLong();
		Long channelId = welcomeChannels.get(guildId);
		if(channelId == null) {
			channelId = guild.getDefaultChannel().getIdLong();
			welcomeChannels.put(guildId, channelId);
		}
		return guild.getTextChannelById(channelId);
    }
    
    public static TextChannel getMusicChannel(Guild guild) {
	    long guildId = guild.getIdLong();
	    Long channelId = musicChannels.get(guildId);
	    if(channelId == null) {
			return null;
	    }
	    return guild.getTextChannelById(channelId);
    }
    
    public static Message getMusicMessage(Guild guild) {
    	long guildId = guild.getIdLong();
    	long messageId = musicMessages.get(guildId);
    	return getMusicChannel(guild).getMessageById(messageId).complete();
    }
    
    public static TextChannel initMusicChannel(Guild guild, String channelName) {
        TextChannel channel = (TextChannel) guild.getController()
	        .createTextChannel(channelName)
	        .addPermissionOverride(guild.getPublicRole(), 0, Permission.getRaw(Permission.MESSAGE_MANAGE, Permission.MESSAGE_READ))
	        .complete();
        musicChannels.put(guild.getIdLong(), channel.getIdLong());
	    EmbedBuilder embedBuilder = musicQueueEmbedBuilder(guild);
        Message message = DiscordTextUtil.sendMessage(channel, embedBuilder.build());
        musicMessages.put(guild.getIdLong(), message.getIdLong());
        return channel;
    }
    
    public static EmbedBuilder musicQueueEmbedBuilder(Guild guild) {
	    AudioTrack[] tracks = DiscordVoiceUtil.getAudioTrackArray(guild);
	    String authorText = guild.getName();
	    String titleText = "Music Queue (" + tracks.length + ")";
	    String footerText = null;
	    if(tracks.length > 10) {
	    	footerText = (tracks.length - 10) + " more tracks in queue";
	    }
	    List<String> fieldNames = new ArrayList<>();
	    List<String> fieldValues = new ArrayList<>();
	    AudioTrack audioTrack;
	    for(int i = 0; i < 10 && i < tracks.length; i++) {
	    	audioTrack = tracks[i];
	    	String prefix = i + ": ";
	    	if(i == 0) {
	    		prefix = "Now Playing: ";
		    }
	    	fieldNames.add(prefix + audioTrack.getInfo().title + "[" + Utility.milisAsTimeStamp(audioTrack.getDuration()) + "]");
	    	fieldValues.add("by " + audioTrack.getInfo().author);
	    }
	    if(tracks.length == 0) {
	    	fieldNames.add("No songs in queue");
	    	fieldValues.add("Queue songs with $play");
	    }
	    return DiscordTextUtil.createEmbedMessage(
		    Color.MAGENTA,
		    authorText,
		    null,
		    titleText,
		    null,
		    fieldNames,
		    fieldValues,
		    footerText,
		    null
	    );
    }
    
    public static void updateMusicMessage(Guild guild) {
    	DiscordTextUtil.editMessage(getMusicMessage(guild), musicQueueEmbedBuilder(guild).build());
    }
    
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        processMessage(event.getAuthor(), event.getMessage(), event.getMember().hasPermission(Permission.ADMINISTRATOR), true);
    }
	
	@Override
	public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
		processMessage(event.getAuthor(), event.getMessage(), false, false);
	}
	
	private void processMessage(User user, Message message, boolean isAdmin, boolean inGuild) {
    	String messageString = message.getContentRaw();
    	TextChannel channel = message.getTextChannel();
		if (!user.isBot()) {
			if (messageString.startsWith("$")) {
				messageString = messageString.substring(1);
				String[] split = messageString.split(" ");
				String[] args = new String[split.length - 1];
				if (args.length != 0) {
					for (int i = 1; i < split.length; i++) {
						args[i - 1] = split[i];
					}
				}
				Command command = Commands.getCommand(split[0]);
				DiscordTextUtil.removeMessage(message);
				if (command != null) {
					if (isAdmin || !command.requiresAdmin()) {
						if(inGuild || !command.requiresGuild()) {
							if (inGuild) {
								Guild guild = message.getGuild();
								if (!(command instanceof CommandMusic) || channel == getMusicChannel(guild) || command == Commands.getCommand("create")) {
									runCommand(command, args, user, channel);
								} else {
									TextChannel musicChannel = getMusicChannel(guild);
									if (musicChannel == null) {
										DiscordTextUtil.sendMessage(channel, "You must create a music channel with $create");
									} else {
										DiscordTextUtil.sendMessage(channel, "You must use music commands in " + musicChannel.getAsMention());
									}
								}
							} else {
								runCommand(command, args, user, channel);
							}
						} else {
							DiscordTextUtil.sendMessage(channel, "Must be in a guild to use this command");
						}
					} else {
						DiscordTextUtil.sendMessage(channel, "Invalid permissions");
					}
				} else {
					DiscordTextUtil.sendMessage(channel, "Not a valid command! Type $help for a list of commands");
				}
			}
		}
	}
	
	private void runCommand(Command command, String[] args, User user, MessageChannel messageChannel) {
    	command.onCall(args, user, messageChannel);
	}
	
	@Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
    	Guild guild = event.getGuild();
    	User user = event.getUser();
        Players.initPlayer(event.getUser());
        getWelcomeChannel(guild).sendMessage(getWelcomeMessage(guild, user)).queue();
    }
	
	@Override
	public void onGuildJoin(GuildJoinEvent event) {
    	for(Member member : event.getGuild().getMembers()) {
    		Players.initPlayer(member.getUser());
	    }
	}
	
	public static JDA getJda() {
		return jda;
	}

	public static AudioPlayerManager getPlayerManager() {
		return playerManager;
	}
	
}