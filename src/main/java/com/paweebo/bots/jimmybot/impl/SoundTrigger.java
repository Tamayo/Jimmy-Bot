package com.paweebo.bots.jimmybot.impl;

public class SoundTrigger {

        private String trigger;
        private String soundLink;

        public SoundTrigger(String trigger, String soundLink){
            this.trigger=trigger;
            this.soundLink=soundLink;

        }

    public String getTrigger() {
        return trigger;
    }

    public String getSoundLink() {
        return soundLink;
    }
}
