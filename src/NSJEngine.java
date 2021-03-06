import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.*;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 14/01/14
 * Time: 18:38
 * To change this template use File | Settings | File Templates.
 */
public class NSJEngine implements ApplicationListener {

    private SpriteBatch spriteBatch;
    private NSJMap map;
    private Texture projectile;
    private Texture crosshair;
    private NSJPlayer player;
    private NSJCharacter enemy;
    private PerspectiveCamera camera;

    private boolean mouseFlag = false;
    private boolean leftBracketFlag = false;
    private boolean rightBracketFlag = false;
    private boolean minusFlag = false;
    private boolean plusFlag = false;
    private boolean spaceFlag = false;
    private boolean apoFlag = false;
    private boolean hashFlag = false;

    private int x = 0;
    private int y = 0;

    private float totalDistanceMoved = 0f;

    public static final int TILE_SIZE = 16;

    private Random random = new Random();

    @Override
    public void create() {

        //Databases
        NSJMapTile.loadDatabase("assets/tiledb.txt");

        spriteBatch = new SpriteBatch();

        player =  new NSJPlayer(48,64);

        map = new NSJMap(player);



        NSJGUI.load();
    }

    @Override
    public void resize(int i, int i2) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void render() {

        if (player.moveDir == -1) {
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                player.faceLeft();
                if (player.canMoveTo(map, player.getX()-TILE_SIZE, player.getY())) {
                    player.moveDir = 0;
                    player.actualX = player.getX() - TILE_SIZE;
                    player.actualY = player.getY();
                }
            }
            else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                player.faceRight();
                if (player.canMoveTo(map, player.getX()+TILE_SIZE, player.getY())) {
                    player.moveDir = 1;
                    player.actualX = player.getX() + TILE_SIZE;
                    player.actualY = player.getY();
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                player.faceUp();
                if (player.canMoveTo(map, player.getX(), player.getY()+TILE_SIZE)) {
                    player.moveDir = 2;
                    player.actualX = player.getX();
                    player.actualY = player.getY() + TILE_SIZE;
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                player.faceDown();
                if (player.canMoveTo(map, player.getX(), player.getY()-TILE_SIZE)) {
                    player.moveDir = 3;
                    player.actualX = player.getX();
                    player.actualY = player.getY() - TILE_SIZE;
                }
            }

            totalDistanceMoved = 0;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.E)) {

            NSJPair<Float, Float> target = player.getInfrontOf();
            map.playerInteract(target.getLeft(), target.getRight());
        }

        /*if (totalDistanceMoved <= TILE_SIZE + 1 && player.moveDir != -1) {
            float distanceToMove = Gdx.graphics.getDeltaTime() * 100;
            if (totalDistanceMoved + distanceToMove > TILE_SIZE)
                distanceToMove = TILE_SIZE - totalDistanceMoved;

            if (distanceToMove < 0)
                distanceToMove = 0;


            if (player.moveDir == 0)
                player.increaseX(-distanceToMove);
            else if (player.moveDir == 1)
                player.increaseX(distanceToMove);
            else if (player.moveDir == 2)
                player.increaseY(distanceToMove);
            else
                player.increaseY(-distanceToMove);

            totalDistanceMoved += distanceToMove;

            if (totalDistanceMoved == TILE_SIZE) {

                //make sure position is an integer
                player.roundCoords();

                player.moveDir = -1;
                totalDistanceMoved = 0f;
            }
        }*/

        player.updateMovement(map);

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT_BRACKET) && leftBracketFlag) {
            ((NSJAI)enemy).iterateAITypeDown();
            leftBracketFlag = false;
        } else if (!Gdx.input.isKeyPressed(Input.Keys.LEFT_BRACKET))
            leftBracketFlag = true;

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT_BRACKET) && rightBracketFlag) {
            ((NSJAI)enemy).iterateAITypeUp();
            rightBracketFlag = false;
        } else if (!Gdx.input.isKeyPressed(Input.Keys.RIGHT_BRACKET))
            rightBracketFlag = true;

        if (Gdx.input.isKeyPressed(Input.Keys.MINUS) && minusFlag) {
            player.increaseZ(-1);
            minusFlag = false;
            NSJDebug.write(NSJDebug.NOTIF, "Decreasing Player Z by 1");
        } else if (!Gdx.input.isKeyPressed(Input.Keys.MINUS)) {
            minusFlag = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && spaceFlag) {
            player.jump();
            spaceFlag = false;
        } else if (!Gdx.input.isKeyPressed(Input.Keys.SPACE))
            spaceFlag =true;


        if (Gdx.input.isKeyPressed(Input.Keys.EQUALS) && plusFlag) {
            player.increaseZ(1);
            plusFlag = false;
            NSJDebug.write(NSJDebug.NOTIF, "Increasing Player Z by 1");
        } else if (!Gdx.input.isKeyPressed(Input.Keys.EQUALS)) {
            plusFlag = true;
        }




        map.update();

        //Render
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        spriteBatch.begin();

        map.render(spriteBatch, (int)player.getX() - Gdx.graphics.getWidth()/2 + player.getWidth()/2, (int)player.getY() - Gdx.graphics.getHeight()/2 + player.getHeight()/2);


        NSJGUI.render(spriteBatch, player.getHealth(),0,5);

        spriteBatch.end();
    }

    @Override
    public void pause() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void resume() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void dispose() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
