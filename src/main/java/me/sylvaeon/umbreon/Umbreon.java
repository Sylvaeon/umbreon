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
import me.sylvaeon.umbreon.rpg.world.entity.player.Player;
import me.sylvaeon.umbreon.rpg.world.entity.player.Players;
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

public class Umbreon extends ListenerAdapter {
    private static final String TOKEN = "NDQyODE5NDMzNTQ3ODI1MTUy.DdI0wg.k76KQG6xTf2Q22NdAW5_7_zU-oY";
    private static JDA jda;
    private static AudioPlayerManager playerManager;
   	private static Map<Long, GuildMusicManager> musicManagers;
   	private static Member CYNTHIA, UMBREON;
	private static Guild guild;
	private static Thread pulseThread;
	private static boolean pulse = true;
	private static boolean endPulse = false;
	private static Map<User, TextChannel> privateChannels;

    public static void main(String[] args) throws LoginException, InterruptedException {

        jda = new JDABuilder(AccountType.BOT).setToken(TOKEN).buildBlocking();
        jda.addEventListener(new Umbreon());
        Presence presence = jda.getPresence();
        presence.setGame(Game.playing("🖤Love You💛"));
        guild = jda.getGuildById("310572543767478272");
        CYNTHIA = guild.getMemberById(199523552850870272L);
        UMBREON = guild.getMemberById(442819433547825152L);

        musicManagers = new HashMap<>();
		privateChannels = new HashMap<>();
        playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(playerManager);
        AudioSourceManagers.registerLocalSource(playerManager);
	
		Items.init();
        World.init();
		Recipes.init();
		Players.init();
		Commands.init();
        Players.updatePeople();

        /*pulseThread = new Thread() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < Integer.MAX_VALUE; i++) {
                        if(endPulse) {
                            Color color = Utility.getPinkGradientColor(7);
							guild.getRolesByName("\uD83D\uDC51Owner", true).get(0).getManager().setColor(color).complete();
                            return;
                        } else {
                            if (i >= 14) {
                                i = 0;
                            }
                            if(pulse) {
                                Color color = Utility.getPinkGradientColor(i % 14);
								guild.getRolesByName("\uD83D\uDC51Owner", true).get(0).getManager().setColor(color).queue();
                            }
                            synchronized (this) {
                                wait(200);
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        pulseThread.start();
        System.out.println("Thread 2 Initialized");*/
    }

	public synchronized static void quit() {
		Players.close();
		pulse = false;
		endPulse = true;
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
	
    public static synchronized GuildMusicManager getGuildAudioPlayer() {
        long guildId = guild.getIdLong();
        GuildMusicManager musicManager = musicManagers.get(guildId);

        if (musicManager == null) {
            musicManager = new GuildMusicManager(playerManager);
            musicManagers.put(guildId, musicManager);
        }
	
		guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

        return musicManager;
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        processMessage(event.getMessage());
    }

	@Override
	public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
		processMessage(event.getMessage());
	}

	private void processMessage(Message message) {
    	String messageString = message.getContentRaw();
    	MessageChannel channel = message.getChannel();
    	User user = message.getAuthor();
    	Member member = message.getMember();
    	if(member == null) {
    		member = guild.getMember(user);
		}
    	Player player = Players.getPlayer(member);
		if (!member.getUser().isBot()) {
			if (messageString.startsWith("$")) {
				messageString = messageString.substring(1);
				String[] split = messageString.split(" ");
				Command command = Commands.getCommand(split[0]);
				message.delete().complete();
				if (command != null) {
					if (Commands.canExecute(member, command)) {
						String[] args = new String[split.length - 1];
						if (args.length != 0) {
							for (int i = 1; i < split.length; i++) {
								args[i - 1] = split[i];
							}
						}
						command.onCall(args, member, channel);
					} else {
						channel.sendMessage("Error: Invalid permissions").queue();
					}
				} else {
					channel.sendMessage("Error: Not a valid command! Type $help for a list of commands").queue();
				}
			}
			if (messageString.toLowerCase().contains("sylvan")) {
				PrivateChannel pc = member.getUser().openPrivateChannel().complete();
				pc.sendMessage("Please remember the information in #announcements ty <3\n~Cynthia").queue();
				message.delete().complete();
			}
		}
	}

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        Member member = event.getMember();
        Players.initPlayer(member);
    }

    public static boolean addPrivateChannel(User user) {
    	String name = "rpg-" + user.getId();
    	if(guild.getTextChannelsByName(name, true).isEmpty()) {
			guild.getController().createTextChannel(name).complete();
			TextChannel textChannel = guild.getTextChannelsByName(name, true).get(0);
			textChannel.createPermissionOverride(guild.getPublicRole()).setDeny(Permission.MESSAGE_READ, Permission.VIEW_CHANNEL).complete();
			textChannel.createPermissionOverride(guild.getMember(user)).setAllow(Permission.MESSAGE_READ, Permission.VIEW_CHANNEL).complete();
			return true;
		} else {
    		return false;
		}
	}

	public static String getTOKEN() {
		return TOKEN;
	}

	public static JDA getJda() {
		return jda;
	}

	public static void setJda(JDA jda) {
		Umbreon.jda = jda;
	}

	public static AudioPlayerManager getPlayerManager() {
		return playerManager;
	}

	public static void setPlayerManager(AudioPlayerManager playerManager) {
		Umbreon.playerManager = playerManager;
	}

	public static Map<Long, GuildMusicManager> getMusicManagers() {
		return musicManagers;
	}

	public static void setMusicManagers(Map<Long, GuildMusicManager> musicManagers) {
		Umbreon.musicManagers = musicManagers;
	}

	public static Member getCYNTHIA() {
		return CYNTHIA;
	}

	public static void setCYNTHIA(Member CYNTHIA) {
		Umbreon.CYNTHIA = CYNTHIA;
	}

	public static Member getUMBREON() {
		return UMBREON;
	}

	public static void setUMBREON(Member UMBREON) {
		Umbreon.UMBREON = UMBREON;
	}

	public static Guild getGuild() {
		return guild;
	}

	public static void setGuild(Guild guild) {
		Umbreon.guild = guild;
	}

	public static Thread getPulseThread() {
		return pulseThread;
	}

	public static void setPulseThread(Thread pulseThread) {
		Umbreon.pulseThread = pulseThread;
	}

	public static boolean isPulse() {
		return pulse;
	}

	public static void setPulse(boolean pulse) {
		Umbreon.pulse = pulse;
	}

	public static void flipPulse() {
    	Umbreon.pulse = !Umbreon.pulse;
	}

	public static boolean isEndPulse() {
		return endPulse;
	}

	public static void setEndPulse(boolean endPulse) {
		Umbreon.endPulse = endPulse;
	}

	public static Map<User, TextChannel> getPrivateChannels() {
		return privateChannels;
	}

	public static void setPrivateChannels(Map<User, TextChannel> privateChannels) {
		Umbreon.privateChannels = privateChannels;
	}
}