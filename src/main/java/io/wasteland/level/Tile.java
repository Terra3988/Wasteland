package io.wasteland.level;

public class Tile {

    public static Tile DIRT = new Tile(0, 0);
    public static Tile GRASS = new Tile(1, 1);

    private final byte blockId;
    private final int textureId;

    public Tile(int blockId, int textureId) {
        this.blockId = (byte) blockId;
        this.textureId = textureId;
    }

    public void render(Tesselator tesselator, Level level, int x, int y, int z) {
        float minU = this.textureId / 16.0f;
        float minV = 0.0f;
        float maxU = minU + 16.0f / 256.0f;
        float maxV = minV + 16.0f / 256.0f;

        float minX = x - 0.5f;
        float minY = y - 0.5f;
        float minZ = z - 0.5f;

        float maxX = minX + 1.0f;
        float maxY = minY + 1.0f;
        float maxZ = minZ + 1.0f;

        if (!level.hasBlock(x, y - 1, z)) {
            tesselator.vertexUV(minX, minY, maxZ, minU, maxV);
            tesselator.vertexUV(minX, minY, minZ, minU, minV);
            tesselator.vertexUV(maxX, minY, minZ, maxU, minV);
            tesselator.vertexUV(maxX, minY, maxZ, maxU, maxV);
        }

        if (!level.hasBlock(x, y + 1, z)) {
            tesselator.vertexUV(maxX, maxY, maxZ, maxU, maxV);
            tesselator.vertexUV(maxX, maxY, minZ, maxU, minV);
            tesselator.vertexUV(minX, maxY, minZ, minU, minV);
            tesselator.vertexUV(minX, maxY, maxZ, minU, maxV);
        }

        if (!level.hasBlock(x, y, z - 1)) {
            tesselator.vertexUV(minX, maxY, minZ, maxU, minV);
            tesselator.vertexUV(maxX, maxY, minZ, minU, minV);
            tesselator.vertexUV(maxX, minY, minZ, minU, maxV);
            tesselator.vertexUV(minX, minY, minZ, maxU, maxV);
        }

        if (!level.hasBlock(x, y, z + 1)) {
            tesselator.vertexUV(minX, maxY, maxZ, minU, minV);
            tesselator.vertexUV(minX, minY, maxZ, minU, maxV);
            tesselator.vertexUV(maxX, minY, maxZ, maxU, maxV);
            tesselator.vertexUV(maxX, maxY, maxZ, maxU, minV);
        }

        if (!level.hasBlock(x - 1, y, z)) {
            tesselator.vertexUV(minX, maxY, maxZ, maxU, minV);
            tesselator.vertexUV(minX, maxY, minZ, minU, minV);
            tesselator.vertexUV(minX, minY, minZ, minU, maxV);
            tesselator.vertexUV(minX, minY, maxZ, maxU, maxV);
        }

        if (!level.hasBlock(x + 1, y, z)) {
            tesselator.vertexUV(maxX, minY, maxZ, minU, maxV);
            tesselator.vertexUV(maxX, minY, minZ, maxU, maxV);
            tesselator.vertexUV(maxX, maxY, minZ, maxU, minV);
            tesselator.vertexUV(maxX, maxY, maxZ, minU, minV);
        }
    }

    public byte getBlockId() {
        return blockId;
    }

    public int getTextureId() {
        return textureId;
    }
}
