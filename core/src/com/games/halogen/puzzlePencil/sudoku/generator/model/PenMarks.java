package com.games.halogen.puzzlePencil.sudoku.generator.model;

import java.util.ArrayList;
import java.util.Collections;

public class PenMarks {
    private ArrayList<Integer> marks;

    public PenMarks(int... a){
        marks = new ArrayList<>();
        for(int i:a){
            marks.add(i);
        }

        Collections.sort(marks);
    }

    public int getMark(int i) {
        return marks.get(i);
    }

    public int getMarksCount() {
        return marks.size();
    }
}
