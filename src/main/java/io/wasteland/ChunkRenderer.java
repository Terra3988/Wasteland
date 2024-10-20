package io.wasteland;

public class ChunkRenderer {
    public void render(Chunk chunk) {
        chunk.setDirty(true);
        Tessellator tessellator = new Tessellator();

        for (int y = 0; y < chunk.CHUNK_SIZE; y++) {
            for (int z = 0; z < chunk.CHUNK_SIZE; z++) {
                for (int x = 0; x < chunk.CHUNK_SIZE; x++) {
                    if (chunk.get(x, y, z) != 0)
                        renderBlock(tessellator, chunk, x, y, z);
                }
            }
        }
        chunk.chunkMesh = tessellator.build();
        chunk.setDirty(false);
    }

    private void renderBlock(Tessellator tessellator, Chunk chunk, int x, int y, int z) {
        // top
        if(!chunk.isBlocked(x, y + 1, z)) {
            renderBlockFace(tessellator, chunk, x, y, z, 0);
        }
        // bottom
        if(!chunk.isBlocked(x, y - 1, z)) {
            renderBlockFace(tessellator, chunk, x, y, z, 1);
        }
        // left
        if(!chunk.isBlocked(x + 1, y, z)) {
            renderBlockFace(tessellator, chunk, x, y, z, 2);
        }
        // right
        if(!chunk.isBlocked(x - 1, y, z)) {
            renderBlockFace(tessellator, chunk, x, y, z, 3);
        }
        // far
        if(!chunk.isBlocked(x, y, z + 1)) {
            renderBlockFace(tessellator, chunk, x, y, z, 5);
        }
        // near
        if(!chunk.isBlocked(x, y, z - 1)) {
            renderBlockFace(tessellator, chunk, x, y, z, 4);
        }
    }

    private void renderBlockFace(Tessellator tessellator, Chunk chunk, int x, int y, int z, int face) {

        float x0 = x - 0.5f;
        float y0 = y - 0.5f;
        float z0 = z - 0.5f;

        float x1 = x0 + 1f;
        float y1 = y0 + 1f;
        float z1 = z0 + 1f;

        float u0 = face * (16.0f / 96.0f);
        float v0 = 0;
        float u1 = u0 + (16.0f / 96.0f);
        float v1 = 1;

        // top
        if(face == 0) {
            tessellator.vertex(x1, y1, z1, u0, v0);
            tessellator.vertex(x1, y1, z0, u0, v1);
            tessellator.vertex(x0, y1, z0, u1, v1);

            tessellator.vertex(x0, y1, z0, u1, v1);
            tessellator.vertex(x0, y1, z1, u1, v0);
            tessellator.vertex(x1, y1, z1, u0, v0);
        }
        // bottom
        if(face == 1) {
            tessellator.vertex(x0, y0, z1, u0, v0);
            tessellator.vertex(x0, y0, z0, u0, v1);
            tessellator.vertex(x1, y0, z0, u1, v1);

            tessellator.vertex(x1, y0, z0, u1, v1);
            tessellator.vertex(x1, y0, z1, u1, v0);
            tessellator.vertex(x0, y0, z1, u0, v0);
        }
        // left
        if(face == 2) {
            tessellator.vertex(x1, y1, z1, u0, v0);
            tessellator.vertex(x1, y0, z1, u0, v1);
            tessellator.vertex(x1, y0, z0, u1, v1);

            tessellator.vertex(x1, y0, z0, u1, v1);
            tessellator.vertex(x1, y1, z0, u1, v0);
            tessellator.vertex(x1, y1, z1, u0, v0);
        }
        // right
        if(face == 3) {
            tessellator.vertex(x0, y1, z0, u0, v0);
            tessellator.vertex(x0, y0, z0, u0, v1);
            tessellator.vertex(x0, y0, z1, u1, v1);

            tessellator.vertex(x0, y0, z1, u1, v1);
            tessellator.vertex(x0, y1, z1, u1, v0);
            tessellator.vertex(x0, y1, z0, u0, v0);
        }
        // far
        if(face == 5) {
            tessellator.vertex(x0, y1, z1, u0, v0);
            tessellator.vertex(x0, y0, z1, u0, v1);
            tessellator.vertex(x1, y0, z1, u1, v1);

            tessellator.vertex(x1, y0, z1, u1, v1);
            tessellator.vertex(x1, y1, z1, u1, v0);
            tessellator.vertex(x0, y1, z1, u0, v0);
        }
        // near
        if(face == 4) {
            tessellator.vertex(x1, y1, z0, u0, v0);
            tessellator.vertex(x1, y0, z0, u0, v1);
            tessellator.vertex(x0, y0, z0, u1, v1);

            tessellator.vertex(x0, y0, z0, u1, v1);
            tessellator.vertex(x0, y1, z0, u1, v0);
            tessellator.vertex(x1, y1, z0, u0, v0);
        }
    }
}
