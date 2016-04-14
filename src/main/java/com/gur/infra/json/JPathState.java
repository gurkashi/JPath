package com.gur.infra.json;

public abstract class JPathState extends State{
    protected final JPathStateMachine context;

    protected JPathState(JPathStateMachine context){
        this.context = context;
    }
}
