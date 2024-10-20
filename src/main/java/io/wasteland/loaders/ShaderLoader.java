package io.wasteland.loaders;

import io.wasteland.renderer.Shader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

import static org.lwjgl.opengl.GL46.*;

public class ShaderLoader {
    private static final int[] status = new int[1];
    private static final Logger log = LoggerFactory.getLogger(ShaderLoader.class);

    public static Shader load(String vertexResName, String fragmentResName) {
        String vsrc = readResource(vertexResName);
        String fsrc = readResource(fragmentResName);
        int vid = create(GL_VERTEX_SHADER, vsrc);
        int fid = create(GL_FRAGMENT_SHADER, fsrc);

        int id = glCreateProgram();
        glAttachShader(id, vid);
        glAttachShader(id, fid);
        glLinkProgram(id);

        return new Shader(id);
    }

    private static String readResource(String name) {
        try(InputStream stream = ShaderLoader.class.getResourceAsStream(name)) {
            byte[] buffer = new byte[256];
            int readed;
            StringBuilder builder = new StringBuilder();
            while((readed = stream.read(buffer)) != -1) {
                builder.append(new String(buffer, 0, readed));
            }
            return builder.toString();
        } catch (IOException e) {
            log.error(e.getMessage());
            return "";
        }
    }

    private static int create(int type, String source) {
        int id = glCreateShader(type);
        glShaderSource(id, source);
        glCompileShader(id);
        glGetShaderiv(id, GL_COMPILE_STATUS, status);
        if(status[0] == 0) {
            log.error(glGetShaderInfoLog(id));
            log.error("Failed to create shader");
            glDeleteShader(id);
            return 0;
        }
        return id;
    }
}
