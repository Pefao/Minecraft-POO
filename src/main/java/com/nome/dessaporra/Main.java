package com.nome.dessaporra;

import com.nome.dessaporra.audio.AudioManager;
import com.nome.dessaporra.block.Block;
import com.nome.dessaporra.block.DiamondBlock;
import com.nome.dessaporra.block.DiamondColor;
import com.nome.dessaporra.block.DiamondSize;
import com.nome.dessaporra.core.Control;
import com.nome.dessaporra.core.GameCanvas;
import com.nome.dessaporra.core.IHasSprite;
import com.nome.dessaporra.misc.ScoreManager;
import com.nome.dessaporra.player.Player;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    private static final List<IHasSprite> spriteList = new ArrayList<>();
    private static final String NAME = "Minecraft 2 (NOVOS DIAMANTES!!!)";
    private static final GameCanvas canvas = new GameCanvas(spriteList);
    private static final JFrame frame = new JFrame(NAME);
    private static final Player player = new Player(spriteList, 0, 0);
    private static final List<Block> blocks = new ArrayList<>() {{
        add(new DiamondBlock(spriteList, 300, 300, 100, 100, DiamondSize.NORMAL, DiamondColor.NORMAL));
    }};
    private static final ScoreManager scoreManager = new ScoreManager(0, Font.BOLD, Color.RED);

    private static void setupFrame() {
        frame.add(canvas);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        canvas.initBuffer();
    }

    private static void loadSprites() {
        spriteList.forEach(s -> {
            try {
                s.loadSprite();
            }catch(IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        setupFrame();
        loadSprites();
        AudioManager audioManager = new AudioManager(50);
        Random random = new Random();
        audioManager.play(AudioManager.SONGS.stream().skip(random.nextInt(AudioManager.SONGS.size())).findFirst().orElse(null));
        start();
    }

    public static void update(double deltaTime) {
        player.update(deltaTime);
        if(blocks.isEmpty()) {
            Random random = new Random();
            DiamondSize size = DiamondSize.values()[random.nextInt(DiamondSize.values().length)];
            DiamondColor color = DiamondColor.values()[random.nextInt(DiamondColor.values().length)];
            Block newBlock = new DiamondBlock(spriteList, 0, 0, 100, 100, size, color);
            newBlock.setX(random.nextInt(newBlock.maxSpawnX() - newBlock.minSpawnX()) + newBlock.minSpawnX());
            newBlock.setY(random.nextInt(newBlock.maxSpawnY() - newBlock.minSpawnY()) + newBlock.minSpawnX());
            try {
                newBlock.loadSprite();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            blocks.add(newBlock);
        }
        blocks.forEach(b -> b.update(deltaTime));
        blocks.removeIf(Block::shouldBeDestroyed);
    }

    public static void render() {
        Graphics g = canvas.beginRender();
        blocks.forEach(b -> b.render(g));
        player.render(g);
        scoreManager.render(g);
        canvas.endRender();
    }

    public static void start() {
        long lastTime = System.currentTimeMillis();
        while(frame.isVisible()) {
            long time = System.currentTimeMillis();
            double deltaTime = (time - lastTime) / 1000.;
            lastTime = time;
            update(deltaTime);
            render();
        }
    }

    public static Point getMousePos() {
        return canvas.getMousePosition();
    }

    public static boolean getMouse(int button) {
        return canvas.getMouse(button);
    }

    public static boolean getControl(Control control) {
        return canvas.getControl(control);
    }

    public static Player getPlayer() {
        return player;
    }

    public static ScoreManager getScoreManager() {
        return scoreManager;
    }
}
