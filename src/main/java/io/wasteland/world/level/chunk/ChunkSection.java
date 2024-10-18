package io.wasteland.world.level.chunk;

public class ChunkSection {
    private static final int SECTION_SIZE = 16;

    private byte[] data;

    private ChunkPos pos;
    private int minY;
    private int maxY;

    public ChunkSection(ChunkPos pos, int minY, int maxY) {

        this.pos = pos;
        this.minY = minY;
        this.maxY = maxY;

        data = new byte[SECTION_SIZE * SECTION_SIZE * SECTION_SIZE];
    }

}
