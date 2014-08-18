import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;

import java.util.HashMap;

public class NSJMap {

    //Maps layer to objects on that layer
    public HashMap<Integer, List<NSJMapTile>> layerMapTiles = new HashMap<Integer, List<NSJMapTile>>();
    public HashMap<Integer, List<NSJEntity>> layerMapEntities = new HashMap<Integer, List<NSJEntity>>();

    private NSJEntity player;


    private static final int MAX_LAYERS = 10;

    //Tileset Map
    private TextureRegion[] textures;
    private TextureRegion[] npcs; //width = 24


    private void layerFromTileMap(int layerNum, String tileMap, NSJMapTile[] entityEncoding, int tileSize) {
        String[] rows = tileMap.split("\n");

        int y = 0;
        int x = 0;

        for (String row : rows) {
            String[] cols = row.split(",");
            for (String col : cols) {
                if (!col.equals(" ")) {
                    int id = Integer.parseInt(col);
                    addEntity(layerNum, entityEncoding[id].clone(x, y));
                }
                x += tileSize;
            }
            x = 0;
            y += tileSize;
        }
    }


    public NSJMap(NSJPlayer player) {

        textures = NSJSpriteSheet.spriteSheetToTextureArray(new TextureRegion(new Texture("assets/mapsheet.png")),NSJEngine.TILE_SIZE,NSJEngine.TILE_SIZE,0,0);
        npcs = NSJSpriteSheet.spriteSheetToTextureArray(new TextureRegion(new Texture("assets/npcsprites.png")),NSJEngine.TILE_SIZE,NSJEngine.TILE_SIZE,2,2);

        NSJMapTile floorTile = new NSJMapTile(0,-1,-1, NSJMapTile.MapTileType.OPEN);
        NSJMapTile wallTile = new NSJMapTile(9,-1,-1, NSJMapTile.MapTileType.SOLID);

        player.setTextures(npcs[74], npcs[74], npcs[66], npcs[66]);


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
                new NSJMapTile[] { floorTile, wallTile },NSJEngine.TILE_SIZE);



    }

    public void addEntity(int layerNum, NSJEntity entity) {
        //Add to layer map
        if (layerMapEntities.get(layerNum) == null)
            layerMapEntities.put(layerNum, new ArrayList<NSJEntity>());
        layerMapEntities.get(layerNum).add(entity);

        entity.setLayer(layerNum);
    }

    public void addEntity(int layerNum, NSJMapTile entity) {
        //Add to layer map
        if (layerMapTiles.get(layerNum) == null)
            layerMapTiles.put(layerNum, new ArrayList<NSJMapTile>());
        layerMapTiles.get(layerNum).add(entity);


        entity.setLayer(layerNum);
    }

    public void update() {
        /*List<NSJEntity> toRemove = new ArrayList<NSJEntity>();

        for (int layer : layerMapTiles.keySet()) {
            for (int i = 0; i <  layerMapTiles.get(layer).size(); i++) {
                NSJEntity entity = layerMapTiles.get(layer).get(i);

                if (layerMapTiles.get(layer).get(i) instanceof NSJDynamicEntity) {
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
        float scaleRatio = 0;
        int z = 0;

        //Combine layers
        /*Integer[] tileLayers = layerMapTiles.keySet().toArray(new Integer[layerMapTiles.keySet().size()]);
        Integer[] entityLayers =  layerMapEntities.keySet().toArray(new Integer[layerMapEntities.keySet().size()]);
        int[] layers = NSJArray.merge(tileLayers, entityLayers);*/

        //Renderer
        for (int layer = 0; layer < MAX_LAYERS; layer++) {
            if (layerMapTiles.get(layer) != null) {
                for (NSJMapTile entity : layerMapTiles.get(layer)) {
                    TextureRegion txt = textures[entity.getTextureId()];
                    spriteBatch.draw(txt.getTexture(), entity.getX() - offsetX - scaleRatio/2, entity.getY() - offsetY - scaleRatio/2, 0,0,16, 16,z+1,z+1,0,txt.getRegionX(),txt.getRegionY(),16,16,false,false);
                }
            }

            if (layerMapEntities.get(layer) != null) {
                for (NSJEntity entity : layerMapEntities.get(layer)) {

                    if (entity instanceof NSJPlayer)
                        ((NSJPlayer)entity).render(spriteBatch);
                    else
                        entity.render(spriteBatch, offsetX, offsetY);
                }
            }
        }

    }

    public List<NSJMapTile> getEntitiesAtPosition(Rectangle boundingBox, float curX, float curY) {



        //TODO speed up using octrees or something
        List<NSJMapTile> entities = new ArrayList<NSJMapTile>();

        for (int layer : layerMapTiles.keySet()) {
            for (NSJMapTile entity : layerMapTiles.get(layer)) {
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
