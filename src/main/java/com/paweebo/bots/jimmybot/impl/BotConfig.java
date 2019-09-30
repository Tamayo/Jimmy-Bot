package com.paweebo.bots.jimmybot.impl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mangaloid on 1/26/2018.
 */
public class BotConfig {

    private List<SoundTrigger> soundTriggers = new ArrayList();
    private List<SoundTrigger> joinTriggers = new ArrayList();


    private String voiceChannelName;
    private transient static final XStreamSerializer<BotConfig> xStreamSerializer = new XStreamSerializer<>("botConfig.xml",new BotConfig());

    static{
        xStreamSerializer.addClassAlias("configuration",BotConfig.class);
        xStreamSerializer.addClassAlias("soundTrigger",SoundTrigger.class);
    }

    public static BotConfig getBotConfiguration(){
        return xStreamSerializer.unSerialize();
    }
    public static void saveBotConfiguration(BotConfig botConfig){
        xStreamSerializer.serialize(botConfig);
    }

    private BotConfig(){

    }


    public String getVoiceChannelName() {
        return voiceChannelName;
    }

    public void setVoiceChannelName(String voiceChannelName) {
        this.voiceChannelName = voiceChannelName;
    }

    public List<SoundTrigger> getSoundTriggers() {
        return soundTriggers;
    }

    public void setSoundTriggers(List<SoundTrigger> soundTriggers) {
        this.soundTriggers = soundTriggers;
    }

    public List<SoundTrigger> getJoinTriggers() {
        return joinTriggers;
    }

    public void setJoinTriggers(List<SoundTrigger> joinTriggers) {
        this.joinTriggers = joinTriggers;
    }
}
