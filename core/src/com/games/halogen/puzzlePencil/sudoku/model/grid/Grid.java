package com.games.halogen.puzzlePencil.sudoku.model.grid;

import com.games.halogen.gameEngine.utils.Pair.IntPair;
import com.games.halogen.puzzlePencil.sudoku.model.House;
import com.games.halogen.puzzlePencil.sudoku.model.HouseType;

import java.util.ArrayList;

/*
When cells are filled, pen marks of other cells are updated
 */
public class Grid {
    private int dimensions;
    private ArrayList<Cell> cells;
    private ArrayList<House> houses;

    public Grid(int dimensions) {
        this.dimensions = dimensions;
        createCells();
        createHouses();
    }

    private void createCells() {
        cells = new ArrayList<>();
        for(int row=0;row<dimensions; row++){
            for(int col=0; col<dimensions; col++) {
                cells.add(new Cell(new IntPair(row,col), dimensions));
            }
        }
    }

    /*
    Create houses array
    Block definitions are read from a file
     */
    private void createHouses() {
        houses = new ArrayList<>();

        //create row houses
        for(int row=0; row<dimensions; row++){
            House h = new House(HouseType.ROW);
            for(int col=0; col<dimensions; col++){
                Cell c = getCell(row, col);
                h.addCell(c);
                c.addHouse(h);
            }
            houses.add(h);
        }

        //create column houses
        for(int col=0; col<dimensions; col++){
            House h = new House(HouseType.COLUMN);
            for(int row=0; row<dimensions; row++){
                Cell c = getCell(row, col);
                h.addCell(c);
                c.addHouse(h);
            }
            houses.add(h);
        }

        //todo: create block houses from a file
        //creating blocks as normal sudoku
        int houseWidth = (int)Math.sqrt(dimensions);
        for(int br = 0; br<houseWidth; br++){
            for(int bc = 0; bc < houseWidth; bc++) {
                House h = new House(HouseType.BLOCK);
                for(int i=0; i<houseWidth; i++){
                    for(int j=0; j<houseWidth; j++){
                        Cell c = getCell(br*houseWidth+i, bc*houseWidth+j);
                        h.addCell(c);
                        c.addHouse(h);
                    }
                }

                houses.add(h);
            }
        }
    }

    public int getDimension() {
        return dimensions;
    }

    public ArrayList<House> getHouses(HouseType houseType) {
        ArrayList<House> rv = new ArrayList<>();
        for(House h: houses){
            if(h.getType() == houseType){
                rv.add(h);
            }
        }

        return rv;
    }

    public void setCellValue(Integer r, Integer c, Integer num) {
        if(num < 1 || num > dimensions){
            throw new RuntimeException("Can;t set cell Value. Value out of dimensions : " + num);
        }
        Cell cell = getCell(r, c);
        cell.setValue(num);
        cell.getPenMarks().removeAllMarks();

        for(House h: cell.getHouses()){
            h.removePencilMarkFromCells(num);
        }
    }

    public void clearCellValue(Integer r, Integer c){
        Cell cell = getCell(r, c);

        cell.clearValue();
        for(House h: cell.getHouses()){
            for(Cell c1: h.getCells()){
                updateCellMarks(c1);
            }
        }
    }

    public void clearAllCells() {
        for(Cell c:cells){
            c.clearValue();
        }
    }

    public Cell getCell(int r, int c){
        return cells.get(r*dimensions + c);
    }

    private void updateCellMarks(Cell c){
        for(int  i=1; i<=getDimension(); i++){
            c.getPenMarks().addMark(i);
            for(House h: c.getHouses()){
                if(h.hasCellWithNum(i)){
                    c.getPenMarks().removeMark(i);
                    break;
                }
            }
        }
    }
}
