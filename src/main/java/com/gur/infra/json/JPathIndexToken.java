package com.gur.infra.json;

public class JPathIndexToken implements JPathToken {
    final int index;

    public JPathIndexToken(int index){
        this.index = index;
    }

    public int getIndex(){
        return index;
    }

    @Override
    public String toString(){
        return Integer.toString(index);
    }
}
