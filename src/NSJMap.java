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

        NSJMapTile floorTile = NSJMapTile.getTile(15);
        NSJMapTile wallTile = NSJMapTile.getTile(9);
        NSJMapTile wildTile = NSJMapTile.getTile(17);

        NSJAI testAi = new NSJAI(NSJAI.RandomMovement, 128,128);
        addEntity(1,testAi);
        NSJAI testAi2 = new NSJAI(NSJAI.RandomMovement, 128-32,128);
        addEntity(1,testAi2);
        NSJAI testAi3 = new NSJAI(NSJAI.RandomMovement, 128-16,128-16);
        addEntity(1,testAi3);
        NSJAI testAi4 = new NSJAI(NSJAI.RandomMovement, 128,128-32);
        addEntity(1,testAi4);
        NSJAI testAi5 = new NSJAI(NSJAI.RandomMovement, 128-32,128-32);
        addEntity(1,testAi5);

        testAi.setTextures(npcs[1],npcs[2],npcs[3]);
        testAi2.setTextures(npcs[1],npcs[2],npcs[3]);
        testAi3.setTextures(npcs[1],npcs[2],npcs[3]);
        testAi4.setTextures(npcs[1],npcs[2],npcs[3]);
        testAi5.setTextures(npcs[1],npcs[2],npcs[3]);
        player.setTextures(npcs[102], npcs[74], npcs[241]);


        this.player = player;



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
        layerFromTileMap(1,
                " , , , , , , , , , , \n" +
                        " , , , , , ,0,0,0, , \n" +
                        " , , , , , ,0,0,0, , \n" +
                        " , , , , , ,0,0,0,0, \n" +
                        " ,0,0,0,0, ,0,0,0,0, \n" +
                        " ,0,0,0,0,0,0,0,0,0, \n" +
                        " ,0,0,0,0,0,0,0,0,0, \n" +
                        " ,0,0,0,0,0,0,0,0,0, \n" +
                        " ,0,0,0,0,0,0,0,0,0, \n" +
                        " ,0,0,0,0,0,0,0,0,0, \n" +
                        " ,0,0,0,0,0,0,0,0,0, \n" +
                        " ,0,0,0,0,0,0,0,0,0, \n" +
                        " , ,0,0,0,0,0,0,0, , \n" +
                        " , ,0,0,0,0,0,0,0, , \n" +
                        " , , , , , , , , , , , , , \n" +
                        " , , ,0, ,0,0,0,0,0,0,0,0, \n" +
                        " , ,0,0,0, , , , , , , ,0, \n" +
                        " , ,0,0,0,0,0,0,0,0,0, ,0, \n" +
                        " , , ,0,0,0, ,0,0,0, ,0,0, \n" +
                        " , , , ,0,0,0, ,0,0, ,0,0,0, \n" +
                        " , , , , ,0,0,0,0, ,0,0,0,0,0, \n"+
                        " , , , , ,0,0,0,0, ,0,0,0,0,0, \n"+
                        " , , , , , ,0,0,0, ,0,0,0,0,0, \n"+
                        " , , , , , , ,0,0, ,0,0,0,0,0, \n" +
                        " , , , , , , , ,0,0,0,0,0,0,0, \n" +
                        " , , , , , , , , , , , , , , , \n",
                new NSJMapTile[] { wildTile },NSJEngine.TILE_SIZE);


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
        for (Integer key : layerMapEntities.keySet())
            for (NSJEntity entity : layerMapEntities.get(key))
                entity.update(this);
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


                    /*if (NSJDebug.ENABLED && entity.getType() == NSJMapTile.MapTileType.SOLID) {
                        //Draw bounding box
                        Rectangle bb = entity.getBoundingBox();
                        spriteBatch.draw(NSJDebug.BOUNDINGBOX, bb.getX() - offsetX, bb.getY() - offsetY, bb.getX() + bb.getWidth()- offsetX, bb.getY() + bb.getHeight()- offsetY);
                    }*/
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

    public List<NSJEntity> getEntitiesAtPosition(Rectangle boundingBox, float curX, float curY) {



        //TODO speed up using octrees or something
        List<NSJEntity> entities = new ArrayList<NSJEntity>();

        for (int layer : layerMapTiles.keySet()) {
            for (NSJMapTile entity : layerMapTiles.get(layer)) {
               if (boundingBox == null && entity.getBoundingBox().contains(curX,curY)) {
                    entities.add(entity);
                } else if (boundingBox != null && entity.getBoundingBox().overlaps(boundingBox))
                   entities.add(entity);
            }
        }


        for (int layer : layerMapEntities.keySet()) {
            for (NSJEntity entity : layerMapEntities.get(layer)) {
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
