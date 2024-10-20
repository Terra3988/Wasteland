package io.wasteland;

public class ChunkRenderer {
    public Mesh render(Chunk chunk) {
        Tessellator tessellator = new Tessellator();

        for (int y = 0; y < chunk.CHUNK_SIZE; y++) {
            for (int z = 0; z < chunk.CHUNK_SIZE; z++) {
                for (int x = 0; x < chunk.CHUNK_SIZE; x++) {
                    renderBlock(tessellator, chunk, x, y, z);
                }
            }
        }

        return tessellator.build();
    }

    private void renderBlock(Tessellator tessellator, Chunk chunk, int x, int y, int z) {

        float x0 = x - 0.5f;
        float y0 = y - 0.5f;
        float z0 = z - 0.5f;

        float x1 = x0 + 1f;
        float y1 = y0 + 1f;
        float z1 = z0 + 1f;

        // top
        if(chunk.get(x, y + 1, z) == 0) {
            tessellator.vertex(x1, y1, z1, 0, 0);
            tessellator.vertex(x1, y1, z0, 0, 1);
            tessellator.vertex(x0, y1, z0, 1, 1);

            tessellator.vertex(x0, y1, z0, 1, 1);
            tessellator.vertex(x0, y1, z1, 1, 0);
            tessellator.vertex(x1, y1, z1, 0, 0);
        }
        // bottom
        if(chunk.get(x, y - 1, z) == 0) {
            tessellator.vertex(x0, y0, z1, 0, 0);
            tessellator.vertex(x0, y0, z0, 0, 1);
            tessellator.vertex(x1, y0, z0, 1, 1);

            tessellator.vertex(x1, y0, z0, 1, 1);
            tessellator.vertex(x1, y0, z1, 1, 0);
            tessellator.vertex(x0, y0, z1, 0, 0);
        }
        // left
        if(chunk.get(x + 1, y, z) == 0) {
            tessellator.vertex(x1, y1, z1, 0, 0);
            tessellator.vertex(x1, y0, z1, 0, 1);
            tessellator.vertex(x1, y0, z0, 1, 1);

            tessellator.vertex(x1, y0, z0, 1, 1);
            tessellator.vertex(x1, y1, z0, 1, 0);
            tessellator.vertex(x1, y1, z1, 0, 0);
        }
        // right
        if(chunk.get(x - 1, y, z) == 0) {
            tessellator.vertex(x0, y1, z0, 0, 0);
            tessellator.vertex(x0, y0, z0, 0, 1);
            tessellator.vertex(x0, y0, z1, 1, 1);

            tessellator.vertex(x0, y0, z1, 1, 1);
            tessellator.vertex(x0, y1, z1, 1, 0);
            tessellator.vertex(x0, y1, z0, 0, 0);
        }
        // far
        if(chunk.get(x, y, z + 1) == 0) {
            tessellator.vertex(x0, y1, z1, 0, 0);
            tessellator.vertex(x0, y0, z1, 0, 1);
            tessellator.vertex(x1, y0, z1, 1, 1);

            tessellator.vertex(x1, y0, z1, 1, 1);
            tessellator.vertex(x1, y1, z1, 1, 0);
            tessellator.vertex(x0, y1, z1, 0, 0);
        }
        // near
        if(chunk.get(x, y, z - 1) == 0) {
            tessellator.vertex(x1, y1, z0, 0, 0);
            tessellator.vertex(x1, y0, z0, 0, 1);
            tessellator.vertex(x0, y0, z0, 1, 1);

            tessellator.vertex(x0, y0, z0, 1, 1);
            tessellator.vertex(x0, y1, z0, 1, 0);
            tessellator.vertex(x1, y1, z0, 0, 0);
        }
    }
}
