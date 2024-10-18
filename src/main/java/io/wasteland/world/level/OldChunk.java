package io.wasteland.world.level;

import io.wasteland.client.renderer.Tessellator;
import io.wasteland.client.renderer.Textures;
import io.wasteland.world.phys.AABB;

import static org.lwjgl.opengl.GL11.*;


public class OldChunk {

    private static final int TEXTURE = Textures.loadTexture("/textures.png", GL_NEAREST);
    private static final Tessellator TESSELLATOR = new Tessellator();

    public static int rebuiltThisFrame;
    public static int updates;

    private final Level level;

    public AABB boundingBox;
    private final int minX, minY, minZ;
    private final int maxX, maxY, maxZ;

    private final int lists;
    private boolean dirty = true;

    public OldChunk(Level level, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        this.level = level;

        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;

        
        this.lists = glGenLists(2);

        
        this.boundingBox = new AABB(minX, minY, minZ, maxX, maxY, maxZ);
    }

    public void rebuild(int layer) {
        if (rebuiltThisFrame == 2) {
            return;
        }

        updates++;
        rebuiltThisFrame++;
        this.dirty = false;

        glNewList(this.lists + layer, GL_COMPILE);
        glEnable(GL_TEXTURE_2D);
        glBindTexture(GL_TEXTURE, TEXTURE);
        TESSELLATOR.init();

        
        for (int x = this.minX; x < this.maxX; ++x) {
            for (int y = this.minY; y < this.maxY; ++y) {
                for (int z = this.minZ; z < this.maxZ; ++z) {
                    
                    if (this.level.isTile(x, y, z)) {

                        
                        if (y > this.level.depth - 7 && this.level.getBrightness(x, y, z) == 1.0F) {
                            
                            Tile.grass.render(TESSELLATOR, this.level, layer, x, y, z);
                        } else {
                            
                            Tile.rock.render(TESSELLATOR, this.level, layer, x, y, z);
                        }
                    }
                }
            }
        }

        
        TESSELLATOR.flush();
        glDisable(GL_TEXTURE_2D);
        glEndList();
    }

    public void render(int layer) {
        
        if (this.dirty) {
            rebuild(0);
            rebuild(1);
        }

        
        glCallList(this.lists + layer);
    }

    public void setDirty() {
        this.dirty = true;
    }
}
