package com.games.halogen.puzzlePencil.sudoku.generator.processors;

import com.games.halogen.puzzlePencil.sudoku.model.grid.Cell;
import com.games.halogen.puzzlePencil.sudoku.model.grid.Grid;
import com.games.halogen.puzzlePencil.sudoku.model.House;
import com.games.halogen.puzzlePencil.sudoku.model.HouseSubset;
import com.games.halogen.puzzlePencil.sudoku.model.PenMarks;

import java.util.ArrayList;

public class HiddenSinglesProcessor extends GridProcessor {
    /*
    Finds a house which has only one cell where i can be placed
     */
    @Override
    public void find(Grid grid) {
        this.getTargetSubsets().clear();
        for(House h:getGridHouses(grid)){
            for(Integer i:getRandomizedIndices(grid)){
                //find cells of h with i as pencil mark
                ArrayList<Cell> cells = h.getCellsWithMark(i);

                //if only one cell has i mark, it is a hidden single
                if(cells.size() == 1) {
                    this.getTargetSubsets().add(new HouseSubset(h, cells, new PenMarks(i)));
                    return;
                }
            }
        }
    }

    @Override
    public void process(Grid grid) {
        ArrayList<HouseSubset> data = this.getTargetSubsets();

        if(data.size() != 1){
            throw new RuntimeException("A hidden single requires only one house subset, found " + data.size());
        }

        HouseSubset hs = data.get(0);
        if(hs.cellCount() != 1){
            throw new RuntimeException("A hidden single should have only one cell in its house subset to be processed, found " + hs.cellCount());
        }

        PenMarks pm = hs.getSpecialMarks();
        if (pm.getMarksCount() != 1){
            throw new RuntimeException("A hidden single should have only one special penMark, found " + pm.getMarksCount());
        }

        Cell c = hs.getCell(0);
        grid.setCellValue(c.getCoordinates().getFirst(), c.getCoordinates().getSecond(),pm.getMark(0));
    }
}
