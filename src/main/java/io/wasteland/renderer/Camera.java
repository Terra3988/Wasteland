package io.wasteland.renderer;

import org.joml.*;
import org.joml.Math;

public class Camera {
    private static final Matrix4f temp = new Matrix4f();
    private static final Matrix4f temp2 = new Matrix4f();

    public Vector3f position;
    public Vector3f front;
    public Vector3f up;
    public Vector3f right;
    public Vector3f worldUp = new Vector3f(0, 1, 0);

    public float fov;

    public float pitch; // up-down
    public float yaw; // left-right

    public Camera(Vector3f position, float fov) {
        this.position = position;
        this.fov = fov;

        this.pitch = 0f;
        this.yaw = 0f;

        this.up = new Vector3f(0, 1, 0);
        this.right = new Vector3f(0, 0, 0);
        this.front = new Vector3f(0, 0, -1);
        update();
    }

    float speed = 0.1f;

    public void moveRight() {
        position.add(right.mul(speed));
    }

    public void moveLeft() {
        position.sub(right.mul(speed));
    }

    public void moveForward() {
        position.add(front.mul(speed));
    }

    public void moveBackward() {
        position.sub(front.mul(speed));
    }

    public void turn(float x, float y) {
        pitch += y;
        yaw += x;

        pitch = Math.min(Math.max(-89.0f, pitch), 89.0f);
    }

    public void update() {
        Vector3f f = new Vector3f();
        f.x = Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch));
        f.y = Math.sin(Math.toRadians(pitch));
        f.z = Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch));

        front = f.normalize();

        front.cross(worldUp, right); right.normalize();
        right.cross(front, up); up.normalize();
    }

    public Matrix4f getProjection() {
        float aspect = 1280.0f / 720.0f;
        temp.identity();
        temp.perspective(fov, aspect, 0.05f, 100f);
        return temp;
    }

    public Matrix4f getView() {
        temp2.identity();

        Vector3f result = new Vector3f(position);
        front.normalize();
        result.x += front.x;
        result.y += front.y;
        result.z += front.z;

        temp2.lookAt(position, result, up);

        return temp2;
    }

    public Matrix4f getProjview() {
        return getProjection().mul(getView());
    }
}
