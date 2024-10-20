package io.wasteland.renderer;

import static org.lwjgl.opengl.GL46.*;

public class Texture {
    private final int handle;

    private final int width;
    private final int height;

    public Texture(int handle, int width, int height) {
        this.handle = handle;
        this.width = width;
        this.height = height;
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, handle);
    }

    public void dispose() {
        glDeleteTextures(handle);
    }

    public int getHandle() {
        return handle;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
