package com.racingink.topracing.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.racingink.topracing.GameView;

public class Car implements Driving {
    private int car_x;
    private int car_y;
    private int max_x;
    private int max_y;
    private int car_speed;
    private Bitmap car_model;

    private GameView gameView;

    public Car(Bitmap car_model, GameView gameView, int max_x, int max_y) {
        this.car_model = car_model;
        this.gameView = gameView;
        this.max_x = max_x;
        this.max_y = max_y;

        this.car_speed = 35;
        this.car_x = max_x / 2;
        this.car_y = max_y - car_model.getHeight() - 10;
    }


    @Override
    public void turn_left() {
        if (car_speed > 0) {
            car_speed = car_speed * -1;
        }
        if (car_x >= car_model.getWidth()) {
            car_x += car_speed;
        }
    }

    @Override
    public void turn_right() {
        if (car_speed < 0) {
            car_speed = car_speed * -1;
        }
        if (car_x <= max_x - car_model.getWidth()*2) {
            car_x += car_speed;
        }
    }

    public void drawCar(Canvas canvas) {
        canvas.drawBitmap(car_model, car_x, car_y, null);
    }

    public void setCar_x(int car_x) {
        this.car_x = car_x;
    }

    public int getCar_x() {
        return car_x;
    }

    public Bitmap getCar_model() {
        return car_model;
    }

    public void setCar_model(Bitmap car_model) {
        this.car_model = car_model;
    }

    public int getCar_y() {
        return car_y;
    }

    public void setCar_y(int car_y) {
        this.car_y = car_y;
    }
}
