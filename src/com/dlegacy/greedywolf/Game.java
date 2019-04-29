package com.dlegacy.greedywolf;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Game extends Canvas implements Runnable {

    public static final int WIDTH = 180;
    public static final int HEIGHT = WIDTH/7 * 9;
    public static final int SCALE = 3;
    public final static String TITLE = "The greedy wolf";

    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_BGR);
    private BufferedImage spriteSheet = null;

    BufferedImage player = null;

    public void init(){
        BufferedImageLoader loader = new BufferedImageLoader();

        try{

            spriteSheet = loader.loadImage("wolfsheet5.png");

        }catch (IOException e){
            e.printStackTrace();
        }

        SpriteSheet ss = new SpriteSheet(spriteSheet);
        player = ss.grabImage(1,1, 32, 32);
    }

    private boolean running = false;
    private Thread thread;

    public synchronized void start(){
        if(running)
            return;

        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop(){
        if(!running)
            return;

        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
                e.printStackTrace();
        }
        System.exit(1);
    }


    @Override
    public void run() {

        init();

        long lastTime = System.nanoTime();
        final double fpsTarget = 60.0;
        double ns = 1000000000 / fpsTarget;
        long timer = System.currentTimeMillis();

        double delta = 0;
        int frames = 0;
        int updates = 0;


        while(running){
            long nowTime = System.nanoTime();
            delta += (nowTime - lastTime) / ns;
            lastTime = nowTime;

            if(delta >= 1){
                update();
                updates++;
                delta--;
            }

            render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                System.out.println("Updated : " + updates + " frames : " + frames);
                frames = 0;
                updates = 0;
            }
        }
        stop();
    }

    private void update() {

    }

    private void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        g.drawImage(player, 100, 100, this);

        g.dispose();
        bs.show();

    }
}
