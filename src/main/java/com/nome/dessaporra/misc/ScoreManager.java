package com.nome.dessaporra.misc;

import com.nome.dessaporra.block.DiamondColor;
import com.nome.dessaporra.block.DiamondSize;
import com.nome.dessaporra.core.IRenderable;

import java.awt.*;

@SuppressWarnings("FieldCanBeLocal")
public class ScoreManager implements IRenderable {

    private int score;
    private final String DEFAULT_MESSAGE = "Pontos: ";
    private final int FONT_SIZE = 32;
    private final int LEFT_MARGIN = 5;
    private final int fontStyle;
    private final Color fontColor;

    public ScoreManager(int score, int fontStyle, Color fontColor) {
        this.score = score;
        this.fontStyle = fontStyle;
        this.fontColor = fontColor;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(this.fontColor);
        g.setFont(new Font("Arial", this.fontStyle, FONT_SIZE));
        g.drawString(DEFAULT_MESSAGE + score, LEFT_MARGIN, FONT_SIZE);
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public int getColorMultiplier(DiamondColor color) {
        return switch(color) {
            case NORMAL -> 1;
            case RED -> 2;
            case GREEN -> 3;
            case PURPLE -> 5;
        };
    }

    public int getSizeMultiplier(DiamondSize size) {
        return switch(size) {
            case NORMAL -> 1;
            case BIG -> 2;
            case VERY_BIG -> 4;
        };
    }
}
