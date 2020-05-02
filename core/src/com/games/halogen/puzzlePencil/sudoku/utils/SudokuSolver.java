package com.games.halogen.puzzlePencil.sudoku.utils;

import com.badlogic.gdx.math.Vector2;
import com.games.halogen.puzzlePencil.sudoku.scene.gameObjects.cell.Cell;
import com.games.halogen.puzzlePencil.sudoku.scene.gameObjects.cell.Miniums;

import java.util.ArrayList;

public class SudokuSolver {
    public void solveGridCell(ArrayList<ArrayList<Cell>> cells, Cell cell) {
        Miniums miniums = getMiniums(cells, cell);

        //naked single check
        if (miniums.size() == 1) {
            cell.setEmpty();
            cell.setValue(miniums.get(0));
        }
    }

    private Miniums getMiniums(ArrayList<ArrayList<Cell>> cells, Cell cell) {
        int gridSize = cells.size();
        int blockSize = (int) Math.sqrt(gridSize);

        Miniums miniums = new Miniums();
        miniums.fillAll(gridSize);

        //check row
        for (Cell c:getRowCells(cells, cell)) {
            if (!c.isEmpty()) {
                miniums.remove((Integer) c.getValue());
            }
        }

        //check col
        for (Cell c:getColumnCells(cells, cell)) {
            if (!c.isEmpty()) {
                miniums.remove((Integer) c.getValue());
            }
        }

        //check block
        for (Cell c:getBlockCells(cells, cell)) {
            if (!c.isEmpty()) {
                miniums.remove((Integer) c.getValue());
            }
        }

        return miniums;
    }

    private ArrayList<Cell> getRowCells(ArrayList<ArrayList<Cell>> cells, Cell cell){
        ArrayList<Cell> rv = new ArrayList<>();
        for (int j = 0; j < cells.size(); j++) {
            rv.add(cells.get((int) cell.getCoordinates().x).get(j));
        }

        return rv;
    }

    private ArrayList<Cell> getColumnCells(ArrayList<ArrayList<Cell>> cells, Cell cell){
        ArrayList<Cell> rv = new ArrayList<>();
        for (int i = 0; i < cells.size(); i++) {
            rv.add(cells.get(i).get((int) cell.getCoordinates().y));
        }

        return rv;
    }

    private ArrayList<Cell> getBlockCells(ArrayList<ArrayList<Cell>> cells, Cell cell){
        ArrayList<Cell> rv = new ArrayList<>();

        int gridSize = cells.size();
        int blockSize = (int) Math.sqrt(gridSize);

        Vector2 cellBlockInd = new Vector2((int) (cell.getCoordinates().x / blockSize), (int) (cell.getCoordinates().y / blockSize));
        for (int i = 0; i < blockSize; i++) {
            for (int j = 0; j < blockSize; j++) {
                rv.add(cells.get(i + (int) (cellBlockInd.x * blockSize)).get(j + (int) (cellBlockInd.y * blockSize)));
            }
        }

        return rv;
    }
}
