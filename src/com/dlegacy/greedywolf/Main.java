package com.dlegacy.greedywolf;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        Game game = new Game();

        game.setPreferredSize(new Dimension(game.WIDTH * game.SCALE, game.HEIGHT * game.SCALE));

        JFrame frame = new JFrame(game.TITLE);
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        game.start();
    }
}
