import com.badlogic.gdx.math.Rectangle;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 21/08/14
 * Time: 20:34
 * To change this template use File | Settings | File Templates.
 */
public class Warp extends NSJEntity {
    private int warpX;
    private int warpY;
    private String mapDest;
    private int warpDestX;
    private int warpDestY;

    public Warp(int warpX, int warpY, String mapDest, int warpDestX, int warpDestY) {
        this.warpX = (warpX-1)* NSJEngine.TILE_SIZE;
        this.warpY = (warpY-1)*NSJEngine.TILE_SIZE;
        this.mapDest = mapDest;
        this.warpDestX = warpDestX*NSJEngine.TILE_SIZE;
        this.warpDestY = warpDestY*NSJEngine.TILE_SIZE;
        this.boundingBox = new Rectangle(this.warpX + 3, this.warpY + 3, NSJEngine.TILE_SIZE - 3,NSJEngine.TILE_SIZE - 3);
    }

    public int getWarpX() {
        return warpX;
    }

    public int getWarpY() {
        return warpY;
    }

    public String getMapDest() {
        return mapDest;
    }

    public int getWarpDestX() {
        return warpDestX;
    }

    public int getWarpDestY() {
        return warpDestY;
    }

    public boolean isAt(int x, int y) {
        return boundingBox.contains(x,y);
    }
}
