package io.wasteland.level;

import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;

public class Tesselator {
    public static final int MAX_VERTICES = 100_000;

    private FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(MAX_VERTICES * 3);
    private FloatBuffer textureCoordsBuffer = BufferUtils.createFloatBuffer(MAX_VERTICES * 2);
    private FloatBuffer colorBuffer = BufferUtils.createFloatBuffer(MAX_VERTICES * 3);

    private int verticesCount = 0;

    private boolean hasColor = false;
    private float r;
    private float g;
    private float b;

    private boolean hasTexture = false;
    private float textureU;
    private float textureV;

    public Tesselator() {
        init();
    }

    public void init() {
        clear();
    }

    public void color(float r, float g, float b) {
        hasColor = true;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public void texture(float u, float v) {
        hasTexture = true;
        this.textureU = u;
        this.textureV = v;
    }

    public void vertex(float x, float y, float z) {
        this.vertexBuffer.put(verticesCount * 3 + 0, x);
        this.vertexBuffer.put(verticesCount * 3 + 1, y);
        this.vertexBuffer.put(verticesCount * 3 + 2, z);

        if (hasColor) {
            this.colorBuffer.put(verticesCount * 3 + 0, r);
            this.colorBuffer.put(verticesCount * 3 + 1, g);
            this.colorBuffer.put(verticesCount * 3 + 2, b);
        }

        if (hasTexture) {
            this.textureCoordsBuffer.put(verticesCount * 2 + 0, textureU);
            this.textureCoordsBuffer.put(verticesCount * 2 + 1, textureV);
        }

        this.verticesCount++;

        if (verticesCount == MAX_VERTICES) {
            flush();
        }
    }

    public void vertexUV(float x, float y, float z, float u, float v) {
        texture(u, v);
        vertex(x, y, z);
    }

    public void flush() {
        glEnableClientState(GL_VERTEX_ARRAY);
        glVertexPointer(3, 0, this.vertexBuffer);

        if (hasColor) {
            glEnableClientState(GL_COLOR_ARRAY);
            glColorPointer(3, 0, this.colorBuffer);
        }

        if (hasTexture) {
            glEnableClientState(GL_TEXTURE_COORD_ARRAY);
            glTexCoordPointer(2, 0, this.textureCoordsBuffer);
        }

        glDrawArrays(GL_QUADS, 0, this.verticesCount);

        glDisableClientState(GL_VERTEX_ARRAY);

        if (hasColor) {
            glDisableClientState(GL_COLOR_ARRAY);
        }

        if (hasTexture) {
            glDisableClientState(GL_TEXTURE_COORD_ARRAY);
        }

        clear();
    }

    public void clear() {
        hasColor = false;
        hasTexture = false;

        vertexBuffer.clear();
        textureCoordsBuffer.clear();
        colorBuffer.clear();

        verticesCount = 0;
    }
}
