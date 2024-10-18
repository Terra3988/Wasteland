package io.wasteland.client;

import io.wasteland.world.level.Level;
import io.wasteland.world.phys.AABB;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class Player {

    private final Level level;

    public double x, y, z;
    public double prevX, prevY, prevZ;
    public double motionX, motionY, motionZ;
    public float xRotation, yRotation;

    private boolean onGround;

    public AABB boundingBox;

    public Player(Level level) {
        this.level = level;

        resetPosition();
    }

    private void setPosition(float x, float y, float z) {
        
        this.x = x;
        this.y = y;
        this.z = z;

        
        float width = 0.3F;
        float height = 0.9F;

        
        this.boundingBox = new AABB(x - width, y - height,
                z - width, x + width,
                y + height, z + width);
    }

    private void resetPosition() {
        float x = (float) Math.random() * this.level.width;
        float y = (float) (this.level.depth + 3);
        float z = (float) Math.random() * this.level.height;

        setPosition(x, y, z);
    }

    /**
     * Turn the camera using motion yaw and pitch
     *
     * @param x Rotate the camera using yaw
     * @param y Rotate the camera using pitch
     */
    public void turn(float x, float y) {
        this.yRotation += x * 0.15F;
        this.xRotation -= y * 0.15F;

        
        this.xRotation = Math.max(-90.0F, this.xRotation);
        this.xRotation = Math.min(90.0F, this.xRotation);
    }

    public void tick() {
        
        this.prevX = this.x;
        this.prevY = this.y;
        this.prevZ = this.z;

        float forward = 0.0F;
        float vertical = 0.0F;

        
        if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
            resetPosition();
        }

        
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            forward++;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            forward--;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            vertical--;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            vertical++;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            if (this.onGround) {
                this.motionY = 0.12F;
            }
        }

        
        moveRelative(vertical, forward, this.onGround ? 0.02F : 0.005F);

        
        this.motionY -= 0.005D;

        
        move(this.motionX, this.motionY, this.motionZ);

        
        this.motionX *= 0.91F;
        this.motionY *= 0.98F;
        this.motionZ *= 0.91F;

        
        if (this.onGround) {
            this.motionX *= 0.8F;
            this.motionZ *= 0.8F;
        }
    }

    public void move(double x, double y, double z) {
        double prevX = x;
        double prevY = y;
        double prevZ = z;

        
        List<AABB> aABBs = this.level.getCubes(this.boundingBox.expand(x, y, z));

        
        for (AABB abb : aABBs) {
            y = abb.clipYCollide(this.boundingBox, y);
        }
        this.boundingBox.move(0.0F, y, 0.0F);

        
        for (AABB aABB : aABBs) {
            x = aABB.clipXCollide(this.boundingBox, x);
        }
        this.boundingBox.move(x, 0.0F, 0.0F);

        
        for (AABB aABB : aABBs) {
            z = aABB.clipZCollide(this.boundingBox, z);
        }
        this.boundingBox.move(0.0F, 0.0F, z);

        
        this.onGround = prevY != y && prevY < 0.0F;

        
        if (prevX != x) this.motionX = 0.0D;
        if (prevY != y) this.motionY = 0.0D;
        if (prevZ != z) this.motionZ = 0.0D;

        
        this.x = (this.boundingBox.minX + this.boundingBox.maxX) / 2.0D;
        this.y = this.boundingBox.minY + 1.62D;
        this.z = (this.boundingBox.minZ + this.boundingBox.maxZ) / 2.0D;
    }

    private void moveRelative(float x, float z, float speed) {
        float distance = x * x + z * z;

        
        if (distance < 0.01F)
            return;

        
        distance = speed / (float) Math.sqrt(distance);
        x *= distance;
        z *= distance;

        
        double sin = Math.sin(Math.toRadians(this.yRotation));
        double cos = Math.cos(Math.toRadians(this.yRotation));

        
        this.motionX += x * cos - z * sin;
        this.motionZ += z * cos + x * sin;
    }
}
