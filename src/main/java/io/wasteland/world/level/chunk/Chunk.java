package io.wasteland.world.level.chunk;

public class Chunk {
    private static final int WORLD_DEPTH = 64;
    private static final int SECTION_SIZE = 16;

    public ChunkPos position;

    private ChunkSection[] sections;

    public Chunk(ChunkPos pos) {
        this.position = pos;

        sections = new ChunkSection[WORLD_DEPTH / SECTION_SIZE];

        for (int y = 0; y < WORLD_DEPTH / SECTION_SIZE; y++) {
            ChunkSection section = new ChunkSection(pos, y * SECTION_SIZE, (y * 1) * SECTION_SIZE);
            sections[y] = section;
        }
    }
}
