import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.List;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 23/08/14
 * Time: 13:09
 * To change this template use File | Settings | File Templates.
 */
public class DialogRenderer {
    //Maps the dialog text to the time out value
    private ArrayList<NSJPair<String, Float>> dialogQueue = new ArrayList<NSJPair<String,Float>>();
    private BitmapFont bitmapFont = new BitmapFont();

    public void addDialog(String text) {
        dialogQueue.add(new NSJPair<String, Float>(text, calculateTimeout(text)));
    }

    public void updateDialogs() {
        if (dialogQueue.size() == 0)
            return;

        for (int i = 0; i < dialogQueue.size(); i++) {
            dialogQueue.get(i).setRight(dialogQueue.get(i).getRight() - Gdx.graphics.getDeltaTime());
            if (dialogQueue.get(i).getRight() <= 0) {
                dialogQueue.remove(i);
                i--;
            }
        }
    }

    public void renderDialogs(SpriteBatch spriteBatch) {
        for (int i = 0; i < dialogQueue.size(); i++) {
            bitmapFont.draw(spriteBatch,dialogQueue.get(i).getLeft(), 0, i * bitmapFont.getLineHeight());
        }
    }

    private float calculateTimeout(String text) {
        return 5f;//text.length() * 0.33f; //1 second per three letters
    }


}
