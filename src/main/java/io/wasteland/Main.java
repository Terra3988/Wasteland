package io.wasteland;

public class Main {
    public static void main(String[] args) {
        Thread thread = new Thread(Wasteland.getInstance());
        thread.start();
    }
}
