package com.paweebo.bots.jimmybot.impl;

import com.paweebo.bots.jimmybot.interfaces.JimmyBot;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Created by Mangaloid on 12/3/2017.
 */
public class JimmyBotImpl implements JimmyBot {
    private JDA jdaApi;

    private final JimmyFacts jimmyFacts;
    private final JimmyAudio jimmyAudio;
    private final BotConfig botConfig;


    public JimmyBotImpl() {
        botConfig = BotConfig.getBotConfiguration();
        try {
            jdaApi = new JDABuilder(AccountType.BOT).setToken(new String(Files.readAllBytes(Paths.get("token.txt")))).buildBlocking();
        } catch (LoginException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RateLimitedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        jimmyFacts = new JimmyFacts(jdaApi);
        jimmyAudio = new JimmyAudio(jdaApi,botConfig);
        jdaApi.addEventListener(jimmyFacts);
        jdaApi.addEventListener(jimmyAudio);
        Guild guild = jdaApi.getGuilds().get(0);
        TextChannel textChannel = guild.getTextChannelsByName("pa-webbo",true).get(0);
        Scanner scan = new Scanner(System.in);
        while(true){
            String nextLine = scan.nextLine();
            textChannel.sendMessage(nextLine).queue();
        }
    }

}
