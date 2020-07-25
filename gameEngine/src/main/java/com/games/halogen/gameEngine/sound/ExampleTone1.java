package com.games.halogen.gameEngine.sound;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.LineUnavailableException;

public class ExampleTone1{

    public static void main(String[] args){

        try {
            ExampleTone1.createTone(262, 100);
        } catch (LineUnavailableException lue) {
            System.out.println(lue);
        }
    }

    /** parameters are frequency in Hertz and volume
     **/
    public static void createTone(int Hertz, int volume)
            throws LineUnavailableException {
        /** Exception is thrown when line cannot be opened */

        float rate = 44100;
        byte[] buf;
        AudioFormat audioF;

        buf = new byte[1];
        audioF = new AudioFormat(rate,8,1,true,false);
        //sampleRate, sampleSizeInBits,channels,signed,bigEndian

        SourceDataLine sourceDL = AudioSystem.getSourceDataLine(audioF);
        sourceDL = AudioSystem.getSourceDataLine(audioF);
        sourceDL.open(audioF);
        sourceDL.start();

        for(int i=0; i<rate; i++){
            double angle = (i/rate)*Hertz*2.0*Math.PI;
            buf[0]=(byte)(Math.sin(angle)*volume);
            sourceDL.write(buf,0,1);
        }

        sourceDL.drain();
        sourceDL.stop();
        sourceDL.close();
    }
}
