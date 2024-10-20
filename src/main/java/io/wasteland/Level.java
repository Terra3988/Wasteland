package io.wasteland;

public class Level {
    public final int width;
    public final int height;
    public final int depth;

    private Chunk[] chunks;

    /**
     * Creating new level
     * @param width in chunks
     * @param height in chunks
     * @param depth in chunks
     */
    public Level(int width, int height, int depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;

        this.chunks = new Chunk[width * height * depth];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < depth; y++) {
                for (int z = 0; z < height; z++) {
                    int index = (y * depth + z) * width + x;
                    chunks[index] = new Chunk(x, y, z);
                }
            }
        }
    }

    public Chunk getChunk(int x, int y, int z) {
        if (x < 0 || y < 0 || z < 0 || x >= width || y >= depth || z >= height)
            return null;
        int index = (y * depth + z) * width + x;
        return chunks[index];
    }
}
