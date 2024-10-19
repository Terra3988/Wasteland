package io.wasteland.client;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.lwjgl.glfw.GLFW.*;

public class Window {
    private static final Logger LOGGER = LoggerFactory.getLogger(Window.class.getSimpleName());

    private long window;
    private int width;
    private int height;
    private String caption;

    public Window(int width, int height, String caption) {
        this.width = width;
        this.height = height;
        this.caption = caption;
    }

    public void create() {
        GLFWErrorCallback.createPrint(System.err).set();
        if (!glfwInit()) {
            LOGGER.error("Cannot initialize GLFW");
            Wasteland.getInstance().fatal();
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 6);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        window = glfwCreateWindow(width, height, caption, 0L, 0L);
        if (window == 0) {
            LOGGER.error("Failed to create window");
            Wasteland.getInstance().fatal();
        }
        glfwMakeContextCurrent(window);
        GL.createCapabilities();
    }

    public void update() {
        glfwSwapBuffers(window);
        glfwPollEvents();
    }

    public boolean isCloseRequested() {
        return glfwWindowShouldClose(window);
    }

    public long getWindow() {
        return window;
    }
}
