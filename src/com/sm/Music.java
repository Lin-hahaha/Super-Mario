package com.sm;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Music {
    public Music() throws FileNotFoundException, JavaLayerException {
        Player player;
        String string=System.getProperty("user.dir")+"/src/Music/music.wav";
        BufferedInputStream name =new BufferedInputStream(new FileInputStream(string));
        player =new Player(name);
        player.play();
    }
}
