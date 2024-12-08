package com.nome.dessaporra.core;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class GameCanvas extends Canvas implements KeyListener, MouseListener, IHasSprite {

    public static final int WIDTH = 900;
    public static final int HEIGHT = 600;
    private BufferStrategy bufferStrategy;
    private BufferedImage image;
    private Graphics g;
    private Image background;

    private final Map<Control, List<Integer>> keyAssociations = Map.of(
            Control.UP,    List.of(KeyEvent.VK_W, KeyEvent.VK_UP),
            Control.DOWN,  List.of(KeyEvent.VK_S, KeyEvent.VK_DOWN),
            Control.RIGHT, List.of(KeyEvent.VK_D, KeyEvent.VK_RIGHT),
            Control.LEFT,  List.of(KeyEvent.VK_A, KeyEvent.VK_LEFT)
    );
    private final Map<Control, Boolean> keyStatus = new HashMap<>() {{
        put(Control.UP,    false);
        put(Control.DOWN,  false);
        put(Control.RIGHT, false);
        put(Control.LEFT,  false);
    }};

    private final Map<Integer, Boolean> mouseStatus = new HashMap<>() {{
        put(1, false);
        put(2, false);
    }};

    public GameCanvas(List<IHasSprite> spriteList) {
        spriteList.add(this);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.addKeyListener(this);
        this.addMouseListener(this);
    }

    public void initBuffer() {
        this.createBufferStrategy(2);
        bufferStrategy = this.getBufferStrategy();
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    }

    public Graphics beginRender() {
        g = image.getGraphics();
        g.drawImage(this.background, 0, 0, WIDTH, HEIGHT, null);
        return g;
    }

    public void endRender() {
        g.dispose();
        g = bufferStrategy.getDrawGraphics();
        g.drawImage(image, 0, 0, WIDTH, HEIGHT, null);
        bufferStrategy.show();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public boolean getControl(Control control) {
        return keyStatus.get(control);
    }

    public boolean getMouse(int button) {
        return mouseStatus.get(button);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keyAssociations.entrySet().stream()
                .filter(s -> s.getValue().contains(e.getKeyCode()))
                .map(Map.Entry::getKey)
                .findFirst()
                .ifPresent(control -> keyStatus.put(control, true));
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyAssociations.entrySet().stream()
                .filter(s -> s.getValue().contains(e.getKeyCode()))
                .map(Map.Entry::getKey)
                .findFirst()
                .ifPresent(control -> keyStatus.put(control, false));
    }

    @Override
    public void loadSprite() throws IOException {
        this.background = ImageIO.read(Objects.requireNonNull(this.getClass().getResource("/cave.png")));
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(!mouseStatus.containsKey(e.getButton())) {
            return;
        }
        mouseStatus.put(e.getButton(), true);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(!mouseStatus.containsKey(e.getButton())) {
            return;
        }
        mouseStatus.put(e.getButton(), false);
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
