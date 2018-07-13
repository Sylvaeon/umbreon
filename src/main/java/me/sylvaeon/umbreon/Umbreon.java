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
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.Presence;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public final class Umbreon extends ListenerAdapter {
    private static final String TOKEN = "NDQyODE5NDMzNTQ3ODI1MTUy.DdI0wg.k76KQG6xTf2Q22NdAW5_7_zU-oY";
    private static JDA jda;
    private static AudioPlayerManager playerManager;
   	private static Map<Long, GuildMusicManager> musicManagers;
   	private static User CYNTHIA, UMBREON;
	private static Thread pulseThread;
	private static boolean pulse = true;
	private static boolean endPulse = false;
	private static Map<User, TextChannel> privateChannels;

    public static void main(String[] args) throws LoginException, InterruptedException {
        jda = new JDABuilder(AccountType.BOT).setToken(TOKEN).buildBlocking();
        jda.addEventListener(new Umbreon());
        Presence presence = jda.getPresence();
        presence.setGame(Game.playing("🖤Love You💛"));
        CYNTHIA = jda.getUserById(199523552850870272L);
        UMBREON = jda.getSelfUser();
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

        pulseThread = new Thread() {
            @Override
            public void run() {
            	Guild guild = jda.getGuildById(310572543767478272L);
            	Role role = guild.getRoleById(440329428439007235L);
                try {
                    for (int i = 0; i < Integer.MAX_VALUE; i++) {
                        if(endPulse) {
                            Color color = new Color(0xEE82EE);
							role.getManager().setColor(color).complete();
                            return;
                        } else {
                            if (i >= 14) {
                                i = 0;
                            }
                            if(pulse) {
                                Color color = Utility.getPinkGradientColor(i % 14);
								role.getManager().setColor(color).queue();
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
        System.out.println("Thread 2 Initialized");
    }

	public synchronized static void quit() {
		Players.close();
		World.close();
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

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        processMessage(event.getMessage());
    }

	private void processMessage(Message message) {
    	String messageString = message.getContentRaw();
    	TextChannel channel = message.getTextChannel();
    	User user = message.getAuthor();
		Member member = message.getMember();
    	Player player = Players.getPlayer(user);
		if (!user.isBot()) {
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
			if (messageString.toLowerCase().contains(badword)) {
				PrivateChannel pc = member.getUser().openPrivateChannel().complete();
				pc.sendMessage("Watch your language :<\n~Cynthia").queue();
				message.delete().complete();
			}
		}
	}

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        Players.initPlayer(event.getUser());
    }

    public static boolean addPrivateChannel(User user) {
    	String name = "rpg-" + user.getId();
    	Guild guild = jda.getGuildById(310572543767478272L);
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

	public static AudioPlayerManager getPlayerManager() {
		return playerManager;
	}

	public static Map<Long, GuildMusicManager> getMusicManagers() {
		return musicManagers;
	}

	public static User getCYNTHIA() {
		return CYNTHIA;
	}

	public static User getUMBREON() {
		return UMBREON;
	}

	public static Thread getPulseThread() {
		return pulseThread;
	}

	public static boolean isPulse() {
		return pulse;
	}

	public static void flipPulse() {
    	pulse = !pulse;
	}

	public static boolean isEndPulse() {
		return endPulse;
	}

	public static Map<User, TextChannel> getPrivateChannels() {
		return privateChannels;
	}









































































	private static final String badword = "sylvan";
}