package com.games.halogen.puzzlePencil.sudoku.grid.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class PenMarks {
    private ArrayList<Integer> marks;

    public PenMarks(Integer... a){
        marks = new ArrayList<>();
        marks.addAll(Arrays.asList(a));

        Collections.sort(marks);
    }

    public int getMark(int i) {
        return marks.get(i);
    }

    public int getMarksCount() {
        return marks.size();
    }

    public void addMark(Integer num) {
        if(!marks.contains(num)){
            marks.add(num);
        }

        Collections.sort(marks);
    }

    public void removeAllMarks() {
        marks.clear();
    }

    public void removeMark(Integer num) {
        marks.remove(num);
    }

    public void toggleMark(Integer num) {
        if (marks.contains(num)){
            marks.remove(num);
        }else{
            marks.add(num);
        }
    }

    public String toString(){
        return marks.toString();
    }
}
