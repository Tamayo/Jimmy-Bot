package com.paweebo.bots.jimmybot.impl;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.managers.AudioManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mangaloid on 12/6/2017.
 */
public class DiscordSoundManager {

    private AudioPlayerManager audioPlayerManager;
    private AudioManager audioManager;
    private VoiceChannel voiceChannel;
    private AudioPlayer audioPlayer;
    private Map<String,String> soundMap;

    public DiscordSoundManager(AudioPlayerManager audioPlayerManager,AudioManager audioManager,AudioPlayer audiopPlayer,VoiceChannel voiceChannel) {
        this.audioPlayerManager = audioPlayerManager;
        this.audioManager = audioManager;
        this.voiceChannel = voiceChannel;
        this.audioPlayer = audiopPlayer;
        this.soundMap = new HashMap<>();
    }
    
    public void registerSound(String trigger,String sound){
        soundMap.put(trigger,sound);
    }

    public void playJimmySound(String sound){
        String link = soundMap.get(sound);
        audioManager.openAudioConnection(voiceChannel);
        audioPlayerManager.loadItem(link, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack audioTrack) {
                audioPlayer.playTrack(audioTrack);
            }

            @Override
            public void playlistLoaded(AudioPlaylist audioPlaylist) {

            }

            @Override
            public void noMatches() {
                System.out.println("Could not find track");
            }

            @Override
            public void loadFailed(FriendlyException e) {
                System.out.println("Failed to laod track");
                e.printStackTrace();
            }
        });
    }

    public String toString(){
        return "The following sounds are registered \n" +
                Arrays.toString(soundMap.keySet().toArray());
    }
}
