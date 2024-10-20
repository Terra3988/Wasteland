package io.wasteland.renderer;

import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL46.*;

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

    public void vertex(float x, float y, float z) {
        this.vertexBuffer.put(vertices * 3 + 0, x);
        this.vertexBuffer.put(vertices * 3 + 1, y);
        this.vertexBuffer.put(vertices * 3 + 2, z);

        this.texCoordsBuffer.put(vertices * 2 + 0, texU);
        this.texCoordsBuffer.put(vertices * 2 + 1, texV);

        this.vertices++;
    }

    public void vertex(float x, float y, float z, float u, float v) {
        texture(u, v);
        vertex(x, y, z);
    }

    public Mesh build() {

        int vao = glGenVertexArrays();
        int vbo = glGenBuffers();
        int tbo = glGenBuffers();

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        glBindBuffer(GL_ARRAY_BUFFER, tbo);
        glBufferData(GL_ARRAY_BUFFER, texCoordsBuffer, GL_STATIC_DRAW);

        glBindVertexArray(vao);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(0);

        glBindBuffer(GL_ARRAY_BUFFER, tbo);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(1);

        glBindVertexArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        Mesh result = new Mesh(vao, vertices);
        clear();
        return result;
    }

    public void clear() {
        this.vertexBuffer.clear();
        this.texCoordsBuffer.clear();

        vertices = 0;
        texU = 0;
        texV = 0;
    }
}
