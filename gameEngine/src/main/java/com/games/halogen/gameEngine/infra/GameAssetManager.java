package com.games.halogen.gameEngine.infra;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.util.HashMap;

public abstract class GameAssetManager {
    private AssetManager manager;

    //Common Textures
    private final HashMap<String, TextureRegion> commonTextures;
    private static final String SQUARE_REGION = "square_reg";

    //Fonts
    public Label.LabelStyle fontLabelStyle;

    public GameAssetManager(){
        commonTextures = new HashMap<>();
    }

    public void beginLoading(AssetManager manager){
        this.manager = manager;

        setupTTFFonts();

        loadDefaultFont();

        loadAssets();

        //todo: make asset loading async
        manager.finishLoading();

        getAssets();
    }

    private void loadDefaultFont() {
        FreetypeFontLoader.FreeTypeFontLoaderParameter params = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        params.fontFileName = "fonts/robotoMed.ttf";
        params.fontParameters.size = 64;
        manager.load("myFont.ttf", BitmapFont.class, params);

        manager.finishLoading();

        BitmapFont font = manager.get("myFont.ttf", BitmapFont.class);
        fontLabelStyle = new Label.LabelStyle(font,new Color(1,1,1,1));
    }

    private void setupTTFFonts(){
        manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(manager.getFileHandleResolver()));
        manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(manager.getFileHandleResolver()));
    }

    public abstract void loadAssets();

    public abstract void getAssets();

    public abstract void disposeAssets();

    public TextureRegion getSquareRegion(){
        if(!commonTextures.containsKey(SQUARE_REGION)) {
            Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
            pix.setColor(1, 1, 1, 1);
            pix.fill();

            commonTextures.put(SQUARE_REGION, new TextureRegion(new Texture(pix)));
            pix.dispose();
        }
        return commonTextures.get(SQUARE_REGION);
    }

    public void dispose(){
        manager.dispose();

        //dispose common textures
        for (String key: commonTextures.keySet()) {
            if(commonTextures.get(key) != null){
                commonTextures.get(key).getTexture().dispose();
            }
        }

        disposeAssets();
    }
}
