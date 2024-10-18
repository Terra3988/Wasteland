package io.wasteland.level;

public class Level {
    public final int width;
    public final int height;
    public final int depth;

    private final byte[] blocks;

    public Level(int width, int height, int depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;

        blocks = new byte[width * height * depth];
        generateMap();
    }

    private void generateMap() {
        for (int x = 0; x < this.width; x++) {
            for (int z = 0; z < this.height; z++) {
                for (int y = 0; y < this.depth; y++) {
                    int index = (y * this.height + z) * this.width + x;
                    if (y == this.depth - 1)
                        setBlock(x, y, z, Tile.GRASS.getBlockId());
                    else
                        setBlock(x, y, z, Tile.DIRT.getBlockId());
                }
            }
        }
    }

    public boolean hasBlock(int x, int y, int z) {
        return getBlock(x, y, z) != 0;
    }

    public byte getBlock(int x, int y, int z) {
        if (x < 0 || y < 0 || z < 0 || x >= width || y >= depth || z >= height)
            return 0;
        int index = (y * this.height + z) * this.width + x;
        return blocks[index];
    }

    public void setBlock(int x, int y, int z, byte blockId) {
        if (x < 0 || y < 0 || z < 0 || x >= width || y >= depth || z >= height)
            return;
        int index = (y * this.height + z) * this.width + x;
        blocks[index] = blockId;
    }
}
