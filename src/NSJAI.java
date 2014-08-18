import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;

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

    public static final int Test = 0;
    private static final Texture TestTexture = new Texture("assets/enemy.png");
    private static final Texture TestPainTexture = new Texture("assets/enemypain.png");


    private int aiType;


    private float moveWaitTime = 0f; //Time to wait  between movements

    //Stop jittered movement (same as in NSJEngine so prob refactoring to do to avoid repeated code)
    private int moveDir = -1;
    private float totalDistanceMoved = 0f;
    private static final float moveDistance = 32f;

    public NSJAI(int id, float x, float y) {
        if (id == Test) {
            construct(TestTexture, x, y);
            painTexture = TestPainTexture;
        }

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

    private void behaveAsPokemon(NSJMap map) {
        if (moveWaitTime > 0)
        {
            moveWaitTime -= Gdx.graphics.getDeltaTime();
            return;
        }


        //**
        int direction = random.nextInt(4);

        if (moveWaitTime <= 0 && moveDir == -1) {
            if (direction == 0)
                if (canMoveTo(map, x-moveDistance, y))
                    moveDir = 0;
            if (direction == 1)
                if (canMoveTo(map, x+moveDistance, y))
                    moveDir = 1;
            if (direction == 2)
                if (canMoveTo(map, x, y+moveDistance))
                    moveDir = 2;
            if (direction == 3)
                if (canMoveTo(map, x, y-moveDistance))
                    moveDir = 3;

            totalDistanceMoved = 0;
        }

        System.out.println("totaldistmoved: " + totalDistanceMoved);
        System.out.println("moveidr: " + moveDir);
        System.out.println("movedist: " + moveDistance);

        if (totalDistanceMoved <= moveDistance  && moveDir != -1) {


            float distanceToMove = Gdx.graphics.getDeltaTime() * 100;
            if (totalDistanceMoved + distanceToMove > moveDistance)
                distanceToMove = moveDistance - totalDistanceMoved;
            else if (totalDistanceMoved + distanceToMove == moveDistance)
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

            if (totalDistanceMoved >= moveDistance) {
                moveDir = -1;
                totalDistanceMoved = 0f;
                moveWaitTime = 1;
            }
        }

    }

}
