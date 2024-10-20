package io.wasteland.loaders;

import io.wasteland.renderer.Texture;
import org.lwjgl.BufferUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL46.*;

public class TextureLoader {
    private static final Logger log = LoggerFactory.getLogger(TextureLoader.class);

    public static Texture load(String resourceName) {

        BufferedImage image = readTexture(resourceName);
        if (image == null) {
            log.error("Failed to load texture");
            return null;
        }

        int width = image.getWidth();
        int height = image.getHeight();
        int[] pixels = new int[width * height];
        image.getRGB(0, 0, width, height, pixels, 0, width);

        for (int i = 0; i < pixels.length; i++) {
            int a = pixels[i] >> 24 & 0xFF;
            int r = pixels[i] >> 16 & 0xFF;
            int g = pixels[i] >> 8 & 0xFF;
            int b = pixels[i] & 0xFF;

            pixels[i] = a << 24 | b << 16 | g << 8 | r;
        }

        ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);
        buffer.asIntBuffer().put(pixels);

        int id = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, id);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        glGenerateMipmap(GL_TEXTURE_2D);
        return new Texture(id, width, height);
    }

    private static BufferedImage readTexture(String resName) {
        try(InputStream stream = TextureLoader.class.getResourceAsStream(resName)) {
            BufferedImage image = ImageIO.read(stream);
            return image;
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
