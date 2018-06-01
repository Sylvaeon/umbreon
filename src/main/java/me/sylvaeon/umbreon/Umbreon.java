package me.sylvaeon.umbreon;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import me.sylvaeon.umbreon.command.Command;
import me.sylvaeon.umbreon.command.Commands;
import me.sylvaeon.umbreon.helper.ColorHelper;
import me.sylvaeon.umbreon.music.GuildMusicManager;
import me.sylvaeon.umbreon.rpg.crafting.Recipes;
import me.sylvaeon.umbreon.rpg.entity.enemy.Enemies;
import me.sylvaeon.umbreon.rpg.entity.player.Player;
import me.sylvaeon.umbreon.rpg.entity.player.Players;
import me.sylvaeon.umbreon.rpg.item.Items;
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
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Umbreon extends ListenerAdapter {
    private static final String TOKEN = "NDQyODE5NDMzNTQ3ODI1MTUy.DdI0wg.k76KQG6xTf2Q22NdAW5_7_zU-oY";
    public static JDA jda;
    public static AudioPlayerManager playerManager;
    public static Map<Long, GuildMusicManager> musicManagers;
    public static Member CYNTHIA, UMBREON;
	public static Guild guild;
	public static Thread pulseThread;
	public static boolean pulse = true;
	public static boolean endPulse = false;
	public static Map<User, TextChannel> privateChannels;

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
		Enemies.init();
		Recipes.init();
		Players.init();
		Commands.init();
        Players.updatePeople();

        pulseThread = new Thread() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < Integer.MAX_VALUE; i++) {
                        if(endPulse) {
                            Color color = ColorHelper.getPinkGradientColor(7);
							guild.getRolesByName("\uD83D\uDC51Owner", true).get(0).getManager().setColor(color).complete();
                            return;
                        } else {
                            if (i >= 14) {
                                i = 0;
                            }
                            if(pulse) {
                                Color color = ColorHelper.getPinkGradientColor(i % 14);
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
        System.out.println("Thread 2 Initialized");
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
    	TextChannel textChannel = message.getTextChannel();
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
						command.onCall(args, member, textChannel);
					} else {
						textChannel.sendMessage("Error: Invalid permissions").queue();
					}
				} else {
					textChannel.sendMessage("Error: Not a valid command! Type $help for a list of commands").queue();
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

}