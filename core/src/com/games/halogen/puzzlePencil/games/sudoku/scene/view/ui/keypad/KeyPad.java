package com.games.halogen.puzzlePencil.games.sudoku.scene.view.ui.keypad;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.games.halogen.puzzlePencil.games.sudoku.scene.view.SudokuObject;

import java.util.ArrayList;

public class KeyPad extends SudokuObject {
    private ArrayList<NumButton> buttons;

    public KeyPad(){
        buttons = new ArrayList<>();
    }

    @Override
    public void init() {
        setupKeys();
    }

    private void setupKeys() {
        for(int i=0;i<9;i++){
            final int num = i+1;
            NumButton butt = new NumButton(Integer.toString(i+1)){
                @Override
                protected void addUIListener() {
                    this.addListener(new InputListener(){
                        @Override
                        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                            getCallbacks().fillNumInActiveCell(num);
                            return super.touchDown(event, x, y, pointer, button);
                        }
                    });
                }
            };
            buttons.add(butt);
            this.addChildObject(butt);

            butt.setPosition(10 + i * 29,10);
            butt.setSize(25,25);
            butt.getTextButton().setTextHeight(25);

        }
    }
}
