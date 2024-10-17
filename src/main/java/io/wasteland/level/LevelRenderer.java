package io.wasteland.level;

public class LevelRenderer {

    private final Chunk[] chunks;

    private final int chunksX;
    private final int chunksY;
    private final int chunksZ;

    public LevelRenderer(Level level) {
        this.chunksX = level.width / 16;
        this.chunksZ = level.height / 16;
        this.chunksY = level.depth / 16;

        chunks = new Chunk[chunksX * chunksY * chunksZ];

        for (int x = 0; x < chunksX; x++) {
            for (int z = 0; z < chunksZ; z++) {
                for (int y = 0; y < chunksY; y++) {
                    int minX = x * 16;
                    int minY = y * 16;
                    int minZ = z * 16;

                    int maxX = minX + 16;
                    int maxY = minY + 16;
                    int maxZ = minZ + 16;

                    Chunk chunk = new Chunk(level, minX, minY, minZ, maxX, maxY, maxZ);
                    chunks[((x + y * chunksX) * chunksZ + z)] = chunk;
                }
            }
        }
    }

    public void render() {
        for(Chunk chunk : chunks) {
            chunk.render();
        }
    }
}
