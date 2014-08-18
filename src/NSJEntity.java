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
    protected Texture texture;
    protected TextureRegion textureRegion;
    protected boolean canPlayerWalkThrough = true;
    private boolean destroyed = false; //Whether entity needs removing from map

    protected Rectangle boundingBox;
    protected int width;
    protected int height;
    private int layer;

    public NSJEntity(Texture texture, float x, float y) {
        construct(texture, x, y);
    }

    protected NSJEntity() {

    }



    protected void construct(Texture texture, float x, float y, int width, int height)  {
        this.x = x;
        this.y = y;
        this.texture = texture;
        this.width = width;
        this.height = height;


        boundingBox =  new Rectangle(3,3,NSJEngine.TILE_SIZE-3,NSJEngine.TILE_SIZE-3);
    }

    protected void construct(Texture texture, float x, float y) {
        construct(texture, x, y, texture.getWidth(), texture.getHeight());
    }

    public NSJEntity(TextureRegion texture, float x, float y, int width, int height)  {
        construct(null, x, y, width, height);
        textureRegion = texture;
    }

    public void render(SpriteBatch spriteBatch, int offsetX, int offsetY) {

        try{

        float scaleRatio = (width*(z+1))/width;

        if (isDestroyed())
            return;

        if (this instanceof NSJPlayer)
            ((NSJPlayer)this).renderCharacter(spriteBatch, 0,0);
        else if (this instanceof NSJCharacter)
            ((NSJCharacter)this).renderCharacter(spriteBatch,offsetX,offsetY);
        else {
            spriteBatch.draw(textureRegion,x,y,NSJEngine.TILE_SIZE, NSJEngine.TILE_SIZE);

        }
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

    public NSJEntity copy(int newXPosition, int newYPosition) {
        NSJEntity clone = new NSJEntity();
        clone.x = newXPosition;
        clone.y = newYPosition;
        clone.texture = texture;
        clone.width = width;
        clone.height = height;
        clone.boundingBox = new Rectangle(clone.x, clone.y, clone.width, clone.height);
        clone.canPlayerWalkThrough = canPlayerWalkThrough;
        clone.destroyed = false;
        return clone;
    }

    public void roundCoords() {
        x = Math.round(x);
        y = Math.round(y);
    }
}
