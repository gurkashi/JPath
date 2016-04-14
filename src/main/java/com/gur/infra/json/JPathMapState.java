package com.gur.infra.json;

public class JPathMapState extends JPathState {
    public JPathMapState(JPathStateMachine context){
        super(context);
    }

    @Override
    public State consume(Character c){
        if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c == '_'){
            JPathKeyState next = context.getKeyState();
            next.enterWith(c);
            return next;
        }
        else {
            return State.REJECT;
        }
    }
}
