package com.games.halogen.puzzlePencil.sudoku.viewOld.ui.keypad;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.games.halogen.puzzlePencil.sudoku.viewOld.SudokuObject;
import com.games.halogen.puzzlePencil.sudoku.viewOld.ui.general.SudokuTextButton;

import java.util.ArrayList;

public class KeyPad extends SudokuObject {
    private ArrayList<SudokuTextButton> buttons;

    private boolean fillingMiniums;

    public KeyPad(){
        buttons = new ArrayList<>();
    }

    @Override
    public void init() {
        setupNumKeys();
    }

    private void setupNumKeys() {
        for(int i=0;i<getCallbacks().getData().numRows;i++){
            final int num = i+1;
            SudokuTextButton butt = new SudokuTextButton(Integer.toString(i+1)){
                @Override
                protected void addUIListener() {
                    this.addListener(new InputListener(){
                        @Override
                        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                            if(fillingMiniums) {
                                getCallbacks().toggleMiniumInActiveCell(num);
                            }else{
                                getCallbacks().toggleNumInActiveCell(num);
                            }
                            return super.touchDown(event, x, y, pointer, button);
                        }
                    });
                }
            };
            buttons.add(butt);
        }

        fillingMiniums = false;
        buttons.add(new SudokuTextButton("M") {
            SudokuTextButton butt = this;
            @Override
            protected void addUIListener() {
                this.addListener(new InputListener(){
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        fillingMiniums = !fillingMiniums;
                        if(fillingMiniums){
                            butt.getTextButton().setText("m");
                        }else{
                            butt.getTextButton().setText("M");
                        }
                        return super.touchDown(event, x, y, pointer, button);
                    }
                });
            }
        });

        for(int i=0; i<buttons.size(); i++) {
            this.addChildObject(buttons.get(i));
        }
    }

    @Override
    public void layout() {
        super.layout();

        float buttonSize = (getWidth() / buttons.size()) * 0.95f;
        float buttMargin = ((getWidth() / buttons.size()) - buttonSize)/2;

        for(int i=0; i<buttons.size(); i++){
            SudokuTextButton butt = buttons.get(i);

            butt.setPosition(buttMargin + i * (buttonSize + 2*buttMargin), (getHeight() - buttonSize)/2);
            butt.setSize(buttonSize, buttonSize);
            butt.getTextButton().setTextHeight(buttonSize);
        }
    }
}
