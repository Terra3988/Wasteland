package io.wasteland.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.lwjgl.opengl.GL46.*;

public class Wasteland implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(Wasteland.class.getSimpleName());
    private static Wasteland INSTANCE = null;

    public Wasteland() {
        if (INSTANCE == null) {
            INSTANCE = this;
        }
    }

    public Window window;

    private void initHardware() {
        window = new Window(1280, 720, "Wasteland");
        window.create();
    }

    private void initRender() {

    }

    private void initGame() {

    }

    private void init() {
        initHardware();
        initRender();
        initGame();
    }

    private void processEvents() {

    }

    private void update() {

    }

    private void render() {
        glClearColor(0.5f, 0.8f, 1.0f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT);

        window.update();
    }

    private void loop() {
        while (!window.isCloseRequested()) {
            processEvents();

            update();
            render();
        }
    }

    @Override
    public void run() {
        init();
        loop();
    }

    public void fatal() {
        System.exit(-1);
    }

    public static Wasteland getInstance() {
        return INSTANCE;
    }
}
