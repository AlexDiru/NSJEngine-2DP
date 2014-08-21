import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Rectangle;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 18/08/14
 * Time: 12:50
 * To change this template use File | Settings | File Templates.
 */
public class NSJMapTile extends NSJEntity {

    //Maps tile ID to tile
    private static HashMap<Integer, NSJMapTile> tileDatabase;

    public static NSJMapTile getTile(int id) {
        NSJMapTile tile = tileDatabase.get(id);
        if (tile == null)
            System.out.println("Tile " + id + " not found");
        return tile;
    }

    public static void loadDatabase(String fileName) {
        try {
            List<String> lines = FileUtils.readLines(Gdx.files.internal("assets/tiledb.txt").file());


            tileDatabase = new HashMap<Integer, NSJMapTile>();

            for (String line : lines) {
                String[] cells = line.split(",");
                int id = Integer.parseInt(cells[0]);
                MapTileType type = MapTileType.valueOf(cells[1]);
                tileDatabase.put(id, new NSJMapTile(id, -1,-1,type));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public enum MapTileType {
        SOLID,
        OPEN,
        WARP,
        TREE,
        WATER,
        WILD,
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
