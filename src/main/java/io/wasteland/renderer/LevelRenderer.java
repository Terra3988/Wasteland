package io.wasteland.renderer;

import io.wasteland.Chunk;
import io.wasteland.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LevelRenderer {
    private static final Logger log = LoggerFactory.getLogger(LevelRenderer.class);
    private Level level;

    public LevelRenderer(Level level) {
        this.level = level;
    }

    public void rebuild() {
        ChunkRenderer renderer = new ChunkRenderer();
        for (int x = 0; x < level.width; x++) {
            for (int z = 0; z < level.height; z++) {
                for (int y = 0; y < level.depth; y++) {
                    Chunk chunk = level.getChunk(x, y, z);
                    if (chunk == null)
                        continue;
                    renderer.render(chunk);
                }
            }
        }
    }

    public void render() {
        for (int x = 0; x < level.width; x++) {
            for (int z = 0; z < level.height; z++) {
                for (int y = 0; y < level.depth; y++) {
                    Chunk chunk = level.getChunk(x, y, z);
                    if (chunk == null)
                        continue;
                    chunk.draw();
                }
            }
        }
    }
}
