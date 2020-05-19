package com.games.halogen.puzzlePencil.sudoku.generator.processors;

import com.games.halogen.puzzlePencil.sudoku.generator.model.Cell;
import com.games.halogen.puzzlePencil.sudoku.generator.model.Grid;
import com.games.halogen.puzzlePencil.sudoku.generator.model.House;
import com.games.halogen.puzzlePencil.sudoku.generator.model.HouseSubset;

import java.util.ArrayList;

public class NakedSinglesProcessor extends GridProcessor {
    @Override
    public ArrayList<HouseSubset> find(Grid grid) {
        for(House h:getGridHouses(grid)){
            for(Integer i:getRandomizedIndices(grid)){
                ArrayList<Cell> cells = h.getCellsWithMark(i, 1);
                ArrayList<HouseSubset> hs = new ArrayList<>();
                hs.add(new HouseSubset(h, cells));
                return hs;
            }
        }

        return null;
    }

    @Override
    public void process(ArrayList<HouseSubset> data) {
        if(data.size() != 1 || data.get(0).size() != 1){
            throw new RuntimeException("Malformed data for Naked Single processor");
        }
        Cell c = data.get(0).get(0);
        c.setValue(c.getPenMarks().get(0));
    }
}
