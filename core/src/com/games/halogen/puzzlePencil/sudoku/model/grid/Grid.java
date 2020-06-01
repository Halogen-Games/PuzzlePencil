package com.games.halogen.puzzlePencil.sudoku.model;

import com.games.halogen.gameEngine.utils.Pair;
import com.games.halogen.gameEngine.utils.Pair.IntPair;

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
                h.addCell(getCell(row, col));
            }
            houses.add(h);
        }

        //create column houses
        for(int col=0; col<dimensions; col++){
            House h = new House(HouseType.COLUMN);
            for(int row=0; row<dimensions; row++){
                h.addCell(getCell(row, col));
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
                        h.addCell(getCell(br*houseWidth+i, bc*houseWidth+j));
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

    /*
    Calculates and returns penMarks for given cell
     */
    public PenMarks getPenMarks(int i, int j) {
        return getCell(i,j).getPenMarks();
    }

    public void setCellValue(Integer r, Integer c, Integer num) {
        getCell(r, c).setValue(num);

    }

    public void clearCellValue(Integer r, Integer c){
        getCell(r, c).clearValue();
    }

    public void clearAllCells() {
        //todo fill
    }

    private Cell getCell(int r, int c){
        return cells.get(r*dimensions + c);
    }
}
