package com.games.halogen.puzzlePencil.sudoku.grid;

import com.games.halogen.gameEngine.utils.Pair.IntPair;
import com.games.halogen.puzzlePencil.sudoku.generator.GridGenerator;
import com.games.halogen.puzzlePencil.sudoku.generator.GridGeneratorParameters;
import com.games.halogen.puzzlePencil.sudoku.grid.model.House;
import com.games.halogen.puzzlePencil.sudoku.grid.model.grid.Cell;
import com.games.halogen.puzzlePencil.sudoku.grid.model.grid.Grid;
import com.games.halogen.puzzlePencil.sudoku.grid.view.grid.CellHighlight;
import com.games.halogen.puzzlePencil.sudoku.grid.view.grid.GridView;

public class SudokuGrid {
    private Grid gridModel;
    private GridView gridView;

    private IntPair selectedCellCoordinates;

    public SudokuGrid(int gridDimensions) {
        gridModel = new Grid(gridDimensions);
        recreateGrid();

        gridView = new GridView(gridModel);
    }

    public GridView getView() {
        return gridView;
    }

    public void recreateGrid() {
        GridGenerator.generatePuzzle(gridModel);
    }

    public void selectCell(int r, int c) {
        selectedCellCoordinates = new IntPair(r, c);
        gridView.setCellHighlight(r, c, CellHighlight.SELECTED);
    }

    public void deselectCell() {
        if(selectedCellCoordinates != null) {
            int r = selectedCellCoordinates.getFirst();
            int c = selectedCellCoordinates.getSecond();
            gridView.setCellHighlight(r, c, CellHighlight.NONE);
        }
        selectedCellCoordinates = null;
    }

    public void toggleValInSelectedCell(int num) {
        if(selectedCellCoordinates == null){
            return;
        }
        int r = selectedCellCoordinates.getFirst();
        int c = selectedCellCoordinates.getSecond();
        gridModel.toggleCellValue(r, c, num);
        gridView.updateCellValue(r, c);

        for(House h: gridModel.getCell(r, c).getHouses()){
            for(Cell cell: h.getCells()){
                gridView.updateCellValueColor(cell.getCoordinates().getFirst(), cell.getCoordinates().getSecond());
            }
        }
    }

    public void toggleVisibleMarkInSelectedCell(int num) {
        int r = selectedCellCoordinates.getFirst();
        int c = selectedCellCoordinates.getSecond();
        gridModel.toggleVisibleMark(r, c, num);
        gridView.updateCellMarks(r, c);
    }
}
