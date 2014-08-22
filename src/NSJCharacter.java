import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class NSJCharacter extends NSJEntity {

    private int health =69696969;


    private boolean jumping = false;
    private boolean beginJump =false;
    private float zVelocity;
    private float initialJumpVelocity = 2;
    private float jumpDeceleration = 3f;

    protected List<TextureRegion> up, down, left;
    protected List<TextureRegion> current;
    protected int currentAnimation = 0;

    protected int moveDir = -1;
    protected float moveWaitTime = 1f;
    protected float totalDistanceMoved = 0f;

    protected NSJCharacter(float x, float y) {
        super(x,y);
        setCanPlayerWalkThrough(false);
    }


    public void setTextures(TextureRegion up, TextureRegion down, TextureRegion left) {
        this.up = new ArrayList<TextureRegion>();
        this.down = new ArrayList<TextureRegion>();
        this.left = new ArrayList<TextureRegion>();
        this.up.add(up);
        this.down.add(down);
        this.left.add(left);
        current = this.down;
    }

    public void setTextures(TextureRegion up1, TextureRegion up2, TextureRegion down1, TextureRegion down2, TextureRegion left1, TextureRegion left2) {
        this.up = new ArrayList<TextureRegion>();
        this.down = new ArrayList<TextureRegion>();
        this.left = new ArrayList<TextureRegion>();
        this.up.add(up1);
        this.up.add(up2);
        this.down.add(down1);
        this.down.add(down2);
        this.left.add(left1);
        this.left.add(left2);
        current = this.down;
    }

    public int getHealth() {
        return health;
    }


    protected TextureRegion getCurrentTexture() {
        return current.get(currentAnimation);
    }


    public void update(NSJMap map) {

        if (health <= 0) {
            destroy();
        }

        if (this instanceof NSJAI)
            ((NSJAI)this).updateAI(map, map.getPlayer());

        //Jumping
        if (beginJump) {
            beginJump = false;
            jumping = true;
            zVelocity = initialJumpVelocity;
        }

        if (jumping) {
            z += zVelocity * Gdx.graphics.getDeltaTime();
            zVelocity -= jumpDeceleration * Gdx.graphics.getDeltaTime();

            if (zVelocity <= -initialJumpVelocity)
                jumping = false;
        }
    }


    public boolean  canMoveTo(NSJMap map, float destX, float destY) {

        if (Math.round(destX) == Math.round(x)) {
            if (Math.round(destY) > Math.round(y)) {
                for (int curY = Math.round(y); curY <= Math.round(destY); curY += 1){
                    if (solidFound(map.getEntitiesAtPosition(null, destX, curY)))
                        return false;
                }
            } else {
                for (int curY = Math.round(y); curY >= Math.round(destY); curY -= 1){
                    if (solidFound(map.getEntitiesAtPosition(null, destX, curY)))
                        return false;
                }
            }
        }
        else if (Math.round(destY) == Math.round(y)) {
            if (Math.round(destX) > Math.round(x)) {
                for (int curX = Math.round(x); curX <= Math.round(destX); curX += 1){
                    if (solidFound(map.getEntitiesAtPosition(null, curX, destY)))
                        return false;
                }
            } else {
                for (float curX = Math.round(x); curX >= Math.round(destX); curX -= 1){
                    if (solidFound(map.getEntitiesAtPosition(null, curX, destY)))
                        return false;
                }
            }
        }


        return true;
    }

    private boolean solidFound(List<NSJEntity> entitiesAtPosition) {
        for (NSJEntity entity : entitiesAtPosition) {
            if (entity != this) {
                if (entity instanceof NSJMapTile) {
                    NSJMapTile.MapTileType type = ((NSJMapTile)entity).getType();
                    if (type == NSJMapTile.MapTileType.BOULDER || type == NSJMapTile.MapTileType.SOLID) {
                        return true;
                    }
                }
                else if (!entity.canPlayerWalkThrough) {
                    return true;
                }
            }
        }
        return false;
    }

    public void jump() {
        if (!jumping)
            beginJump = true;
    }


    public void increaseX(float v) {
        x+=v;
        if (v > 0) {
            if (!left.get(currentAnimation).isFlipX())
                left.get(currentAnimation).flip(true, false);
            current = left;
        }
        else
        {
            if (left.get(currentAnimation).isFlipX())
                left.get(currentAnimation).flip(true, false);

            current = left;
        }
    }
    public void increaseY(float v) {
        y+=v;

        if (v > 0)
            current = up;
        else
            current = down;
    }


    public void updateMovement(NSJMap map) {


        if (totalDistanceMoved <= NSJEngine.TILE_SIZE  && moveDir != -1) {

            float distanceToMove = Gdx.graphics.getDeltaTime() * 100;
            if (totalDistanceMoved + distanceToMove > NSJEngine.TILE_SIZE)
                distanceToMove = NSJEngine.TILE_SIZE - totalDistanceMoved;
            else if (totalDistanceMoved + distanceToMove == NSJEngine.TILE_SIZE)
                distanceToMove = 0;


            if (moveDir == 0)
                increaseX(-distanceToMove);
            else if (moveDir == 1)
                increaseX(distanceToMove);
            else if (moveDir == 2)
                increaseY(distanceToMove);
            else if (moveDir == 3)
                increaseY(-distanceToMove);

            totalDistanceMoved += distanceToMove;

            if (totalDistanceMoved < 3.5f || totalDistanceMoved > 13.5f)
                currentAnimation = 0;
            else
                currentAnimation = current.size() - 1;

            if (totalDistanceMoved >= NSJEngine.TILE_SIZE) {
                currentAnimation = 0; //Return to default animation
                moveDir = -1;
                totalDistanceMoved = 0f;
                moveWaitTime = 1f + (float)(Math.random()*3);
                x = actualX;
                y = actualY;

                //Check warps
                Warp warp = map.getWarpAt((int)x,(int)y);
                if (warp != null) {
                    System.out.println("Warping to " + warp.getMapDest() + " at (" + warp.getWarpDestX() + ", " + warp.getWarpDestY() + ") [" + warp.hashCode() + "]");
                    map.clear();
                    map.loadMap(warp.getMapDest());
                }

            }
        }
    }

    public void faceDown() {
        current = down;
    }

    public void faceUp() {
        current = up;
    }

    public void faceLeft() {
        if (left.get(currentAnimation).isFlipX())
            left.get(currentAnimation).flip(true, false);
        current = left;
    }

    public void faceRight() {
        if (!left.get(currentAnimation).isFlipX())
            left.get(currentAnimation).flip(true, false);
        current = left;
    }
}
