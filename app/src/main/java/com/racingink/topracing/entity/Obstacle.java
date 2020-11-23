package com.racingink.topracing.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.racingink.topracing.GameView;

import java.util.Random;

public class Obstacle {
    private int obstacle_x;
    private int obstacle_y;
    private int max_x;
    private int max_y;
    private Bitmap obstacle_model;
    private int obstacle_speed = 15;
    private Random random;
    private GameView gameView;

    public Obstacle(Bitmap car_model, GameView gameView, int max_x, int max_y, int obstacle_x) {
        this.obstacle_model = car_model;
        this.gameView = gameView;
        this.max_x = max_x;
        this.max_y = max_y;
        this.obstacle_x = obstacle_x;
        this.obstacle_y = max_y - car_model.getHeight();
        random = new Random();
    }


    private void updateObstacle(){
        obstacle_y+=obstacle_speed;
    }

    public void obstacleIsOut(){
        if (obstacle_y>=max_y){
            obstacle_y=-10;
            obstacle_x = random.nextInt(max_x-max_x/3)+max_x/7;
        }
    }

    public void lvlUp(){
        obstacle_speed++;
    }

    public void drawObstacle(Canvas canvas){
        updateObstacle();
        obstacleIsOut();
        canvas.drawBitmap(obstacle_model, obstacle_x, obstacle_y, null);
    }

    public int getObstacle_x() {
        return obstacle_x;
    }

    public void setObstacle_x(int obstacle_x) {
        this.obstacle_x = obstacle_x;
    }

    public int getObstacle_y() {
        return obstacle_y;
    }

    public void setObstacle_y(int obstacle_y) {
        this.obstacle_y = obstacle_y;
    }

    public Bitmap getObstacle_model() {
        return obstacle_model;
    }

    public void setObstacle_model(Bitmap obstacle_model) {
        this.obstacle_model = obstacle_model;
    }
}
