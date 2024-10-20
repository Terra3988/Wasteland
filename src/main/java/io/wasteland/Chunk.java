package io.wasteland;

import org.joml.Math;

public class Chunk {
    public byte[] blocks;

    public final int CHUNK_SIZE = 16;

    public Chunk() {
        blocks = new byte[CHUNK_SIZE * CHUNK_SIZE * CHUNK_SIZE];

        for (int y = 0; y < CHUNK_SIZE; y++) {
            for (int z = 0; z < CHUNK_SIZE; z++) {
                for (int x = 0; x < CHUNK_SIZE; x++) {
                    int index = (y * CHUNK_SIZE + z) * CHUNK_SIZE + x;

                    int id = (y <= (Math.sin(x*0.3f) * 0.5f + 0.5f) * 10) ? 1 : 0;
                    blocks[index] = (byte) id;
                }
            }
        }
    }

    public boolean isIn(int x, int y, int z) {
        return !(x < 0 || y < 0 || z < 0 || x >= CHUNK_SIZE || y >= CHUNK_SIZE || z >= CHUNK_SIZE);
    }

    public boolean isBlocked(int x, int y, int z) {
        return isIn(x, y, z) && get(x, y, z) > 0;
    }

    public int get(int x, int y, int z) {
        if (x < 0 || y < 0 || z < 0 || x >= CHUNK_SIZE || y >= CHUNK_SIZE || z >= CHUNK_SIZE)
            return 0;
        int index = (y * CHUNK_SIZE + z) * CHUNK_SIZE + x;
        return (int) blocks[index];
    }
}
