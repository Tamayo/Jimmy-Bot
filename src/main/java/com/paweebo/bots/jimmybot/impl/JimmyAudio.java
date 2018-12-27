package com.paweebo.bots.jimmybot.impl;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEvent;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.AudioManager;

import java.util.List;


/**
 * Created by Mangaloid on 12/7/2017.
 */
public class JimmyAudio extends ListenerAdapter {
    private final DiscordSoundManager joinSoundManager;
    private final DiscordSoundManager jimmySoundManager;
    private final String CHANNEL_NAME;

    public JimmyAudio(JDA jda,BotConfig botConfig) {
        CHANNEL_NAME = botConfig.getVoiceChannelName();
        AudioPlayerManager audioPlayerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(audioPlayerManager);
        AudioSourceManagers.registerLocalSource(audioPlayerManager);
        AudioPlayer audioPlayer = audioPlayerManager.createPlayer();
        Guild guild = jda.getGuilds().get(0);
        AudioManager audioManager = guild.getAudioManager();
        audioPlayer.addListener(new AudioEventAdapter() {

            @Override
            public void onEvent(AudioEvent audioEvent) {

            }

            public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
                audioManager.closeAudioConnection();
            }
        });

        audioManager.setSendingHandler(new AudioPlayerSendHandler(audioPlayer));
        joinSoundManager = new DiscordSoundManager(audioPlayerManager, audioManager, audioPlayer, guild.getVoiceChannelsByName(CHANNEL_NAME, true).get(0));
        jimmySoundManager = new DiscordSoundManager(audioPlayerManager, audioManager, audioPlayer, guild.getVoiceChannelsByName(CHANNEL_NAME, true).get(0));
        registerDiscordSounds(botConfig);
    }


    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String message = event.getMessage().getContent();
        int paramIndex = message.indexOf(" ");
        if(message == null || message.length() == 0){
            return;
        }
        String request = paramIndex != -1 ? message.toLowerCase().substring(0, paramIndex) : message.toLowerCase();
        switch(request){
            case "!listsounds":
                event.getTextChannel().sendMessage(jimmySoundManager.toString()).queue();
                break;
        }
        if (paramIndex == -1) {
            return;
        }
        String sound = message.substring(paramIndex + 1);
        System.out.println("Playing Jimmy Sound " + sound);
        jimmySoundManager.playJimmySound(sound);

    }

    @Override
    public void onGuildVoiceMove(GuildVoiceMoveEvent event) {
        userVoiceJoin(event, null);
    }

    @Override
    public void onGuildVoiceJoin(GuildVoiceJoinEvent event) {
        userVoiceJoin(null, event);
    }

    private void userVoiceJoin(GuildVoiceMoveEvent event, GuildVoiceJoinEvent event2) {
        Member member;
        String channelJoined;
        if (event2 == null) {
            member = event.getMember();
            channelJoined = event.getChannelJoined().getName().toLowerCase();
        } else {
            member = event2.getMember();
            channelJoined = event2.getChannelJoined().getName().toLowerCase();
        }
        if (channelJoined.equals(CHANNEL_NAME)) {
            String memberName = member.getUser().getName().toLowerCase();
            System.out.println("Playing sound for member " + memberName);
            joinSoundManager.playJimmySound(memberName);
        }
    }

    private void registerDiscordSounds(BotConfig botConfig){
        //SoundTrigger trigger = new SoundTrigger("yawn", "audio.wav");
        //jimmySoundManager.registerSound(trigger.getTrigger(),trigger.getSoundLink());
        List<SoundTrigger> soundTriggers = botConfig.getSoundTriggers();
        for(SoundTrigger trigger : soundTriggers){
          jimmySoundManager.registerSound(trigger.getTrigger(),trigger.getSoundLink());
        }
        List<SoundTrigger> joinTriggers = botConfig.getJoinTriggers();
        for(SoundTrigger trigger : joinTriggers){
            joinSoundManager.registerSound(trigger.getTrigger(),trigger.getSoundLink());
        }

        jimmySoundManager.registerSound("yawn","audio.wav");
        //jimmySoundManager.registerSound("slurp","ibEix1PhO1M");
        //jimmySoundManager.registerSound("ayylmao","qvI8XvAKAv0");
        jimmySoundManager.registerSound("alien","dZipOstSTsg");
        jimmySoundManager.registerSound("osteoporosis","dKT2CdxeIFk");
        jimmySoundManager.registerSound("depression","SLEdsI731J4");
        jimmySoundManager.registerSound("cricket","RktX4lbe_g4");
        jimmySoundManager.registerSound("drums","6zXDo4dL7SU");
        jimmySoundManager.registerSound("fail","_asNhzXq72w");
        jimmySoundManager.registerSound("dicks","-gYCHXCt0to");
        jimmySoundManager.registerSound("doit","FQRW0RM4V0k");
        jimmySoundManager.registerSound("horn","QVw5mnRI8Zw");
        jimmySoundManager.registerSound("wae","NqQd2ocRuCM");
        jimmySoundManager.registerSound("spag","1xA7j_TceH4");
        jimmySoundManager.registerSound("offer","Ch8uCOPbH7I");
        jimmySoundManager.registerSound("skidoo","3rdLRcazpdY");
        jimmySoundManager.registerSound("done","9Mmp6-Lofdk");
        jimmySoundManager.registerSound("terrell","audioclips/iBeatMyMeat.mp3");
        jimmySoundManager.registerSound("red","audioclips/frescocommunist.mp3");
        jimmySoundManager.registerSound("berserk","ocQ6PDiP014");
        jimmySoundManager.registerSound("cancel","RwJIAnh7lNw");
        //joinSoundManager.registerSound("jimmyfacts","ocQ6PDiP014");

     //  botConfig.getSoundTriggers();

    }
}