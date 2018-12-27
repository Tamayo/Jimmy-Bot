package com.paweebo.bots.jimmybot.impl;

import com.thoughtworks.xstream.XStream;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.requests.RestAction;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Mangaloid on 12/3/2017.
 */
public class JimmyFacts  extends ListenerAdapter {
    private final List<String> facts= new ArrayList<>();
    private final XStreamSerializer<List<String>> xStream;
    private final JDA jdaApi;

    public JimmyFacts(JDA jdaApi){
        this.jdaApi = jdaApi;
        this.xStream = new XStreamSerializer<>("jimmyFacts.xml",facts);
        List<String> persistedFacts = xStream.unSerialize();
        facts.addAll(persistedFacts);
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        String message = event.getMessage().getContent();
        MessageChannel messageChannel = event.getChannel();
        if(message.charAt(0) != '!'){
            return;
        }
        int paramIndex = message.indexOf(" ");
        String request = paramIndex != -1 ? message.toLowerCase().substring(0,paramIndex) : message;
        RestAction<Message> messageToSend = null;
        switch(Request.fromRequestString(request)){
            case RANDOM_FACT:
                messageToSend = messageChannel.sendMessage(getFact(event));
                break;
            case LIST_FACTS:
                List<String> facts = getJimmyFactList();
                facts.forEach(fact -> messageChannel.sendMessage(fact).queue());
                break;
            case ADD_FACT:
                if(paramIndex != -1){
                    String fact = message.substring(paramIndex+1);
                    addNewFact(fact);
                }
                break;
            case REMOVE_FACT:
                removeFact(message);
                break;
            case MODIFY_FACT:
                modifyFact(message);
                break;
            case NOT_VALID:
                return;
            default:
                break;
        }
        if(messageToSend != null){
            messageToSend.queue();
        }
    }

    private String getFact(MessageReceivedEvent event){
        Random rand = new Random();
        String message = event.getMessage().getContent();
        int jimmyFactIndex = message.indexOf(" ");

        if(jimmyFactIndex != -1){
            jimmyFactIndex = Integer.parseInt(message.substring(message.indexOf(" ")+1));
        }else{
            jimmyFactIndex= rand.nextInt(facts.size());
        }

        return processFact(facts.get(jimmyFactIndex),event);
    }

    private void modifyFact(String message) {
        String[] splitString = message.split(" ");
        int factIndex = Integer.parseInt(splitString[1]);
        StringBuilder stringBuilder = new StringBuilder();
        for(int x =2;x<splitString.length;x++){
            stringBuilder.append(splitString[x]).append(" ");
        }
        facts.set(factIndex,stringBuilder.toString());
        saveFacts();
    }

    private void removeFact(String message) {
       // int paramIndex = message.indexOf(" ");
       // String request = paramIndex != -1 ? message.toLowerCase().substring(0,paramIndex) : message;
    }

    private String processFact(String fact,MessageReceivedEvent event) {
        Random rand = new Random();
        List<Member> members = jdaApi.getGuilds().get(0).getMembers();
        String returnFact = fact.replace("[sender]",event.getAuthor().getName())
                .replace("[random_user]",members.get(rand.nextInt(members.size())).getEffectiveName());
        return returnFact;
    }

    private void addNewFact(String fact){
        facts.add(fact);
        saveFacts();
    }

    private synchronized void saveFacts(){
            xStream.serialize(facts);
    }

    public List<String> getJimmyFactList() {
        List<String> list = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Here are all the current Jimmy Facts");
        stringBuilder.append(System.lineSeparator());
        for(int x =0;x<facts.size();x++){
            stringBuilder.append(x);
            stringBuilder.append(": ");
            stringBuilder.append(facts.get(x));
            stringBuilder.append(System.lineSeparator());
            if(stringBuilder.length() >=1400){
                list.add(stringBuilder.toString());
                stringBuilder.setLength(0);
            }

        }
        list.add(stringBuilder.toString());
        return list;
    }

    public enum Request{
        RANDOM_FACT("jimmyfact"),
        LIST_FACTS("listfacts"),
        ADD_FACT("addfact"),
        REMOVE_FACT("removefact"),
        MODIFY_FACT("modifyfact"),
        JIMMY_SOUND("jimmysound"),
        NOT_VALID("asdfdfdfdfdfdfdfd");

        private String requestString;

        private static final Map<String,Request> requestStringToEnum = Arrays.asList(Request.values()).stream().collect(Collectors.toMap(Request::getRequestString,r -> r));

        Request(String requestString){
            this.requestString = requestString;
        }

        public static Request fromRequestString(String requestString){
            Request requestToReturn = requestStringToEnum.get(requestString.replace("!",""));
            return requestToReturn != null ? requestStringToEnum.get(requestString.replace("!","")) : NOT_VALID;
        }

        public String getRequestString() {
            return requestString;
        }
    }
}
