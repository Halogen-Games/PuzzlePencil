package com.games.halogen.puzzlePencil.sudoku.generator.processors;

import com.games.halogen.puzzlePencil.sudoku.generator.model.Cell;
import com.games.halogen.puzzlePencil.sudoku.generator.model.Grid;
import com.games.halogen.puzzlePencil.sudoku.generator.model.House;
import com.games.halogen.puzzlePencil.sudoku.generator.model.HouseSubset;

import java.util.ArrayList;

public class NakedSinglesProcessor extends GridProcessor {
    /*
    Finds a house which has only one pen mark
     */
    @Override
    public void find(Grid grid) {
        this.getTargetSubsets().clear();

        for(House h:getGridHouses(grid)){
            //find cells of h with only 1 pencil mark
            ArrayList<Cell> cells = h.getCellsWithMarkCount(1);

            //if only one cell has i mark, it is a hidden single
            if(cells.size() > 0) {
                this.getTargetSubsets().add(new HouseSubset(h, cells, cells.get(0).getPenMarks()));
                return;
            }
        }
    }

    @Override
    public void process(Grid grid) {
        ArrayList<HouseSubset> data = this.getTargetSubsets();
        if(data.size() != 1){
            throw new RuntimeException("A naked single requires only one house subset, found " + data.size());
        }

        HouseSubset hs = data.get(0);
        if(hs.cellCount() != 1){
            throw new RuntimeException("A naked single should have only one cell in its house subset to be processed, found " + hs.cellCount());
        }

        Cell c = hs.getCell(0);
        if (c.getPenMarks().getMarksCount() != 1){
            throw new RuntimeException("A naked single should have only one penMark, found " + c.getPenMarks().getMarksCount());
        }

        grid.setCellValue(c.getCoordinates().getFirst(), c.getCoordinates().getSecond(),c.getPenMarks().getMark(0));
    }
}
