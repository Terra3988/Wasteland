package io.wasteland.client;

import io.wasteland.core.Timer;
import io.wasteland.world.level.OldChunk;
import io.wasteland.world.level.Level;
import io.wasteland.client.renderer.LevelRenderer;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.glu.GLU;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.lwjgl.opengl.GL11.*;

public class Wasteland implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(Wasteland.class.getSimpleName());
    private static Wasteland INSTANCE = null;

    public Wasteland() {
        if (INSTANCE == null) {
            INSTANCE = this;
        }
    }

    // BACKEND
    public DisplayMode displayMode;
    private boolean exitRequested = false;

    // GAME
    private Level level;
    private LevelRenderer levelRenderer;
    private Player player;
    private final Timer timer = new Timer(60);

    private void initHardware() throws LWJGLException {
        displayMode = new DisplayMode(1280, 720);

        Display.setDisplayMode(displayMode);
        Display.setTitle("Minecraft");
        Display.setResizable(false);

        Display.create();

        Mouse.create();
        Keyboard.create();

        Mouse.setGrabbed(true);
    }

    private void initRender() {
        glEnable(GL_TEXTURE_2D);
        glShadeModel(GL_SMOOTH);
        glClearColor(0.5f, 0.8f, 1.0f, 1.0f);
        glClearDepth(1.0);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glDepthFunc(GL_LEQUAL);

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        float aspect = (float) displayMode.getWidth() / displayMode.getHeight();
        GLU.gluPerspective(70, aspect, 0.05f, 200f);
        glMatrixMode(GL_MODELVIEW);
    }

    private void initGame() {
        this.level = new Level(256, 256, 64);
        this.levelRenderer = new LevelRenderer(this.level);
        this.player = new Player(this.level);
    }

    private void init() throws LWJGLException {
        initHardware();
        initRender();
        initGame();
    }

    private void loop() {
        int frames = 0;
        long lastTime = System.currentTimeMillis();

        while(!exitRequested) {
            handleEvents();

            update();
            render(this.timer.partialTicks);

            frames++;
            while(System.currentTimeMillis() >= lastTime + 1000L) {
                System.out.println(frames + " fps, " + OldChunk.updates);
                OldChunk.updates = 0;
                lastTime += 1000L;
                frames = 0;
            }
        }
    }

    private void handleEvents() {
        if (Display.isCloseRequested() || Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
            requestExit();
    }

    private void update() {
        this.timer.advanceTime();

        for (int i = 0; i < this.timer.ticks; i++) {
            tick();
        }
    }

    private void tick() {
        this.player.tick();
    }

    private void moveCameraToPlayer(float partialTicks) {
        Player player = this.player;

        glTranslatef(0.0f, 0.0f, -0.3f);

        glRotatef(player.xRotation, 1.0f, 0.0f, 0.0f);
        glRotatef(player.yRotation, 0.0f, 1.0f, 0.0f);

        double x = this.player.prevX + (this.player.x - this.player.prevX) * partialTicks;
        double y = this.player.prevY + (this.player.y - this.player.prevY) * partialTicks;
        double z = this.player.prevZ + (this.player.z - this.player.prevZ) * partialTicks;

        glTranslated(-x, -y, -z);
    }

    private void render(float partialTicks) {
        float motionX = Mouse.getDX();
        float motionY = Mouse.getDY();

        this.player.turn(motionX, motionY);

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glLoadIdentity();

        moveCameraToPlayer(partialTicks);

        this.levelRenderer.render(0);
        this.levelRenderer.render(1);

        Display.update();
    }

    private void dispose() {
        this.level.save();

        Keyboard.destroy();
        Mouse.destroy();
        Display.destroy();
        INSTANCE = null;
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
            e.printStackTrace();
            System.exit(-1);
        }
        dispose();
    }

    public static Wasteland getInstance() {
        return  INSTANCE;
    }
}
