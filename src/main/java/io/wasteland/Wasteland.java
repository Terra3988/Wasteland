package io.wasteland;

import io.wasteland.loaders.ShaderLoader;
import io.wasteland.loaders.TextureLoader;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL46.*;

public class Wasteland implements Runnable {

    private static float[] vertices = {
            -0.5f,  0.5f, -1.0f,
            -0.5f, -0.5f, -1.0f,
             0.5f, -0.5f, -1.0f,

             0.5f, -0.5f, -1.0f,
             0.5f,  0.5f, -1.0f,
            -0.5f,  0.5f, -1.0f,
    };

    private static float[] uvs = {
            0, 0,
            0, 1,
            1, 1,

            1, 1,
            1, 0,
            0, 0
    };

    private static final Logger log = LoggerFactory.getLogger(Wasteland.class);
    private Window window;
    private Input input;

    private Shader shader;
    private Texture texture;
    private Camera camera;

    @Override
    public void run() {
        window = new Window(1280, 720, "Wasteland");
        window.init();

        input = Input.getInstance();
        input.init(window.getWindow());

        shader = ShaderLoader.load("/shaders/blit.vsh", "/shaders/blit.fsh");
        texture = TextureLoader.load("/block.png");

        camera = new Camera(new Vector3f(0, 0, 3), 90.0f);
        glEnable(GL_TEXTURE_2D);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        int vao = glGenVertexArrays();
        int vbo = glGenBuffers();
        int tbo = glGenBuffers();

        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length);
        buffer.put(vertices).position(0);

        FloatBuffer buffer2 = BufferUtils.createFloatBuffer(uvs.length);
        buffer2.put(uvs).position(0);

        glBindVertexArray(vao);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(0);

        glBindBuffer(GL_ARRAY_BUFFER, tbo);
        glBufferData(GL_ARRAY_BUFFER, buffer2, GL_STATIC_DRAW);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(1);

        glBindVertexArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        window.lockCursor();

        while(!window.isWindowShouldClose()) {
            if (input.isJustPressed(Input.KEY_ESCAPE)) {
                window.setWindowShouldClose(true);
            }
            if(input.isPressed(Input.KEY_W)) {
                camera.moveForward();
            }

            if(input.isPressed(Input.KEY_S)) {
                camera.moveBackward();
            }

            if(input.isPressed(Input.KEY_A)) {
                camera.moveLeft();
            }

            if(input.isPressed(Input.KEY_D)) {
                camera.moveRight();
            }

            camera.turn(input.getDeltaX(), input.getDeltaY());
            camera.update();

            glClear(GL_COLOR_BUFFER_BIT);
            shader.use();
            shader.uniformMatrix("uProjview", camera.getProjview());
            texture.bind();

            glBindVertexArray(vao);
            glDrawArrays(GL_TRIANGLES, 0, 6);
            glBindVertexArray(0);

            input.pollEvents();
            window.swapBuffers();
        }

        texture.dispose();
        shader.dispose();

        input.dispose(window.getWindow());
        window.dispose();
    }

    public static void main(String[] args) {
        new Thread(new Wasteland()).start();
    }
}
