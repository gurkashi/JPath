package com.gur.infra.json;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by gur on 11/24/2015.
 *
 */
public class JPath {
    private final JSONObject json;

    public JPath(){
        this(new JSONObject());
    }

    public JPath(JSONObject json){
        this.json = json;
    }

    public JPath(String map){
        this(new JSONObject(map));
    }

    public Object get(String path) {
        List<JPathToken> tokens = compilePath(path);
        return get(tokens, json);
    }

    public void put(String path, Object value) {
        List<JPathToken> tokens = compilePath(path);
        List<JPathToken> headTokens = tokens.subList(0, tokens.size() - 1);
        JPathToken last = tokens.get(tokens.size() - 1);

        Object container = get(headTokens, json);

        if (last.getClass().equals(JPathKeyToken.class)){
            JPathKeyToken keyToken = (JPathKeyToken) last;
            JSONObject mapContainer = (JSONObject) container;
            mapContainer.put(keyToken.getKey(), value);
        }
        else if (last.getClass().equals(JPathIndexToken.class)){
            JPathIndexToken indexToken = (JPathIndexToken) last;
            JSONArray arrayContainer = (JSONArray) container;
            arrayContainer.put(indexToken.getIndex(), value);
        }
        else {
            throw new JPathException("Cannot put, Invalid token");
        }
    }

    public void remove(String path) {
        List<JPathToken> tokens = compilePath(path);
        List<JPathToken> headTokens = tokens.subList(0, tokens.size() - 1);
        JPathToken last = tokens.get(tokens.size() - 1);

        Object container = get(headTokens, json);

        if (last.getClass().equals(JPathKeyToken.class)){
            JPathKeyToken keyToken = (JPathKeyToken) last;
            JSONObject mapContainer = (JSONObject) container;
            mapContainer.remove(keyToken.key);
        }
        else if (last.getClass().equals(JPathIndexToken.class)){
            JPathIndexToken indexToken = (JPathIndexToken) last;
            JSONArray arrayContainer = (JSONArray) container;
            arrayContainer.remove(indexToken.getIndex());
        }
        else {
            throw new JPathException("Cannot remove, Invalid token");
        }
    }

    public void rename(String path, String newName) {
        List<JPathToken> tokens = compilePath(path);
        List<JPathToken> headTokens = tokens.subList(0, tokens.size() - 1);
        JPathToken last = tokens.get(tokens.size() - 1);

        if (!last.getClass().equals(JPathKeyToken.class)){
            throw new JPathException("Can only rename keys");
        }

        JSONObject container = (JSONObject) get(headTokens, json);
        JPathKeyToken keyToken = (JPathKeyToken) last;
        Object value = container.get(keyToken.key);
        container.remove(keyToken.key);
        container.put(newName, value);
    }

    public int size(String path) {
        Object obj = get(path);

        if (obj.getClass().equals(JSONArray.class)){
            return ((JSONArray)obj).length();
        }
        else if (obj.getClass().equals(JSONObject.class)){
            return ((JSONObject)obj).length();
        }
        else {
            throw new JPathException("Object is not a valid json container");
        }
    }

    @Override
    public String toString(){
        return this.json.toString();
    }

    static Object get(List<JPathToken> tokens, Object context) {
        if (tokens.isEmpty()){
            return context;
        }

        JPathToken headToken = tokens.get(0);
        List<JPathToken> tailTokens = tokens.subList(1, tokens.size());

        if (headToken.getClass().equals(JPathKeyToken.class)){
            JPathKeyToken keyToken = (JPathKeyToken) headToken;
            JSONObject mapContext = (JSONObject) context;
            return get(tailTokens, mapContext.get(keyToken.getKey()));
        }
        else if (headToken.getClass().equals(JPathIndexToken.class)){
            JPathIndexToken indexToken = (JPathIndexToken) headToken;
            JSONArray arrayContext = (JSONArray) context;
            return get(tailTokens, arrayContext.get(indexToken.getIndex()));
        }

        throw new JPathException("Invalid token reached");
    }

    static List<JPathToken> compilePath (CharSequence path) {
        JPathStateMachine tokenizer = new JPathStateMachine();
        tokenizer.consume(path);
        return tokenizer.getTokens();
    }

    @Override
    public boolean equals(Object other){
        return other!= null && other.getClass().equals(getClass()) && json.equals(((JPath)other).json);
    }

    @Override
    public int hashCode(){
        return json.hashCode();
    }
}