package com.games.halogen.puzzlePencil.sudoku.generator;

import java.util.Random;

public class GridGeneratorParameters {
    public static int seed = 1;
    private static Random random = new Random(seed);

    public static Random getRandom() {
        return random;
    }

    public static void setSeed(int seed){
        GridGeneratorParameters.seed = seed;
        random = new Random(seed);
    }
}
