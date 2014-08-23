import com.badlogic.gdx.math.Rectangle;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 22/08/14
 * Time: 21:30
 * To change this template use File | Settings | File Templates.
 */
public class Dialog extends NSJEntity {

    private String text;

    public Dialog(int x, int y, String text) {
        this.x = NSJEngine.TILE_SIZE * (x);
        this.y = NSJEngine.TILE_SIZE * (y);
        this.text = text;
        boundingBox = new Rectangle(this.x + 3, this.y + 3, NSJEngine.TILE_SIZE - 3,NSJEngine.TILE_SIZE - 3);
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
