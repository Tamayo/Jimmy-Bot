package com.paweebo.bots.jimmybot;

import com.paweebo.bots.jimmybot.impl.JimmyBotImpl;
import com.paweebo.bots.jimmybot.interfaces.JimmyBot;

/**
 * Created by Mangaloid on 12/3/2017.
 */
public class JimmyBotRunner {

    public static void main(String[] args) throws Exception{
        JimmyBot jimmyBot = new JimmyBotImpl();
    }
}
