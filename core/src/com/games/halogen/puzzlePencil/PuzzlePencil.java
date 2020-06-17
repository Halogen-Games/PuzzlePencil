package com.games.halogen.puzzlePencil;

import com.games.halogen.gameEngine.HalogenGame;
import com.games.halogen.puzzlePencil.shader.ShaderScreen;
import com.games.halogen.puzzlePencil.sudoku.SudokuScreen;
import com.games.halogen.puzzlePencil.infra.PuzzlePencilInjector;

public class PuzzlePencil extends HalogenGame {
	public static final int VIRTUAL_WIDTH = 1920;
	public static final int VIRTUAL_HEIGHT = 1080;

	@Override
	protected void init() {
		setScreen(new ShaderScreen(getDependencyInjector(), null));
	}

	@Override
	protected void setDependencyInjector() {
		this.di = new PuzzlePencilInjector(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
	}

	private PuzzlePencilInjector getDependencyInjector(){
		return getGameDependencyManager(PuzzlePencilInjector.class);
	}
}
