import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;

import java.util.HashMap;

public class NSJMap {

    //Maps layer to objects on that layer
    public HashMap<Integer, List<NSJMapTile>> layerMap = new HashMap<Integer, List<NSJMapTile>>();

    private NSJEntity player;
    private List<NSJEntity> entities = new ArrayList<NSJEntity>();


    //Tileset Map
    private TextureRegion[] textures;


    private void layerFromTileMap(int layerNum, String tileMap, NSJMapTile[] entityEncoding, int tileSize) {
        String[] rows = tileMap.split("\n");

        int y = 0;
        int x = 0;

        for (String row : rows) {
            String[] cols = row.split(",");
            for (String col : cols) {
                if (!col.equals(" ")) {
                    int id = Integer.parseInt(col);
                    addEntity(layerNum, entityEncoding[id].clone(x,y));
                }
                x += tileSize;
            }
            x = 0;
            y += tileSize;
        }
    }


    public NSJMap(NSJEntity player) {
        //Texture floor = new Texture("assets/floor.png");
        //Texture me = new Texture("assets/ship.png");
       // Texture marcus = new Texture("assets/bullet.png");
        //Texture churly = new Texture("assets/enemybullet.png");

        textures = NSJSpriteSheet.spriteSheetToTextureArray(new TextureRegion(new Texture("assets/mapsheet.png")),16,16);

        NSJMapTile floorTile = new NSJMapTile(0,-1,-1, NSJMapTile.MapTileType.OPEN);
        NSJMapTile wallTile = new NSJMapTile(9,-1,-1, NSJMapTile.MapTileType.SOLID);


        this.player = player;


        /*for (int x = 0; x < 32 * 10; x += 32)
            for (int y = 0; y < 32 * 10; y += 32)
                addEntity(0, new NSJEntity(floor, x, y, 32, 32));

        for (int x = 0; x < 32 * 10; x += 32)
            for (int y = 0; y < 32 * 10; y += 32)
                if (x == 0 || y == 0 || x == 32 * 9 || y == 32 * 9) {
                    NSJEntity e = new NSJEntity(wall, x, y,32,32);
                    e.setCanPlayerWalkThrough(false);
                    addEntity(1, e);
                }*/

        layerFromTileMap(0,
                " , ,1,1,1,1,1,1,1, , \n" +
                " ,1,0,0,0,0,0,0,0,1, \n" +
                " ,1,0,0,0,0,0,0,0,1, \n" +
                "1,0,0,0,0,0,0,0,0,0,1\n" +
                "1,0,0,0,0,0,0,0,0,0,1\n" +
                "1,0,0,0,0,0,0,0,0,0,1\n" +
                "1,0,0,0,0,0,0,0,0,0,1\n" +
                "1,0,0,0,0,0,0,0,0,0,1\n" +
                "1,0,0,0,0,0,0,0,0,0,1\n" +
                "1,0,0,0,0,0,0,0,0,0,1\n" +
                "1,0,0,0,0,0,0,0,0,0,1\n" +
                "1,0,0,0,0,0,0,0,0,0,1\n" +
                " ,1,0,0,0,0,0,0,0,1, \n" +
                " ,1,0,0,0,0,0,0,0,1, \n" +
                " , ,1,1,1,0,1,1,1,1,1,1,1, \n" +
                " , ,1,0,1,0,0,0,0,0,0,0,0,1\n" +
                " ,1,0,0,0,1,1,1,1,1,1,1,0,1\n" +
                " ,1,0,0,0,0,0,0,0,0,0,1,0,1\n" +
                " , ,1,0,0,0,1,0,0,0,1,0,0,1\n" +
                " , , ,1,0,0,0,1,0,0,1,0,0,0,1\n" +
                " , , , ,1,0,0,0,0,1,0,0,0,0,0,1\n"+
                " , , , ,1,0,0,0,0,1,0,0,0,0,0,1\n"+
                " , , , , ,1,0,0,0,1,0,0,0,0,0,1\n"+
                " , , , , , ,1,0,0,1,0,0,0,0,0,1\n" +
                " , , , , , , ,1,0,0,0,0,0,0,0,1\n" +
                " , , , , , , , ,1,1,1,1,1,1,1, \n",
                new NSJMapTile[] { floorTile, wallTile },16);



    }

    public void addEntity(int layerNum, NSJEntity entity) {
        entities.add(entity);
        entity.setLayer(layerNum);
    }

    public void addEntity(int layerNum, NSJMapTile entity) {
        //Add to layer map
        if (layerMap.get(layerNum) == null)
            layerMap.put(layerNum, new ArrayList<NSJMapTile>());
        layerMap.get(layerNum).add(entity);


        entity.setLayer(layerNum);
    }

    public void update() {
        /*List<NSJEntity> toRemove = new ArrayList<NSJEntity>();

        for (int layer : layerMap.keySet()) {
            for (int i = 0; i <  layerMap.get(layer).size(); i++) {
                NSJEntity entity = layerMap.get(layer).get(i);

                if (layerMap.get(layer).get(i) instanceof NSJDynamicEntity) {
                    NSJDynamicEntity dynamicEntity = (NSJDynamicEntity)entity;
                    dynamicEntity.update(this);
                }

                if (entity instanceof NSJCharacter || entity instanceof NSJAI) {
                    ((NSJCharacter)entity).update(this);
                }

                if (entity.isDestroyed())
                    toRemove.add(entity);
            }
        }

        for (NSJEntity rem : toRemove) {
            destroyEntity(rem);
        }*/
    }

    public void render(SpriteBatch spriteBatch, int offsetX, int offsetY) {
        for (int layer : layerMap.keySet()) {
            for (NSJMapTile entity : layerMap.get(layer)) {
                spriteBatch.draw(textures[entity.getTextureId()], entity.getX(), entity.getY());
                //entity.render(spriteBatch, offsetX, offsetY);
            }
        }
    }

    public List<NSJMapTile> getEntitiesAtPosition(Rectangle boundingBox, float curX, float curY) {



        //TODO speed up using octrees or something
        List<NSJMapTile> entities = new ArrayList<NSJMapTile>();

        for (int layer : layerMap.keySet()) {
            for (NSJMapTile entity : layerMap.get(layer)) {
               if (boundingBox == null && entity.getBoundingBox().contains(curX,curY)) {
                    entities.add(entity);
                } else if (boundingBox != null && entity.getBoundingBox().overlaps(boundingBox))
                   entities.add(entity);
            }
        }

        return entities;


    }
    public NSJEntity getPlayer() {
        return player;
    }

}
