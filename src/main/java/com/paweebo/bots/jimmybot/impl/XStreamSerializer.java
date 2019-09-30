package com.paweebo.bots.jimmybot.impl;

import com.thoughtworks.xstream.XStream;

import java.io.*;

/**
 * Created by Mangaloid on 1/26/2018.
 */
public class XStreamSerializer<T> {

    private final String filePath;
    private final XStream xStream;
    private T defaultObject;

    public XStreamSerializer(String filePath){
        this.filePath = filePath;
        this.xStream = new XStream();
    }
    public XStreamSerializer(String filePath,T defaultObject){
        this.filePath = filePath;
        this.xStream = new XStream();
        this.defaultObject = defaultObject;
        this.xStream.ignoreUnknownElements();
    }

    public void serialize(T toSerialize){
        try {
            xStream.toXML(toSerialize,new FileOutputStream(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public T unSerialize(){
        T returnedObject = null;
        try {
            returnedObject = (T)xStream.fromXML(new FileInputStream(filePath));
        } catch (FileNotFoundException e) {
           if(defaultObject != null){
               serialize(defaultObject);
               returnedObject = defaultObject;
           }
            e.printStackTrace();
        }
        return returnedObject;
    }

    public XStream getXStream(){
        return xStream;
    }

    public void addClassAlias(String name, Class classToAlias){
        xStream.alias(name,classToAlias);
    }

    public interface FileNotExistCallback{
        void fileNotExist();
    }
}
