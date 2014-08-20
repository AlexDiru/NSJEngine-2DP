import com.badlogic.gdx.math.Rectangle;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 18/08/14
 * Time: 12:50
 * To change this template use File | Settings | File Templates.
 */
public class NSJMapTile extends NSJEntity {

    public enum MapTileType {
        SOLID,
        OPEN,
        WARP,
        TREE,
        WATER,
        BOULDER
    }

    private int textureId;
    private MapTileType type;
    private int layer;

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }


    public NSJMapTile(int textureId, int x, int y, MapTileType type) {
        this.textureId = textureId;
        this.x = x;
        this.y = y;
        this.type = type;
        boundingBox = new Rectangle(x,y,12,12);
    }

    public NSJMapTile clone(int newX, int newY) {
        return new NSJMapTile(textureId, newX, newY, type);
    }

    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    public int getTextureId() {
        return textureId;
    }

    public void setTextureId(int textureId) {
        this.textureId = textureId;
    }

    public float getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public MapTileType getType() {
        return type;
    }

    public void setType(MapTileType type) {
        this.type = type;
    }


}
