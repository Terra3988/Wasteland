package io.wasteland;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.lwjgl.glfw.GLFW.*;

public class Window {
    private static final Logger log = LoggerFactory.getLogger(Window.class);
    private long window;

    private int width;
    private int height;
    private String title;

    public Window(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
    }

    public void init() {
        GLFWErrorCallback.createPrint(System.err).set();
        if(!glfwInit()) {
            log.error("Failed to initialize glfw");
            System.exit(-1);
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 6);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

        window = glfwCreateWindow(width, height, title, 0L, 0L);
        if (window == 0) {
            log.error("Failed to create window");
            dispose();
            System.exit(-1);
        }

        glfwMakeContextCurrent(window);
        GL.createCapabilities();
    }

    public void swapBuffers() {
        glfwSwapBuffers(window);
    }
    public void lockCursor() {
        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
    }

    public void setWindowShouldClose(boolean state) {
        glfwSetWindowShouldClose(window, state);
    }

    public boolean isWindowShouldClose() {
        return glfwWindowShouldClose(window);
    }

    public void dispose() {
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public long getWindow() {
        return window;
    }
}
