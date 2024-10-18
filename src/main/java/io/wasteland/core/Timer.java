package io.wasteland.core;

public class Timer {

    private static final long NS_PER_SECOND = 1000000000L;
    private static final long MAX_NS_PER_UPDATE = 1000000000L;
    private static final int MAX_TICKS_PER_UPDATE = 100;

    private final float ticksPerSecond;
    private long lastTime = System.nanoTime();
    public float timeScale = 1.0F;
    public float fps = 0.0F;
    public float passedTime = 0.0F;
    public int ticks;
    public float partialTicks;


    public Timer(float ticksPerSecond) {
        this.ticksPerSecond = ticksPerSecond;
    }

    public void advanceTime() {
        long now = System.nanoTime();
        long passedNs = now - this.lastTime;
        this.lastTime = now;
        passedNs = Math.max(0, passedNs);
        passedNs = Math.min(MAX_NS_PER_UPDATE, passedNs);
        this.fps = (float) (NS_PER_SECOND / passedNs);
        this.passedTime += passedNs * this.timeScale * this.ticksPerSecond / NS_PER_SECOND;
        this.ticks = (int) this.passedTime;
        this.ticks = Math.min(MAX_TICKS_PER_UPDATE, this.ticks);
        this.passedTime -= this.ticks;
        this.partialTicks = this.passedTime;
    }
}
