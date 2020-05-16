package com.games.halogen.puzzlePencil.sudoku.scene.view.grid;

import com.badlogic.gdx.Gdx;
import com.games.halogen.puzzlePencil.sudoku.scene.view.SudokuObject;
import com.games.halogen.puzzlePencil.sudoku.scene.world.SudokuLayoutManager;
import com.games.halogen.puzzlePencil.sudoku.utils.SudokuGenerator;
import com.games.halogen.puzzlePencil.sudoku.utils.SudokuUtils;

import java.util.ArrayList;

public class SudokuGrid extends SudokuObject {
    private int numBlocks;

    private Cell activeCell;
    private int emptyCells;

    private ArrayList<ArrayList<Cell>> cells;
    private ArrayList<ArrayList<Cell>> tempCells;

    public SudokuGrid(int numBlocks) {
        this.numBlocks = numBlocks;
    }

    @Override
    public void init() {
        int numRows = getCallbacks().getData().numRows;
        SudokuLayoutManager lm = getCallbacks().getLayoutManager();
        lm.cellSize = (lm.gridSize / numRows) * (1 - lm.cellMarginToSizeRatio);
        lm.cellMargin = (lm.gridSize / numRows) * lm.cellMarginToSizeRatio / 2;

        setupGrid();
        recreateGrid();
    }

    private void setupGrid() {
        createCells();
        addChildObject(new SudokuLines(), true);
    }

    private void createCells() {
        SudokuLayoutManager lm = getCallbacks().getLayoutManager();
        int numRows = getCallbacks().getData().numRows;
        cells = new ArrayList<>();
        for(int i = 0; i< numRows; i++){
            ArrayList<Cell> row = new ArrayList<>();
            for(int j = 0; j< numRows; j++){
                Cell c = new Cell(i, j);
                row.add(c);
                addChildObject(c);
            }
            cells.add(row);
        }

        tempCells = new ArrayList<>();
        for(int i = 0; i< numRows; i++){
            ArrayList<Cell> row = new ArrayList<>();
            for(int j = 0; j< numRows; j++){
                Cell c = new Cell(i, j);
                row.add(c);
                addChildObject(c);
                c.setVisible(false);
            }
            tempCells.add(row);
        }
    }

    public void recreateGrid() {
        SudokuGenerator.generate(this);
        freezeFilledCells();
//        clearAllMiniums();
        emptyCells = getEmptyCellCount();
    }

    private int getEmptyCellCount() {
        int rv = 0;
        for(int i=0; i<cells.size(); i++){
            for(int j=0; j<cells.get(0).size(); j++){
                if(getCell(i,j).isEmpty()){
                    rv++;
                }
            }
        }

        System.out.println(rv);

        return rv;
    }

    private void freezeFilledCells(){
        //mark cells editable
        for(int i=0;i<cells.size();i++){
            for(int j=0; j<cells.get(0).size(); j++){
                if(getCell(i,j).isEmpty()) {
                    getCell(i, j).setEditable(true);
                }else{
                    getCell(i, j).setEditable(false);
                }
            }
        }
    }

    private void clearAllMiniums() {
        for(int i=0;i<cells.size();i++){
            for(int j=0; j<cells.get(0).size(); j++){
                getCell(i, j).getMiniums().clearAllMiniums();
            }
        }
    }

    /*
    Functions specific to the grid's view
     */
    @Override
    public void modelUpdated() {
        //todo: fill
    }

    public void saveState(){
        for(int i=0; i<cells.size(); i++){
            for(int j=0; j<cells.get(0).size();j++){
                tempCells.get(i).get(j).setValue(cells.get(i).get(j).getValue());
            }
        }
    }

    public void loadState(){
        //todo:fill
        if(tempCells == null){
            Gdx.app.log(this.getClass().getSimpleName(), "No state to load from");
            return;
        }

        for(int i=0; i<cells.size(); i++){
            for(int j=0; j<cells.get(0).size();j++){
                cells.get(i).get(j).setValue(tempCells.get(i).get(j).getValue());
            }
        }
    }

    /*
    Setters and getters
     */
    public void setValue(int i, int j, int value) {
        cells.get(i).get(j).setValue(value);
    }

    public int getValue(int i, int j) {
        return cells.get(i).get(j).getValue();
    }

    public Cell getCell(int i, int j) {
        return cells.get(i).get(j);
    }

    public int getLevel() {
        return getCallbacks().getData().level;
    }

    public int getNumRows() {
        return getCallbacks().getData().numRows;
    }

    public void setActiveCell(Cell c){
        this.activeCell = c;
    }

    public void resetActiveCell() {
        if(activeCell == null){
            return;
        }
        activeCell.setActive(false);
        activeCell = null;
    }

    public void toggleValInActiveCell(int num) {
        if(activeCell == null || !activeCell.isEditable()){
            return;
        }

        if(activeCell.getValue() == num){
            activeCell.setEmpty();
            emptyCells++;
        }else {
            activeCell.setValue(num);
            emptyCells--;
        }

        SudokuUtils.updateCellValidityOnUpdate(this, activeCell);

        SudokuUtils.findMiniums(this);

        getCallbacks().nextButtonRevealed(emptyCells == 0 && getInvalidCellCount() == 0);
    }

    private int getInvalidCellCount(){
        int rv = 0;
        for(int i=0; i<cells.size(); i++){
            for(int j=0; j<cells.get(0).size(); j++){
                if(!getCell(i,j).isValid()){
                    rv++;
                }
            }
        }

        return rv;
    }

    public void toggleMiniumInActiveCell(int num) {
        if(activeCell == null || !activeCell.isEditable()){
            return;
        }

        activeCell.toggleMinium(num);
    }

    public int getNumBlocks() {
        return numBlocks;
    }

    public void clearAllCells() {
        for(int i=0; i<getNumRows(); i++){
            for(int j=0; j<getNumRows(); j++){
                getCell(i,j).setEmpty();
                getCell(i,j).getMiniums().clearAllMiniums();
            }
        }
    }
}
