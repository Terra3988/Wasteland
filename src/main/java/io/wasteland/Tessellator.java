package io.wasteland;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

public class Tessellator {
    private static final int MAX_VERTICES = 1_000_000;

    private FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(MAX_VERTICES * 3);
    private FloatBuffer texCoordsBuffer = BufferUtils.createFloatBuffer(MAX_VERTICES * 2);

    private int vertices;

    private float texU;
    private float texV;

    public void init() {
        clear();
    }

    public void texture(float u, float v) {
        texU = u;
        texV = v;
    }

    public void clear() {
        this.vertexBuffer.clear();
        this.texCoordsBuffer.clear();

        vertices = 0;
        texU = 0;
        texV = 0;
    }
}
