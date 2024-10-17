package io.wasteland;

import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluBuild2DMipmaps;

public class Textures {
    private static int lastId = Integer.MIN_VALUE;

    public static int loadTexture(String resourceName, int mode) {
        int id = glGenTextures();
        bind(id);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, mode);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, mode);

        InputStream inputStream = Textures.class.getResourceAsStream(resourceName);

        try {
            BufferedImage image = ImageIO.read(inputStream);

            int width = image.getWidth();
            int height = image.getHeight();

            int[] pixels = new int[width * height];
            image.getRGB(0, 0, width, height, pixels, 0, width);

            for (int i = 0; i < pixels.length; i++) {
                int alpha = pixels[i] >> 24 & 0xFF;
                int red = pixels[i] >> 16 & 0xFF;
                int green = pixels[i] >> 8 & 0xFF;
                int blue = pixels[i] & 0xFF;

                pixels[i] = alpha << 24 | blue << 16 | green << 8 | red;
            }

            ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);
            buffer.asIntBuffer().put(pixels);

            gluBuild2DMipmaps(GL_TEXTURE_2D, GL_RGBA, width, height, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

    public static void bind(int id) {
        if (id != lastId) {
            glBindTexture(GL_TEXTURE_2D, id);
            lastId = id;
        }
    }
}
