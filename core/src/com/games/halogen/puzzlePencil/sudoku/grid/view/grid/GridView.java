package com.games.halogen.puzzlePencil.sudoku.grid.view.grid;

import com.games.halogen.puzzlePencil.sudoku.grid.model.grid.Grid;
import com.games.halogen.puzzlePencil.sudoku.grid.view.SudokuObject;
import com.games.halogen.puzzlePencil.sudoku.world.SudokuLayoutManager;

import java.util.ArrayList;

public class GridView extends SudokuObject {
    private int dimensions;
    private ArrayList<CellView> cells;

    private Grid gridModel;

    public GridView(Grid gridModel) {
        this.dimensions = gridModel.getDimension();
        this.gridModel = gridModel;
    }

    @Override
    public void init() {
        SudokuLayoutManager lm = getCallbacks().getLayoutManager();
        lm.cellSize = (lm.gridSize / dimensions) * (1 - lm.cellMarginToSizeRatio);
        lm.cellMargin = (lm.gridSize / dimensions) * lm.cellMarginToSizeRatio / 2;

        setupGrid();
    }

    private void setupGrid() {
        createCells();
        addChildObject(new SudokuLines(), true);
    }

    private void createCells() {
        cells = new ArrayList<>();
        for(int row = 0; row< dimensions; row++){
            for(int col = 0; col< dimensions; col++){
                CellView c = new CellView(row, col);

                SudokuLayoutManager lm = getCallbacks().getLayoutManager();
                c.setSize(lm.cellSize + 2*lm.cellMargin, lm.cellSize + 2*lm.cellMargin);
                c.setPosition(col*c.getWidth(), row*c.getHeight());

                updateCellValue(row, col);
                updateCellMarks(row, col);

                cells.add(c);
                addChildObject(c);
            }
        }
    }

    public void setCellHighlight(int r, int c, CellHighlight highlightType) {
        getCellView(r, c).setHighlight(highlightType);
    }

    public void updateCellValue(int r, int c){
        getCellView(r, c).updateValue(gridModel.getCell(r, c));
    }

    public void updateCellValueColor(int r, int c){
        getCellView(r, c).updateValueColor(gridModel.getCell(r, c));
    }

    public void updateCellMarks(int r, int c){
        getCellView(r, c).updateMarks(gridModel.getCell(r, c));
    }

    private CellView getCellView(int r, int c){
        return cells.get(r*dimensions + c);
    }
}
