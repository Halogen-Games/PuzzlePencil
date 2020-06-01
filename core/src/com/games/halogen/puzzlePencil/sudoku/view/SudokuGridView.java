package com.games.halogen.puzzlePencil.sudoku.view;

import com.games.halogen.puzzlePencil.oldSudoku.viewOld.SudokuObject;
import com.games.halogen.puzzlePencil.sudoku.model.grid.Grid;

public class SudokuGridView extends SudokuObject {
    private Grid grid;

    @Override
    public void init() {
        int dimensions = getCallbacks().getData().gridDimensions;
        grid = new Grid(dimensions);


    }

}
