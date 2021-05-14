package com.paweebo.bots.jimmybot.impl;

import com.paweebo.bots.jimmybot.interfaces.JimmyBot;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

import javax.security.auth.login.LoginException;
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


    public JimmyBotImpl() throws Exception {
        botConfig = BotConfig.getBotConfiguration();
        try {
            jdaApi = JDABuilder.createDefault(new String(Files.readAllBytes(Paths.get("token.txt")))).build().awaitReady();
        } catch (LoginException e) {
            e.printStackTrace();
        }
        jimmyFacts = new JimmyFacts(jdaApi);
        jimmyAudio = new JimmyAudio(jdaApi,botConfig);
        jdaApi.addEventListener(jimmyFacts);
        jdaApi.addEventListener(jimmyAudio);
        Guild guild = jdaApi.getGuilds().get(0);
        TextChannel textChannel = guild.getTextChannelsByName("pawebbo",true).get(0);
        Scanner scan = new Scanner(System.in);
        while(true){
            String nextLine = scan.nextLine();
            textChannel.sendMessage(nextLine).queue();
        }
    }

}
