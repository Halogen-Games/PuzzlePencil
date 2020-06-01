package com.games.halogen.gameEngine.utils;

import java.util.Locale;

public abstract class Pair<T, U> {

    public static Object IntPair;
    private T first;
    private U second;

    public Pair(T first, U second){
        this.first = first;
        this.second = second;
    }

    public T getFirst(){
        return first;
    }

    public U getSecond(){
        return second;
    }

    public void set(T first, U second){
        this.first = first;
        this.second = second;
    }

    public abstract String toString();

    public static class IntPair extends Pair<Integer, Integer>{
        public IntPair(Integer first, Integer second) {
            super(first, second);
        }

        @Override
        public String toString() {
            return String.format(Locale.US, "(%d, %d)", getFirst(), getSecond());
        }
    }
}
