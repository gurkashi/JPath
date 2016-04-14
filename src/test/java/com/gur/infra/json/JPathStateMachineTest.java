package com.gur.infra.json;

import org.junit.Assert;
import org.junit.Test;

public class JPathStateMachineTest {
    @Test
    public void empty() throws JPathException {
        JPathStateMachine machine = new JPathStateMachine();

        machine.consume("");
    }

    @Test
    public void key() throws JPathException {
        JPathStateMachine machine = new JPathStateMachine();

        machine.consume(".data");

        Assert.assertEquals("data", machine.getTokens().get(0).toString());
        Assert.assertEquals(JPathKeyToken.class, machine.getTokens().get(0).getClass());
    }

    @Test
    public void twoKeys() throws JPathException {
        JPathStateMachine machine = new JPathStateMachine();

        machine.consume(".first.second");

        Assert.assertEquals("first", machine.getTokens().get(0).toString());
        Assert.assertEquals("second", machine.getTokens().get(1).toString());
        Assert.assertEquals(JPathKeyToken.class, machine.getTokens().get(0).getClass());
        Assert.assertEquals(JPathKeyToken.class, machine.getTokens().get(1).getClass());
    }

    @Test
    public void twoKeysIndex() throws JPathException {
        JPathStateMachine machine = new JPathStateMachine();

        machine.consume(".first.second[12]");

        Assert.assertEquals("first", machine.getTokens().get(0).toString());
        Assert.assertEquals("second", machine.getTokens().get(1).toString());
        Assert.assertEquals("12", machine.getTokens().get(2).toString());
        Assert.assertEquals(JPathKeyToken.class, machine.getTokens().get(0).getClass());
        Assert.assertEquals(JPathKeyToken.class, machine.getTokens().get(1).getClass());
        Assert.assertEquals(JPathIndexToken.class, machine.getTokens().get(2).getClass());
    }

    @Test
    public void keyIndexKey() throws JPathException {
        JPathStateMachine machine = new JPathStateMachine();

        machine.consume(".first[5].second");

        Assert.assertEquals("first", machine.getTokens().get(0).toString());
        Assert.assertEquals("5", machine.getTokens().get(1).toString());
        Assert.assertEquals("second", machine.getTokens().get(2).toString());
        Assert.assertEquals(JPathKeyToken.class, machine.getTokens().get(0).getClass());
        Assert.assertEquals(JPathIndexToken.class, machine.getTokens().get(1).getClass());
        Assert.assertEquals(JPathKeyToken.class, machine.getTokens().get(2).getClass());
    }

    @Test
    public void indexIndexKey() throws JPathException {
        JPathStateMachine machine = new JPathStateMachine();

        machine.consume("[6][5].key");

        Assert.assertEquals("6", machine.getTokens().get(0).toString());
        Assert.assertEquals("5", machine.getTokens().get(1).toString());
        Assert.assertEquals("key", machine.getTokens().get(2).toString());
        Assert.assertEquals(JPathIndexToken.class, machine.getTokens().get(0).getClass());
        Assert.assertEquals(JPathIndexToken.class, machine.getTokens().get(1).getClass());
        Assert.assertEquals(JPathKeyToken.class, machine.getTokens().get(2).getClass());
    }
}
