package com.gur.infra.json;

/**
 * Created by gur on 11/28/2015.
 */
public abstract class StateMachine {
    State current;

    public void consume(CharSequence input) {
        if (current == State.REJECT){
            throw new JPathException("Invalid path");
        }
        if (input.length() == 0){
            current.consumeEpsilon();
            return;
        }

        current = current.consume(input.charAt(0));

        consume(input.subSequence(1, input.length()));
    }

    public void setStart(State state) {
        current = state;
    }
}

