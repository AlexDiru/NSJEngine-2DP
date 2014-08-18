import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.List;

public class NSJPlayer extends NSJCharacter {

    private TextureRegion up, down, left, right;

    public NSJPlayer(Texture player, Texture playerPain) {
        super(player,playerPain,32,32);

        //boundingBox = new NSJBoundingBox(new NSJVert(4,1), new NSJVert(28,1), new NSJVert(4,30), new NSJVert(28,30));
    }


    public void setTextures(TextureRegion up, TextureRegion down, TextureRegion left, TextureRegion right) {
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
    }

    public void render(SpriteBatch spriteBatch) {

        float scaleRatio = (width*(z+1))/width;
        spriteBatch.draw(up, (Gdx.graphics.getWidth() - width * scaleRatio) /2, (Gdx.graphics.getHeight() - height * scaleRatio) /2);// 0,0,width, height,z+1,z+1,0,0,0,texture.getWidth(), texture.getHeight(),false,false);
    }

    public void renderOnGUI(SpriteBatch spriteBatch) {

    }



    public void increaseX(int v) {
        x+=v;
    }
    public void increaseX(float v) {
        x+=v;
    }
    public void increaseY(float v) {
        y+=v;
    }

    public void increaseY(int v) {
        y+=v;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void increaseZ(int v) {
        z+=v;
    }
}
