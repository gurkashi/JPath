package com.gur.infra.json;

public class JPathKeyState extends JPathState {
    private StringBuilder key;

    public JPathKeyState(JPathStateMachine context){
        super(context);
        this.key = new StringBuilder();
    }

    @Override
    public State consume(Character c){
        if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c == '_' || c >= '0' && c <= '9'){
            key.append(c);
            return this;
        }
        else if (c == '.'){
            context.addToken(new JPathKeyToken(key.toString()));
            key = new StringBuilder();
            return context.getMapState();
        }
        else if (c == '['){
            context.addToken(new JPathKeyToken(key.toString()));
            key = new StringBuilder();
            return context.getIndexState();
        }
        else {
            return State.REJECT;
        }
    }

    @Override
    public void consumeEpsilon(){
        context.addToken(new JPathKeyToken(key.toString()));
        key = new StringBuilder();
    }

    public void enterWith(Character c){
        key.append(c);
    }
}
