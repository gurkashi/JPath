package com.gur.infra.json;

public class JPathIndexState extends JPathState {
    private StringBuilder index;

    public JPathIndexState(JPathStateMachine context){
        super(context);
        this.index = new StringBuilder();
    }

    @Override
    public State consume(Character c){
        if (c >= '0' && c <= '9'){
            index.append(c);
            return this;
        }
        else if (c == ']'){
            context.addToken(new JPathIndexToken(Integer.parseInt(index.toString())));
            index = new StringBuilder();
            return context.getAnyState();
        }
        else {
            return State.REJECT;
        }
    }
}
