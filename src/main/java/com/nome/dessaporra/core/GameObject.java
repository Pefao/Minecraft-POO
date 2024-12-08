package com.nome.dessaporra.core;

import java.awt.*;
import java.util.List;

public abstract class GameObject implements IUpdatable, IRenderable, IHasSprite {

    protected float x;
    protected float y;
    protected Image sprite;

    public GameObject(List<IHasSprite> spriteList, float x, float y) {
        this.x = x;
        this.y = y;
        spriteList.add(this);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}
