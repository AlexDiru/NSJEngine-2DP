import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 18/08/14
 * Time: 12:16
 * To change this template use File | Settings | File Templates.
 */
public class NSJSpriteSheet {

    public static Sprite[] spriteSheetToTextureArray(TextureRegion spriteSheet, int tileWidth, int tileHeight, int gapBetweenSpritesX, int gapBetweenSpritesY) {


        TextureRegion[][] regions = spriteSheet.split( tileWidth, tileHeight);
        Sprite[] sprites = new Sprite[regions.length * regions[0].length];

        for (int y = 0; y < regions.length; y++) {
            for (int x = 0; x < regions[0].length; x++) {
                int rx = regions[y][x].getRegionX() + gapBetweenSpritesX * x;
                int ry = regions[y][x].getRegionY() + gapBetweenSpritesY * y;
                regions[y][x].setRegion(rx, ry, regions[y][x].getRegionWidth(), regions[y][x].getRegionHeight());
                sprites[y * regions[0].length + x] = new Sprite(regions[y][x]);
            }
        }

        return sprites;
    }
}
