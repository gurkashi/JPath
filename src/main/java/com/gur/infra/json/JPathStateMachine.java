package com.gur.infra.json;

import java.util.LinkedList;
import java.util.List;

public class JPathStateMachine extends StateMachine{
    private final List<JPathToken> tokens;
    private final JPathKeyState keyState;
    private final JPathIndexState indexState;
    private final JPathAnyState anyState;
    private final JPathMapState mapState;

    public JPathStateMachine(){
        tokens = new LinkedList<JPathToken>();

        keyState = new JPathKeyState(this);
        indexState = new JPathIndexState(this);
        anyState = new JPathAnyState(this);
        mapState = new JPathMapState(this);

        super.setStart(anyState);
    }

    public void addToken(JPathToken token) {
        tokens.add(token);
    }

    public JPathIndexState getIndexState() {
        return indexState;
    }

    public JPathKeyState getKeyState() {
        return keyState;
    }

    public State getMapState() { return mapState; }

    public JPathAnyState getAnyState() {
        return anyState;
    }

    public List<JPathToken> getTokens() {
        return tokens;
    }
}
