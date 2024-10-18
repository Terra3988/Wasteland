package io.wasteland.client.renderer;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;

public class Tessellator {

    private static final int MAX_VERTICES = 100000;

    private final FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(MAX_VERTICES * 3);
    private final FloatBuffer textureCoordinateBuffer = BufferUtils.createFloatBuffer(MAX_VERTICES * 2);
    private final FloatBuffer colorBuffer = BufferUtils.createFloatBuffer(MAX_VERTICES * 3);

    private int vertices = 0;

    
    private boolean hasTexture = false;
    private float textureU;
    private float textureV;

    
    private boolean hasColor;
    private float red;
    private float green;
    private float blue;

    public void init() {
        clear();
    }

    public void vertex(float x, float y, float z) {
        
        this.vertexBuffer.put(this.vertices * 3, x);
        this.vertexBuffer.put(this.vertices * 3 + 1, y);
        this.vertexBuffer.put(this.vertices * 3 + 2, z);

        
        if (this.hasTexture) {
            this.textureCoordinateBuffer.put(this.vertices * 2, this.textureU);
            this.textureCoordinateBuffer.put(this.vertices * 2 + 1, this.textureV);
        }

        
        if (this.hasColor) {
            this.colorBuffer.put(this.vertices * 3, this.red);
            this.colorBuffer.put(this.vertices * 3 + 1, this.green);
            this.colorBuffer.put(this.vertices * 3 + 2, this.blue);
        }

        this.vertices++;

        
        if (this.vertices == MAX_VERTICES) {
            flush();
        }
    }

    public void texture(float textureU, float textureV) {
        this.hasTexture = true;
        this.textureU = textureU;
        this.textureV = textureV;
    }

    public void color(float red, float green, float blue) {
        this.hasColor = true;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public void flush() {
        this.vertexBuffer.flip();
        this.textureCoordinateBuffer.flip();

        
        glVertexPointer(3, GL_POINTS, this.vertexBuffer);
        if (this.hasTexture) {
            glTexCoordPointer(2, GL_POINTS, this.textureCoordinateBuffer);
        }
        if (this.hasColor) {
            glColorPointer(3, GL_POINTS, this.colorBuffer);
        }

        
        glEnableClientState(GL_VERTEX_ARRAY);
        if (this.hasTexture) {
            glEnableClientState(GL_TEXTURE_COORD_ARRAY);
        }
        if (this.hasColor) {
            glEnableClientState(GL_COLOR_ARRAY);
        }

        
        glDrawArrays(GL_QUADS, GL_POINTS, this.vertices);

        
        glDisableClientState(GL_VERTEX_ARRAY);
        if (this.hasTexture) {
            glDisableClientState(GL_TEXTURE_COORD_ARRAY);
        }
        if (this.hasColor) {
            glDisableClientState(GL_COLOR_ARRAY);
        }
        clear();
    }

    private void clear() {
        this.vertexBuffer.clear();
        this.textureCoordinateBuffer.clear();
        this.vertices = 0;

        this.hasTexture = false;
        this.hasColor = false;
    }
}