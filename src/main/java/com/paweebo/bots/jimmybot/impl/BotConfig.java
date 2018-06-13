package com.paweebo.bots.jimmybot.impl;

/**
 * Created by Mangaloid on 1/26/2018.
 */
public class BotConfig {

    private String voiceChannelName;
    private transient static final XStreamSerializer<BotConfig> xStreamSerializer = new XStreamSerializer<BotConfig>("botConfig.xml",new BotConfig());

    public static BotConfig getBotConfiguration(){
        xStreamSerializer.getXStream().alias("configuration",BotConfig.class);
        return xStreamSerializer.unSerialize();
    }

    private BotConfig(){

    }


    public String getVoiceChannelName() {
        return voiceChannelName;
    }

    public void setVoiceChannelName(String voiceChannelName) {
        this.voiceChannelName = voiceChannelName;
    }
}
