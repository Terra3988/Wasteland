package io.wasteland;

import java.util.concurrent.TimeUnit;

public class Timer {

    public long lastTime;
    public float delta;

    public Timer() {
        lastTime = now();
    }

    public long now() {
        return System.nanoTime();
    }

    public void advanceTime() {
        long newTime = now();
        delta = (newTime - lastTime) / 1000_000.0f;
        lastTime = newTime;
    }
}
