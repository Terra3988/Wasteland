package io.wasteland;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL46.*;

public class Shader {
    private final int handle;

    public Shader(int handle) {
        this.handle = handle;
    }

    public void use() {
        glUseProgram(handle);
    }

    public void uniformMatrix(String uname, Matrix4f m) {
        int loc = glGetUniformLocation(handle, uname);
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        m.get(buffer);
        glProgramUniformMatrix4fv(handle, loc, false, buffer);
    }

    public void dispose() {
        glDeleteProgram(handle);
    }

    public int getHandle() {
        return handle;
    }
}
