package com.games.halogen.puzzlePencil.sudoku.generator.processors;

import com.games.halogen.puzzlePencil.sudoku.generator.GridGeneratorParameters;
import com.games.halogen.puzzlePencil.sudoku.grid.model.grid.Grid;
import com.games.halogen.puzzlePencil.sudoku.grid.model.House;
import com.games.halogen.puzzlePencil.sudoku.grid.model.HouseSubset;
import com.games.halogen.puzzlePencil.sudoku.grid.model.HouseType;

import java.util.ArrayList;
import java.util.Collections;

public abstract class GridProcessor {
    public abstract void find(Grid grid);
    public abstract void process(Grid grid);

    private ArrayList<HouseSubset> targetSubsets;
    private ArrayList<HouseSubset> affectedSubsets;

    public GridProcessor(){
        this.targetSubsets = new ArrayList<>();
        this.affectedSubsets = new ArrayList<>();
    }

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
        //todo fill
        return new ArrayList<>();
    }

    public ArrayList<HouseSubset> getTargetSubsets(){
        return targetSubsets;
    }
}
