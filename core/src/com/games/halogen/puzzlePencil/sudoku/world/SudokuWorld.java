package com.games.halogen.puzzlePencil.sudoku.world;

import com.badlogic.gdx.utils.viewport.Viewport;
import com.games.halogen.gameEngine.scene.world.GameWorld;
import com.games.halogen.puzzlePencil.infra.PuzzlePencilInjector;
import com.games.halogen.puzzlePencil.sudoku.grid.SudokuGrid;
import com.games.halogen.puzzlePencil.sudoku.grid.view.ui.Background;
import com.games.halogen.puzzlePencil.sudoku.grid.view.ui.NextButton;
import com.games.halogen.puzzlePencil.sudoku.grid.view.ui.keypad.KeyPad;

public class SudokuWorld extends GameWorld {
    private Background bg;
    private SudokuGrid sudokuGrid;
    private NextButton nextButton;
    private KeyPad keypad;

    public SudokuWorld(PuzzlePencilInjector injector){
        super(injector);

        bg = new Background();
        this.addGameObject(bg, true);

        sudokuGrid = new SudokuGrid(getData().gridDimensions);
        this.addGameObject(sudokuGrid.getView(), true);

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

        bg.setSize(vp.getWorldWidth(), vp.getWorldHeight());
        bg.setPosition(0, 0);

        //fixme: setting grid size has no effect on children sizes
        sudokuGrid.getView().setSize(lm.gridSize, lm.gridSize);
        sudokuGrid.getView().setPosition(lm.gridPos.x, lm.gridPos.y);

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

    void selectCell(int r, int c) {
        sudokuGrid.selectCell(r, c);
    }

    void deselectCell() {
        sudokuGrid.deselectCell();
    }

    void toggleInSelectedCell(int num) {
        sudokuGrid.toggleValInSelectedCell(num);
    }

    void toggleVisibleInSelectedCell(int num) {
        sudokuGrid.toggleVisibleMarkInSelectedCell(num);
    }

    void nextButtonRevealed(boolean isRevealed)
    {
        nextButton.setVisible(isRevealed);
    }
}
