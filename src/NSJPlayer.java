import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.List;

public class NSJPlayer extends NSJCharacter {


    public NSJPlayer(float x, float y) {
        super(x,y);
    }



    public void render(SpriteBatch spriteBatch) {

        float scaleRatio = (width*(z+1))/width;
        spriteBatch.draw(getCurrentTexture(), (Gdx.graphics.getWidth() - width * scaleRatio) /2, (Gdx.graphics.getHeight() - height * scaleRatio) /2);// 0,0,width, height,z+1,z+1,0,0,0,texture.getWidth(), texture.getHeight(),false,false);
    }

    public void renderOnGUI(SpriteBatch spriteBatch) {

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
