package com.games.halogen.puzzlePencil.sudoku.scene.view.cell;

import java.util.ArrayList;

//todo: this is a model, move to models class
/** This class represents the set of numbers that can be possibly entered in a cell based on row, column and block values*/
public class Miniums extends ArrayList<Integer>{

    public Miniums fillAll(int num) {
        for(int i=1;i<=num;i++){
            this.add(i);
        }

        return this;
    }
}
