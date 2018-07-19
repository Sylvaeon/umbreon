package me.sylvaeon.umbreon;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import me.sylvaeon.umbreon.command.Command;
import me.sylvaeon.umbreon.command.Commands;
import me.sylvaeon.umbreon.music.GuildMusicManager;
import me.sylvaeon.umbreon.rpg.crafting.Recipes;
import me.sylvaeon.umbreon.rpg.item.Items;
import me.sylvaeon.umbreon.rpg.world.World;
import me.sylvaeon.umbreon.rpg.world.entity.player.Players;
import me.sylvaeon.umbreon.util.DiscordTextUtil;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.Presence;

import javax.security.auth.login.LoginException;
import java.util.HashMap;
import java.util.Map;

public final class Umbreon extends ListenerAdapter {
    private static final String TOKEN = "NDQyODE5NDMzNTQ3ODI1MTUy.DdI0wg.k76KQG6xTf2Q22NdAW5_7_zU-oY";
    private static JDA jda;
    private static AudioPlayerManager playerManager;
   	private static Map<Long, GuildMusicManager> musicManagers;
   	
   	private static Map<Long, String> welcomeMessages;
   	private static Map<Long, TextChannel> welcomeChannels;
   	
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
		TextChannel textChannel = welcomeChannels.get(guildId);
		if(textChannel == null) {
			textChannel = guild.getDefaultChannel();
			welcomeChannels.put(guildId, textChannel);
		}
		return textChannel;
    }
    
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        processMessage(event.getAuthor(), event.getMessage(), event.getMember().hasPermission(Permission.ADMINISTRATOR));
    }
	
	@Override
	public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
		processMessage(event.getAuthor(), event.getMessage(), false);
	}
	
	private void processMessage(User user, Message message, boolean isAdmin) {
    	String messageString = message.getContentRaw();
    	TextChannel channel = message.getTextChannel();
		if (!user.isBot()) {
			if (messageString.startsWith("$")) {
				messageString = messageString.substring(1);
				String[] split = messageString.split(" ");
				Command command = Commands.getCommand(split[0]);
				message.delete().complete();
				if (command != null) {
					if (Commands.canExecute(user, command, isAdmin)) {
						String[] args = new String[split.length - 1];
						if (args.length != 0) {
							for (int i = 1; i < split.length; i++) {
								args[i - 1] = split[i];
							}
						}
						command.onCall(args, user, channel);
					} else {
						channel.sendMessage("Error: Invalid permissions").queue();
					}
				} else {
					channel.sendMessage("Error: Not a valid command! Type $help for a list of commands").queue();
				}
			}
			if (messageString.toLowerCase().contains(badword)) {
				PrivateChannel pc = user.openPrivateChannel().complete();
				pc.sendMessage("Watch your language :<\n~Cynthia").queue();
				if(isAdmin) {
					DiscordTextUtil.removeMessage(message);
				}
			}
		}
	}
	
	@Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
    	Guild guild = event.getGuild();
    	User user = event.getUser();
        Players.initPlayer(event.getUser());
        getWelcomeChannel(guild).sendMessage(getWelcomeMessage(guild, user)).queue();
    }
	
	public static JDA getJda() {
		return jda;
	}

	public static AudioPlayerManager getPlayerManager() {
		return playerManager;
	}
	






































































	private static final String badword = "sylvan";
}