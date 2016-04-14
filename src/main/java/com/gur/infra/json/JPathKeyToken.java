package com.gur.infra.json;

public class JPathKeyToken implements JPathToken {
    final String key;

    public JPathKeyToken(String key){
        this.key = key;
    }

    public String getKey(){
        return key;
    }

    @Override
    public String toString(){
        return key;
    }
}
