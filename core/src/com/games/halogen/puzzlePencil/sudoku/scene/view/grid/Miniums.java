package com.games.halogen.puzzlePencil.sudoku.scene.view.grid;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.games.halogen.puzzlePencil.sudoku.scene.view.SudokuObject;
import com.games.halogen.puzzlePencil.sudoku.scene.view.ui.general.TextLabel;

import java.util.ArrayList;

//todo: this is a model, move to models class
/** This class represents the set of numbers that can be possibly entered in a cell based on row, column and block values*/
public class Miniums extends SudokuObject {
    private ArrayList<Integer> nums;

    private ArrayList<TextLabel> labels;

    Miniums(){
        nums = new ArrayList<>();
        labels = new ArrayList<>();
    }

    @Override
    public void init() {

        for(int i=0; i<getCallbacks().getData().numRows; i++){
            TextLabel l = new TextLabel(Integer.toString(i+1), getCallbacks().getDependencyInjector().getAssetManager().fontLabelStyle);

            Vector2 alignment = getNumberAlignments(i);
            l.setAlignment(alignment.x, alignment.y);

            l.setSize(getWidth(), getHeight());
            l.setColor(getCallbacks().getLayoutManager().editableFontColor);

            l.setTextHeight(l.getHeight() * getCallbacks().getLayoutManager().miniumsTextRatio);

            labels.add(l);

            this.addChildObject(l);
        }
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        float pad = getWidth() * 0.1f;

        for(int i=0; i<labels.size(); i++){
            TextLabel l = labels.get(i);
            l.setSize(getWidth() - 2*pad, getHeight() - 2*pad);
            l.setPosition(pad, pad);
            l.setTextHeight(l.getHeight()/3);
        }
    }

    public void fillAll(int num) {
        this.nums.clear();
        for(int i=1;i<=num;i++){
            this.add(i);
        }
    }

    public int size() {
        return nums.size();
    }

    public void add(Integer i) {
        if(i < 1 || i > getCallbacks().getData().numRows){
            throw new RuntimeException("Miniums value out of range:" + i);
        }
        this.nums.add(i);
        labels.get(i-1).setVisible(true);
    }

    public Integer get(int i) {
        return nums.get(i);
    }

    public void remove(Integer i) {
        if(i < 1 || i > getCallbacks().getData().numRows){
            throw new RuntimeException("Miniums value out of range:" + i);
        }
        nums.remove(i);
        labels.get(i - 1).setVisible(false);
    }

    void set(Miniums miniums) {
        if(miniums == this){
            //don;t set me from myself
            return;
        }
        nums.clear();
        for(int i=0;i<miniums.size();i++){
            this.add(miniums.get(i));
        }
    }

    public void toggleNum(Integer num) {
        if(nums.contains(num)){
            this.remove(num);
        }else{
            this.add(num);
        }
    }

    public void clearAllMiniums() {
        for(int i = 0;i < nums.size();i++){
            this.remove(nums.get(i));
            i--;
        }
    }

    private Vector2 getNumberAlignments(int num){
        if(num > getCallbacks().getData().numRows){
            throw new RuntimeException("minium number greater than num rows, can't fetch alignment");
        }
        int numBlocks = getCallbacks().getData().numBlocks;
        int numRows = getCallbacks().getData().numRows;

        if(numRows == 1){
            return new Vector2(0.5f,0.5f);
        }

        float step = 1f/(numBlocks - 1);

        Vector2 rv = new Vector2();

        int div = num / numBlocks;
        int remainder = num % numBlocks;

        rv.set(remainder * step, 1 - div * step);

        return rv;
    }

    public boolean hasNum(Integer i) {
        return nums.contains(i);
    }

    public ArrayList<Integer> getAllNums() {
        return new ArrayList<>(nums);
    }
}
