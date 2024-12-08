package com.nome.dessaporra.block;

import com.nome.dessaporra.core.GameObject;
import com.nome.dessaporra.core.IHasSprite;
import com.nome.dessaporra.Main;

import java.awt.*;
import java.util.List;

public abstract class Block extends GameObject {

    protected final int width, height;
    private boolean shouldBeDestroyed;

    public Block(List<IHasSprite> spriteList, float x, float y, int width, int height) {
        super(spriteList, x, y);
        this.width = width;
        this.height = height;
    }

    @Override
    public void update(double deltaTime) {
        Rectangle rect = new Rectangle((int)x, (int)y, width, height);
        Point pos = Main.getMousePos();
        Point playerPos = new Point((int)Main.getPlayer().getX(), (int)Main.getPlayer().getY());
        boolean isCloseEnough = playerPos.distance((int)x, (int)y) < miningDistance();
        if(pos != null && Main.getMouse(1) && rect.contains(pos) && isCloseEnough && !shouldBeDestroyed) {
            shouldBeDestroyed = true;
            onShouldBeDestroyed();
        }
    }

    public boolean shouldBeDestroyed() {
        return shouldBeDestroyed;
    }

    protected void onShouldBeDestroyed() {}

    protected abstract int miningDistance();

    public abstract int minSpawnX();
    public abstract int minSpawnY();
    public abstract int maxSpawnX();
    public abstract int maxSpawnY();
}
