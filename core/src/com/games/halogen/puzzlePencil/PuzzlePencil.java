package com.games.halogen.puzzlePencil;

import com.games.halogen.gameEngine.HalogenGame;
import com.games.halogen.puzzlePencil.sudoku.SudokuScreen;
import com.games.halogen.puzzlePencil.infra.GameData;
import com.games.halogen.puzzlePencil.infra.PuzzlePencilInjector;

public class PuzzlePencil extends HalogenGame {
	@Override
	protected void init() {
		setScreen(new SudokuScreen(getDependencyInjector()));
	}

	@Override
	protected void setDependencyInjector() {
		this.di = new PuzzlePencilInjector(GameData.VIRTUAL_WIDTH, GameData.VIRTUAL_HEIGHT);
	}

	private PuzzlePencilInjector getDependencyInjector(){
		return getGameDependencyManager(PuzzlePencilInjector.class);
	}
}
