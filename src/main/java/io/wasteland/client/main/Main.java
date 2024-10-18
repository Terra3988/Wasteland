package io.wasteland.client.main;

import io.wasteland.client.Wasteland;

public class Main {
    public static void main(String[] args) {
        Thread thread = new Thread(new Wasteland());
        thread.start();
    }
}
