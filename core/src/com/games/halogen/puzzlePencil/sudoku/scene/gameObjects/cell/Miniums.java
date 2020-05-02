package com.games.halogen.puzzlePencil.sudoku.scene.gameObjects.cell;

import java.util.ArrayList;

/** This class represents the set of numbers that can be possibly entered in a cell based on row, column and block values*/
public class Miniums extends ArrayList<Integer>{

    public void fillAll(int num) {
        for(int i=1;i<=num;i++){
            this.add(i);
        }
    }
}
