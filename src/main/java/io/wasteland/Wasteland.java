package io.wasteland;

import io.wasteland.level.Tesselator;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.lwjgl.opengl.GL11.*;

public class Wasteland implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(Wasteland.class.getSimpleName());
    private static final Wasteland INSTANCE = new Wasteland();

    private static final int WINDOW_WIDTH = 1280;
    private static final int WINDOW_HEIGHT = 720;
    private static final String WINDOW_TITLE = Wasteland.class.getSimpleName();

    private boolean exitRequested = false;

    // GAME
    public Timer timer;

    private void initHardware() throws LWJGLException {
        Display.setDisplayMode(new DisplayMode(WINDOW_WIDTH, WINDOW_HEIGHT));
        Display.setTitle(WINDOW_TITLE);
        Display.setResizable(false);
        Display.create();

        Mouse.create();

        Keyboard.create();
    }

    private void initGame() {
        timer = new Timer();
    }

    private void initRender() {
        glClearColor(0.5f, 0.8f, 1.0f, 1.0f);
        glClearDepth(1.0);

        glEnable(GL_DEPTH_TEST);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_CULL_FACE);

        glFrontFace(GL_CW);

        glDepthFunc(GL_LEQUAL);
    }

    private void init() throws LWJGLException {
        initHardware();
        initGame();
        initRender();
    }

    private void loop() {
        while (!exitRequested) {
            handleEvents();
            timer.advanceTime();

            update(timer.delta);
            render(timer.delta);
        }
    }

    private void handleEvents() {
        if (Display.isCloseRequested() || Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
            requestExit();

    }

    private void update(float dt) {

    }

    private final Tesselator tesselator = new Tesselator();

    private void render(float dt) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        tesselator.color(1, 0, 0);
        tesselator.vertex(0.5f, 0.5f, 0);
        tesselator.vertex(0.5f, -0.5f, 0);
        tesselator.vertex(-0.5f, -0.5f, 0);
        tesselator.vertex(-0.5f, 0.5f, 0);
        tesselator.flush();

        Display.update();
    }

    private void terminateHardware() {
        Display.destroy();
        Mouse.destroy();
        Keyboard.destroy();
    }

    private void terminate() {
        terminateHardware();
    }

    public void requestExit() {
        exitRequested = true;
    }

    @Override
    public void run() {
        try {
            init();
            loop();
        } catch (LWJGLException e) {
            LOG.error("Failed to start game");
            LOG.error(e.getMessage());
            System.exit(-1);
        }

        terminate();
    }

    public static Wasteland getInstance() {
        return INSTANCE;
    }
}
