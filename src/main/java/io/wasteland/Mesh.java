package io.wasteland;

import static org.lwjgl.opengl.GL46.*;

public class Mesh {
    private final int VAO;
    private final int length;

    public Mesh(int VAO, int length) {
        this.VAO = VAO;
        this.length = length;
    }

    public void draw() {
        glBindVertexArray(VAO);
        glDrawArrays(GL_TRIANGLES, 0, length);
        glBindVertexArray(0);
    }

    public void dispose() {
        glDeleteVertexArrays(VAO);
    }
}
