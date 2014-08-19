import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 23/06/14
 * Time: 23:17
 * To change this template use File | Settings | File Templates.
 */
public class NSJEntity {

    protected float x;
    protected float y;
    protected float z;
    protected TextureRegion textureRegion;
    protected boolean canPlayerWalkThrough = true;
    private boolean destroyed = false; //Whether entity needs removing from map

    protected Rectangle boundingBox;
    protected int width;
    protected int height;
    private int layer;

    public NSJEntity( float x, float y) {
        construct(x, y, NSJEngine.TILE_SIZE, NSJEngine.TILE_SIZE);
    }

    protected NSJEntity() {

    }



    protected void construct(float x, float y, int width, int height)  {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;


        boundingBox =  new Rectangle(3,3,NSJEngine.TILE_SIZE-3,NSJEngine.TILE_SIZE-3);
    }


    public NSJEntity(TextureRegion texture, float x, float y, int width, int height)  {
        construct( x, y, width, height);
        textureRegion = texture;
    }

    public void render(SpriteBatch spriteBatch, int offsetX, int offsetY) {

        try{
            if (this instanceof NSJAI)
                ((NSJAI)this).render(spriteBatch,offsetX,offsetY);
            //else
            //    spriteBatch.draw(textureRegion,x,y,NSJEngine.TILE_SIZE, NSJEngine.TILE_SIZE);

        } catch (Exception e) {
           e.printStackTrace();
        }
        return;
    }

    public void setCanPlayerWalkThrough(boolean canPlayerWalkThrough) {
        this.canPlayerWalkThrough = canPlayerWalkThrough;
    }

    public Rectangle getBoundingBox() {
        boundingBox.setPosition(x,y);
        //boundingBox.setWidth(4);
        return new Rectangle(boundingBox.getX(), boundingBox.getY(), 4,4);
        //return boundingBox;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void increaseZ(float v) {
        this.z += v;
    }

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public void destroy() {
        destroyed = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }


    public void roundCoords() {
        x = Math.round(x);
        y = Math.round(y);
    }

    public void update(NSJMap map) {
        if (this instanceof NSJAI) {
            ((NSJAI)this).updateAI(map, null);
        }
    }
}
