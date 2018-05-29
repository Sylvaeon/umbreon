package me.sylvaeon.umbreon;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import me.sylvaeon.umbreon.command.Command;
import me.sylvaeon.umbreon.command.Commands;
import me.sylvaeon.umbreon.helper.ColorHelper;
import me.sylvaeon.umbreon.music.GuildMusicManager;
import me.sylvaeon.umbreon.rpg.command.CommandRPG;
import me.sylvaeon.umbreon.rpg.crafting.Recipe;
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
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.Presence;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class Umbreon extends ListenerAdapter {
    private static final String TOKEN = "NDQyODE5NDMzNTQ3ODI1MTUy.DdI0wg.k76KQG6xTf2Q22NdAW5_7_zU-oY";
    public static JDA jda;
    public static AudioPlayerManager playerManager;
    public static Map<Long, GuildMusicManager> musicManagers;
	public static Map<TextChannel, Long> lastLoadedMessageID;
    public static Member CYNTHIA, UMBREON;
    public static boolean loaded = false;
	public static Guild guild;
	public static Thread pulseThread;
	public static boolean pulse = true;
	public static boolean endPulse = false;

    public static void main(String[] args) throws LoginException, InterruptedException {

        jda = new JDABuilder(AccountType.BOT).setToken(TOKEN).buildBlocking();
        jda.addEventListener(new Umbreon());
        Presence presence = jda.getPresence();
        presence.setGame(Game.playing("🖤Love You💛"));
		lastLoadedMessageID = new HashMap<>();
        guild = jda.getGuildById("310572543767478272");
        CYNTHIA = guild.getMemberById(199523552850870272L);
        UMBREON = guild.getMemberById(442819433547825152L);

        musicManagers = new HashMap<>();

        playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(playerManager);
        AudioSourceManagers.registerLocalSource(playerManager);

		Enemies.init();
		Items.init();
		Recipes.init();
		Players.init();
		Commands.init();

		//Message Data
        initMessageData();
        loadMessageData();
		textChannel_loop:
        for (TextChannel textChannel : guild.getTextChannels()) {
			long lastID = lastLoadedMessageID.get(textChannel);
			message_loop:
            for (Message message : textChannel.getIterableHistory()) {
				Member member = message.getMember();
				try {
					member.getUser();
					Players.getPlayer(member).getLvl();
				} catch (NullPointerException e) {
					continue message_loop;
				}
				if (!member.getUser().isBot()) {
					if (lastID != -1L && message.getIdLong() <= lastID) {
						lastLoadedMessageID.put(textChannel, textChannel.getLatestMessageIdLong());
						continue textChannel_loop;
					} else {
						Players.getPlayer(member).addXp(1);
					}
				}
			}
			lastLoadedMessageID.put(textChannel, textChannel.getLatestMessageIdLong());
        }
        Players.updatePeople();
        
        saveMessageData();
        System.out.println("Done adding xp");

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
        loaded = true;
    }

    private static void initMessageData() {
		for(TextChannel textChannel : guild.getTextChannels()) {
			lastLoadedMessageID.put(textChannel, -1L);
		}
	}
    
    private static void loadMessageData() {
		File file = new File("src/main/resources/messages.json");
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader(file));
			JSONObject jsonObject = (JSONObject) obj;
			for (Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext(); ) {
				String key = (String) iterator.next();
				long value = (long) jsonObject.get(key);
				lastLoadedMessageID.put(guild.getTextChannelById(key), value);
			}
		} catch (IOException | ParseException | NullPointerException e) {

		}
	}
	
    private static void saveMessageData() {
    	File file = new File("src/main/resources/messages.json");
		JSONObject jsonObject = new JSONObject();
		for(Map.Entry<TextChannel, Long> entry : lastLoadedMessageID.entrySet()) {
			jsonObject.put(entry.getKey().getId(), entry.getValue());
		}
		try(FileWriter writer = new FileWriter(file)) {
			writer.write(jsonObject.toJSONString());
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized static void quit() {
		saveMessageData();
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
		Umbreon.jda.shutdownNow();
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
        String message = event.getMessage().getContentRaw();
        long ID = event.getMessage().getIdLong();
        Member member = event.getMember();
        TextChannel textChannel = event.getChannel();
        lastLoadedMessageID.put(textChannel, ID);
        if (loaded && !event.getAuthor().isBot()) {
        	Player player = Players.getPlayer(member);
            player.addXp(textChannel, 1);
            if (message.startsWith("$")) {
                message = message.substring(1);
                String[] split = message.split(" ");
                Command command = Commands.getCommand(split[0]);
                if (command != null) {
                    if (!command.requiresAdmin() || member.hasPermission(Permission.ADMINISTRATOR)) {
                    	if(!(command instanceof CommandRPG) || (textChannel.getName().equalsIgnoreCase("rpg") || textChannel.getName().equalsIgnoreCase("dev"))) {
							String[] args = new String[split.length - 1];
							if (args.length != 0) {
								for (int i = 1; i < split.length; i++) {
									args[i - 1] = split[i];
								}
							}
							command.onCall(args, member, textChannel);
						} else {
							textChannel.sendMessage("Please use #rpg for rpg commands!").queue();
						}
                    } else {
                        event.getChannel().sendMessage("Error: Invalid permissions").queue();
                    }
                } else {
                    event.getChannel().sendMessage("Error: Not a valid command! Type $help for a list of commands").queue();
                }
            }
            if (message.toLowerCase().contains("sylvan")) {
                PrivateChannel pc = event.getAuthor().openPrivateChannel().complete();
                pc.sendMessage("Please remember the information in #announcements ty <3\n~Cynthia").queue();
                event.getMessage().delete().complete();
            }
        }
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        Member member = event.getMember();
        Players.initPlayer(member);
    }
    
}