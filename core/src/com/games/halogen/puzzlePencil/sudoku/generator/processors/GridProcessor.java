package com.games.halogen.puzzlePencil.sudoku.generator.processors;

import com.games.halogen.puzzlePencil.sudoku.generator.GridGeneratorParameters;
import com.games.halogen.puzzlePencil.sudoku.generator.model.Grid;
import com.games.halogen.puzzlePencil.sudoku.generator.model.House;
import com.games.halogen.puzzlePencil.sudoku.generator.model.HouseSubset;
import com.games.halogen.puzzlePencil.sudoku.generator.model.HouseType;

import java.util.ArrayList;
import java.util.Collections;

public abstract class GridProcessor {
    public abstract ArrayList<HouseSubset> find(Grid grid);
    public abstract void process(ArrayList<HouseSubset> data);

    public ArrayList<House> getGridHouses(Grid grid){
        ArrayList<House> rows = grid.getHouses(HouseType.ROW);
        ArrayList<House> cols = grid.getHouses(HouseType.COLUMN);
        ArrayList<House> blocks = grid.getHouses(HouseType.BLOCK);

        rows.addAll(cols);
        rows.addAll(blocks);

        Collections.shuffle(rows, GridGeneratorParameters.getRandom());

        return rows;
    }

    public ArrayList<Integer> getRandomizedIndices(Grid grid){
        return null;
    }
}
