package io.wasteland.level;

import static org.lwjgl.opengl.GL11.*;

public class Chunk {
    private final int minX;
    private final int minY;
    private final int minZ;
    private final int maxX;
    private final int maxY;
    private final int maxZ;

    private final Level level;
    private boolean dirty = true;
    private final Tesselator tesselator;

    // OpenGL
    private int lists;

    public Chunk(Level level, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        this.level = level;
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;

        this.lists = glGenLists(1);
        this.tesselator = new Tesselator();
    }

    private void build() {
        glNewList(this.lists, GL_COMPILE);
        tesselator.init();

        for (int x = minX; x < maxX; x++) {
            for (int z = minZ; z < maxZ; z++) {
                for (int y = minY; y < maxY; y++) {
                    int blockId = level.getBlock(x, y, z);

                    if (blockId > 0) {
                        Tile.DIRT.render(tesselator, level, x, y, z - 100);
                    }
                }
            }
        }

        tesselator.flush();
        glEndList();
    }

    public void render() {
        if (dirty)
            build();
        glCallList(lists);
    }
}
