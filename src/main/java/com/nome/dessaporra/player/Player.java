package com.nome.dessaporra.player;

import com.nome.dessaporra.Main;
import com.nome.dessaporra.core.Control;
import com.nome.dessaporra.core.GameObject;
import com.nome.dessaporra.core.IHasSprite;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("FieldCanBeLocal")
public class Player extends GameObject {

    private final double accelX = 200;
    private final double accelY = 200;
    private final double deccelX = 100;
    private final double deccelY = 100;
    private double speedX = 0;
    private double speedY = 0;
    private static final int WIDTH = 150;
    private static final int HEIGHT = 150;

    public Player(List<IHasSprite> spriteList, float x, float y) {
        super(spriteList, x, y);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(this.sprite, (int)x, (int)y, WIDTH, HEIGHT, null);
    }

    @Override
    public void update(double deltaTime) {
        double cx = 0, cy = 0;
        if(Main.getControl(Control.RIGHT)) cx += 1;
        if(Main.getControl(Control.LEFT)) cx -= 1;
        if(Main.getControl(Control.DOWN)) cy += 1;
        if(Main.getControl(Control.UP)) cy -= 1;
        double mag = Math.sqrt(cx*cx + cy*cy);
        if(mag > 0) {
            cx /= mag;
            cy /= mag;
        }
        speedX += cx*accelX*deltaTime;
        speedY += cy*accelY*deltaTime;
        if(speedX > 0) speedX = Math.max(speedX - deccelX*deltaTime, 0);
        else if(speedX < 0) speedX = Math.min(speedX + deccelX*deltaTime, 0);
        if(speedY > 0) speedY = Math.max(speedY - deccelY*deltaTime, 0);
        else if(speedY < 0) speedY = Math.min(speedY + deccelY*deltaTime, 0);
        x += speedX*deltaTime;
        y += speedY*deltaTime;
    }

    @Override
    public void loadSprite() throws IOException {
        this.sprite = ImageIO.read(Objects.requireNonNull(this.getClass().getResource("/steve.png")));
    }
}
