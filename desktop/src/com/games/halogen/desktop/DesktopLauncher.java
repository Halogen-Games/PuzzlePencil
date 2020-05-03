package com.games.halogen.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.games.halogen.puzzlePencil.PuzzlePencil;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.width = PuzzlePencil.VIRTUAL_WIDTH;
		config.height = PuzzlePencil.VIRTUAL_HEIGHT;

		config.samples = 4;

		new LwjglApplication(new PuzzlePencil(), config);
	}
}
