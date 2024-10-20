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
        texture = TextureLoader.load("/atlas.png");

        camera = new Camera(new Vector3f(0, 8, -5), 90.0f);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_CULL_FACE);
        glEnable(GL_DEPTH_TEST);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        ChunkRenderer renderer = new ChunkRenderer();
        Chunk chunk = new Chunk();

        Mesh mesh = renderer.render(chunk);

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

            camera.turn(input.getDeltaX(), -input.getDeltaY());
            camera.update();

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            shader.use();
            shader.uniformMatrix("uProjview", camera.getProjview());
            texture.bind();

            mesh.draw();

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
