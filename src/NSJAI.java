import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 27/06/14
 * Time: 13:08
 * To change this template use File | Settings | File Templates.
 */
public class NSJAI extends NSJCharacter {

    private static final int AIType_Poke = 0;
    private static final int MAX_AI_TYPES = 1;


    private static final Random random = new Random();

    public static final int RandomMovement = 0;

    private int aiType;


    private float moveWaitTime = 0f; //Time to wait  between movements

    //Stop jittered movement (same as in NSJEngine so prob refactoring to do to avoid repeated code)
    private int moveDir = -1;
    private float totalDistanceMoved = 0f;

    public NSJAI(int id, float x, float y) {
        super(x,y);
        aiType = AIType_Poke;
    }

    public void iterateAITypeUp() {
        if (aiType < MAX_AI_TYPES - 1)
            aiType++;
        else
            aiType = 0;

        NSJDebug.write(NSJDebug.NOTIF, "AI Type switched to " + aiType);
    }

    public void iterateAITypeDown() {
        if (aiType > 0)
            aiType--;
        else
            aiType = MAX_AI_TYPES - 1;

        NSJDebug.write(NSJDebug.NOTIF, "AI Type switched to " + aiType);
    }

    public void updateAI(NSJMap map, NSJEntity target) {

        if (aiType == AIType_Poke)
            behaveAsPokemon(map);

        return;
    }


    public void render(SpriteBatch spriteBatch, int offsetX, int offsetY) {

        float scaleRatio = (width*(z+1))/width;
        spriteBatch.draw(current, x - offsetX, y - offsetY);
    }

    private void behaveAsPokemon(NSJMap map) {
        if (moveWaitTime > 0)
        {
            moveWaitTime -= Gdx.graphics.getDeltaTime();
            return;
        }


        //**
        int direction = random.nextInt(4);

        if (moveWaitTime <= 0 && moveDir == -1) {
            if (direction == 0) {
                if (canMoveTo(map, x-NSJEngine.TILE_SIZE, y))
                    moveDir = 0;
            }
            else if (direction == 1) {
                if (canMoveTo(map, x+NSJEngine.TILE_SIZE, y))
                    moveDir = 1;
            }
            else if (direction == 2) {
                if (canMoveTo(map, x, y+NSJEngine.TILE_SIZE))
                    moveDir = 2;
            }
            else if (direction == 3) {
                if (canMoveTo(map, x, y-NSJEngine.TILE_SIZE))
                    moveDir = 3;
            }

            totalDistanceMoved = 0;
        }

        if (totalDistanceMoved <= NSJEngine.TILE_SIZE  && moveDir != -1) {

            float distanceToMove = Gdx.graphics.getDeltaTime() * 100;
            if (totalDistanceMoved + distanceToMove > NSJEngine.TILE_SIZE)
                distanceToMove = NSJEngine.TILE_SIZE - totalDistanceMoved;
            else if (totalDistanceMoved + distanceToMove == NSJEngine.TILE_SIZE)
                distanceToMove = 0;


            if (moveDir == 0)
                x -= (distanceToMove);
            else if (moveDir == 1)
                x += (distanceToMove);
            else if (moveDir == 2)
                y += (distanceToMove);
            else if (moveDir == 3)
                y -= (distanceToMove);

            totalDistanceMoved += distanceToMove;

            if (totalDistanceMoved >= NSJEngine.TILE_SIZE) {
                moveDir = -1;
                totalDistanceMoved = 0f;
                moveWaitTime = 0;
                x = Math.round(x);
                y = Math.round(y);
            }
        }

    }

}
