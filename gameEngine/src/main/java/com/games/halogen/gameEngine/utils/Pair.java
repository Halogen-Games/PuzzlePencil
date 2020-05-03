package com.games.halogen.gameEngine.utils;

public class Pair<T, U> {

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

    public static class IntPair extends Pair<Integer, Integer>{
        public IntPair(Integer first, Integer second) {
            super(first, second);
        }
    }
}
