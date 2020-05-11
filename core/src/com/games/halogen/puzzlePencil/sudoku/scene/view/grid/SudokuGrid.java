package com.games.halogen.puzzlePencil.sudoku.scene.view.grid;

import com.badlogic.gdx.Gdx;
import com.games.halogen.puzzlePencil.sudoku.scene.view.SudokuObject;
import com.games.halogen.puzzlePencil.sudoku.scene.world.SudokuLayoutManager;
import com.games.halogen.puzzlePencil.sudoku.utils.SudokuGenerator;

import java.util.ArrayList;

public class SudokuGrid extends SudokuObject {
    private int numBlocks;
    private int numRows;
    private int level;

    private Cell activeCell;

    private ArrayList<ArrayList<Cell>> cells;
    private ArrayList<ArrayList<Cell>> tempCells;

    public SudokuGrid(int numBlocks, int level) {
        this.numBlocks = numBlocks;
        this.numRows = numBlocks*numBlocks;
        this.level = level;
    }

    @Override
    public void init() {
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

    private void createCells() {
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
        clearAllMiniums();
    }

    private void clearAllMiniums() {
        for(int i=0;i<cells.size();i++){
            for(int j=0; j<cells.get(0).size(); j++){
                getCell(i, j).getMiniums().removeAllMiniums();
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
        return this.level;
    }

    public int getBlockSize() {
        return numBlocks;
    }

    public int getNumRows() {
        return numRows;
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

    public void toggleInActiveCell(int num) {
        if(activeCell == null || !activeCell.isEditable()){
            return;
        }

        if(activeCell.getValue() == num){
            activeCell.setEmpty();
        }else {
            activeCell.setValue(num);
        }
    }

    public void toggleMiniumInActiveCell(int num) {
        if(activeCell == null || !activeCell.isEditable()){
            return;
        }

        activeCell.toggleMinium(num);
    }
}
