package com.gur.infra.json;

public class JPathAnyState extends JPathState {
    public JPathAnyState(JPathStateMachine context){
        super(context);
    }

    @Override
    public State consume(Character c){
        if(c == '.'){
            return context.getMapState();
        }
        else if (c == '['){
            return context.getIndexState();
        }
        else {
            return State.REJECT;
        }
    }
}

