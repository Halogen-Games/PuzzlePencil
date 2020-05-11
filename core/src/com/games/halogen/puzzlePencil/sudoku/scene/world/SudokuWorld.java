package com.games.halogen.puzzlePencil.sudoku.scene.world;

import com.badlogic.gdx.utils.viewport.Viewport;
import com.games.halogen.gameEngine.scene.world.GameWorld;
import com.games.halogen.puzzlePencil.sudoku.scene.view.grid.Cell;
import com.games.halogen.puzzlePencil.sudoku.scene.view.ui.keypad.KeyPad;
import com.games.halogen.puzzlePencil.infra.PuzzlePencilInjector;
import com.games.halogen.puzzlePencil.sudoku.scene.view.grid.SudokuGrid;
import com.games.halogen.puzzlePencil.sudoku.scene.view.ui.Background;
import com.games.halogen.puzzlePencil.sudoku.scene.view.ui.NextButton;

public class SudokuWorld extends GameWorld {
    private Background bg;
    private SudokuGrid sudokuGrid;
    private NextButton nextButton;
    private KeyPad keypad;

    public SudokuWorld(PuzzlePencilInjector injector){
        super(injector);

        bg = new Background();
        this.addGameObject(bg, true);

        sudokuGrid = new SudokuGrid(getData().numBlocks, getData().level);
        this.addGameObject(sudokuGrid, true);

        nextButton = new NextButton("Next Puzzle");
        this.addGameObject(nextButton, true);
        nextButton.setVisible(false);

        keypad = new KeyPad();
        this.addGameObject(keypad, true);
    }

    @Override
    public void layout() {
        super.layout();
        SudokuLayoutManager lm = getCallbacks().getLayoutManager();
        Viewport vp = getCallbacks().getDependencyInjector().getViewport();
        PuzzlePencilInjector di = getCallbacks().getDependencyInjector();

        bg.setSize(di.getVirtualWidth(), di.getVirtualHeight());
        bg.setPosition((vp.getWorldWidth() - getCallbacks().getDependencyInjector().getVirtualWidth())/2, 0);

        //fixme: setting grid size has no effect on children sizes
        sudokuGrid.setSize(lm.gridSize, lm.gridSize);
        sudokuGrid.setPosition(lm.gridPos.x, lm.gridPos.y);

        nextButton.setSize(lm.nextButtSize.x, lm.nextButtSize.y);
        nextButton.setPosition((vp.getWorldWidth() - nextButton.getWidth())/2,35);

        keypad.setSize(getCallbacks().getDependencyInjector().getVirtualWidth() * 0.8f, 30);
        keypad.setPosition((vp.getWorldWidth() - keypad.getWidth())/2, 100);
    }

    @Override
    protected void setWorldCallbacks() {
        this.callbacks = new SudokuWorldCallbacks(this);
    }

    @Override
    protected void setLayoutManager() {
        this.layoutManager = new SudokuLayoutManager();
    }

    @Override
    protected void setGameData() {
        this.gameWorldData = new SudokuWorldData();
    }

    @Override
    protected void initWorldSpecificDependencies() {

    }

    void createNewGrid() {
        sudokuGrid.recreateGrid();
    }

    SudokuWorldCallbacks getWorldCallbacks(){
        return getGameWorldCallbacks(SudokuWorldCallbacks.class);
    }

    SudokuLayoutManager getLayoutManager(){
        return getGameLayoutManager(SudokuLayoutManager.class);
    }

    SudokuWorldData getData(){
        return getGameData(SudokuWorldData.class);
    }

    PuzzlePencilInjector getDependencyInjector(){
        return getGameDependencyInjector(PuzzlePencilInjector.class);
    }

    public SudokuWorldCallbacks getCallbacks() {
        return getGameWorldCallbacks(SudokuWorldCallbacks.class);
    }

    void setActiveCell(Cell c) {
        sudokuGrid.setActiveCell(c);
    }

    void resetActiveCell() {
        sudokuGrid.resetActiveCell();
    }

    void toggleInActiveCell(int num) {
        sudokuGrid.toggleInActiveCell(num);
    }

    void toggleMiniumInActiveCell(int num) {
        sudokuGrid.toggleMiniumInActiveCell(num);
    }


}
