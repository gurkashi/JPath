package com.gur.infra.json;

public class State{
    protected State(){};

    public static final State REJECT = new State();

    public State consume(Character c){ return REJECT; };

    @Override
    public String toString(){
        return getClass().getSimpleName();
    }

    public void consumeEpsilon() {}
}

