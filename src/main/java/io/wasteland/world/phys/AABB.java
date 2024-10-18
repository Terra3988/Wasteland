package io.wasteland.world.phys;

public class AABB {

    private final double epsilon = 0.0F;

    public double minX;
    public double minY;
    public double minZ;
    public double maxX;
    public double maxY;
    public double maxZ;

    /**
     * Bounding box
     *
     * @param minX Minimum x side
     * @param minY Minimum y side
     * @param minZ Minimum z side
     * @param maxX Maximum x side
     * @param maxY Maximum y side
     * @param maxZ Maximum z side
     */
    public AABB(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    /**
     * Copy the current bounding box object
     *
     * @return Clone of the bounding box
     */
    public AABB clone() {
        return new AABB(this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ);
    }

    /**
     * Expand the bounding box. Positive and negative numbers controls which side of the box should grow.
     *
     * @param x Amount to expand the minX or maxX
     * @param y Amount to expand the minY or maxY
     * @param z Amount to expand the minZ or maxZ
     * @return The expanded bounding box
     */
    public AABB expand(double x, double y, double z) {
        double minX = this.minX;
        double minY = this.minY;
        double minZ = this.minZ;
        double maxX = this.maxX;
        double maxY = this.maxY;
        double maxZ = this.maxZ;

        
        if (x < 0.0F) {
            minX += x;
        } else {
            maxX += x;
        }

        
        if (y < 0.0F) {
            minY += y;
        } else {
            maxY += y;
        }

        
        if (z < 0.0F) {
            minZ += z;
        } else {
            maxZ += z;
        }

        
        return new AABB(minX, minY, minZ, maxX, maxY, maxZ);
    }

    /**
     * Expand the bounding box on both sides.
     * The center is always fixed when using grow.
     *
     * @param x
     * @param y
     * @param z
     * @return
     */
    public AABB grow(double x, double y, double z) {
        return new AABB(this.minX - x, this.minY - y,
                this.minZ - z, this.maxX + x,
                this.maxY + y, this.maxZ + z);
    }

    /**
     * Check for collision on the X axis
     *
     * @param otherBoundingBox The other bounding box that is colliding with the this one.
     * @param x                Position on the X axis that is colliding
     * @return Returns the corrected x position that collided.
     */
    public double clipXCollide(AABB otherBoundingBox, double x) {
        
        if (otherBoundingBox.maxY <= this.minY || otherBoundingBox.minY >= this.maxY) {
            return x;
        }

        
        if (otherBoundingBox.maxZ <= this.minZ || otherBoundingBox.minZ >= this.maxZ) {
            return x;
        }

        
        if (x > 0.0F && otherBoundingBox.maxX <= this.minX) {
            double max = this.minX - otherBoundingBox.maxX - this.epsilon;
            if (max < x) {
                x = max;
            }
        }

        
        if (x < 0.0F && otherBoundingBox.minX >= this.maxX) {
            double max = this.maxX - otherBoundingBox.minX + this.epsilon;
            if (max > x) {
                x = max;
            }
        }

        return x;
    }

    /**
     * Check for collision on the Y axis
     *
     * @param otherBoundingBox The other bounding box that is colliding with the this one.
     * @param y                Position on the X axis that is colliding
     * @return Returns the corrected x position that collided.
     */
    public double clipYCollide(AABB otherBoundingBox, double y) {
        
        if (otherBoundingBox.maxX <= this.minX || otherBoundingBox.minX >= this.maxX) {
            return y;
        }

        
        if (otherBoundingBox.maxZ <= this.minZ || otherBoundingBox.minZ >= this.maxZ) {
            return y;
        }

        
        if (y > 0.0F && otherBoundingBox.maxY <= this.minY) {
            double max = this.minY - otherBoundingBox.maxY - this.epsilon;
            if (max < y) {
                y = max;
            }
        }

        
        if (y < 0.0F && otherBoundingBox.minY >= this.maxY) {
            double max = this.maxY - otherBoundingBox.minY + this.epsilon;
            if (max > y) {
                y = max;
            }
        }

        return y;
    }

    /**
     * Check for collision on the Y axis
     *
     * @param otherBoundingBox The other bounding box that is colliding with the this one.
     * @param z                Position on the X axis that is colliding
     * @return Returns the corrected x position that collided.
     */
    public double clipZCollide(AABB otherBoundingBox, double z) {
        
        if (otherBoundingBox.maxX <= this.minX || otherBoundingBox.minX >= this.maxX) {
            return z;
        }

        
        if (otherBoundingBox.maxY <= this.minY || otherBoundingBox.minY >= this.maxY) {
            return z;
        }

        
        if (z > 0.0F && otherBoundingBox.maxZ <= this.minZ) {
            double max = this.minZ - otherBoundingBox.maxZ - this.epsilon;
            if (max < z) {
                z = max;
            }
        }

        
        if (z < 0.0F && otherBoundingBox.minZ >= this.maxZ) {
            double max = this.maxZ - otherBoundingBox.minZ + this.epsilon;
            if (max > z) {
                z = max;
            }
        }

        return z;
    }

    /**
     * Check if the two boxes are intersecting/overlapping
     *
     * @param otherBoundingBox The other bounding box that could intersect
     * @return The two boxes are overlapping
     */
    public boolean intersects(AABB otherBoundingBox) {
        
        if (otherBoundingBox.maxX <= this.minX || otherBoundingBox.minX >= this.maxX) {
            return false;
        }

        
        if (otherBoundingBox.maxY <= this.minY || otherBoundingBox.minY >= this.maxY) {
            return false;
        }

        
        return (!(otherBoundingBox.maxZ <= this.minZ)) && (!(otherBoundingBox.minZ >= this.maxZ));
    }

    /**
     * Move the bounding box relative.
     *
     * @param x Relative offset x
     * @param y Relative offset y
     * @param z Relative offset z
     */
    public void move(double x, double y, double z) {
        this.minX += x;
        this.minY += y;
        this.minZ += z;
        this.maxX += x;
        this.maxY += y;
        this.maxZ += z;
    }

    /**
     * Create a new bounding box with the given offset
     *
     * @param x Relative offset x
     * @param y Relative offset x
     * @param z Relative offset x
     * @return New bounding box with the given offset relative to this bounding box
     */
    public AABB offset(double x, double y, double z) {
        return new AABB(this.minX + x, this.minY + y, this.minZ + z, this.maxX + x, this.maxY + y, this.maxZ + z);
    }
}
