package com.nome.dessaporra.block;

import com.nome.dessaporra.Main;
import com.nome.dessaporra.core.GameCanvas;
import com.nome.dessaporra.core.IHasSprite;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.RGBImageFilter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class DiamondBlock extends Block {

    private Image sprite;
    private static final int MINING_DISTANCE = 150;

    private static final int MIN_SPAWN_X = 100;
    private static final int MIN_SPAWN_Y = 100;
    private static final int MAX_SPAWN_X = GameCanvas.WIDTH - 100;
    private static final int MAX_SPAWN_Y = GameCanvas.HEIGHT - 100;
    private final DiamondSize size;
    private final DiamondColor color;

    private static int getDiamondScale(DiamondSize size) {
        return switch(size) {
            case NORMAL -> 1;
            case BIG -> 2;
            case VERY_BIG -> 3;
        };
    }

    public DiamondBlock(List<IHasSprite> spriteList, float x, float y, int width, int height, DiamondSize size, DiamondColor color) {
        super(spriteList, x, y, width*getDiamondScale(size), height*getDiamondScale(size));
        this.size = size;
        this.color = color;
    }

    @Override
    protected void onShouldBeDestroyed() {
        int score = Main.getScoreManager().getColorMultiplier(this.color)*Main.getScoreManager().getSizeMultiplier(this.size);
        Main.getScoreManager().setScore(Main.getScoreManager().getScore()+score);
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
    }

    @Override
    protected int miningDistance() {
        return MINING_DISTANCE*getDiamondScale(size);
    }

    @Override
    public int minSpawnX() {
        return MIN_SPAWN_X;
    }

    @Override
    public int minSpawnY() {
        return MIN_SPAWN_Y;
    }

    @Override
    public int maxSpawnX() {
        return MAX_SPAWN_X;
    }

    @Override
    public int maxSpawnY() {
        return MAX_SPAWN_Y;
    }

    @Override
    public void render(Graphics g) {
        ImageFilter filter = new RGBImageFilter() {
            @Override
            public int filterRGB(int x, int y, int rgb) {
                int filter = switch(color) {
                    case NORMAL -> 1;
                    case RED -> 0xFF0000;
                    case GREEN -> 0x00FF00;
                    case PURPLE -> 0x6B03FC;
                };
                return rgb * filter;
            }
        };
        Image sprite = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(this.sprite.getSource(), filter));
        g.drawImage(sprite, (int)x, (int)y, width, height, null);
    }

    @Override
    public void loadSprite() throws IOException {
        sprite = ImageIO.read(Objects.requireNonNull(this.getClass().getResource("/diamond.png")));
    }
}
