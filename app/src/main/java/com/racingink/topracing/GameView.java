package com.racingink.topracing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.racingink.topracing.entity.Car;
import com.racingink.topracing.entity.Obstacle;

import java.util.Timer;
import java.util.TimerTask;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private int life_count;
    private MainActivity mainActivity;
    private GameThread gameThread;
    private Timer timer;
    private long start_time;
    private long race_time;
    private int max_x;
    private int max_y;
    private float touchX;
    private boolean isTouch = false;

    private Bitmap[] background;

    private Bitmap life;

    private Car car;

    private Obstacle obstacle;


    public GameView(Context context, int screen_x, int screen_y) {
        super(context);
        getHolder().addCallback(this);
        mainActivity = (MainActivity) context;
        max_x = screen_x;
        max_y = screen_y;
        timer = new Timer();
        gameThread = new GameThread(this);
        start_time = System.currentTimeMillis();
        car = new Car(scale_bitmap(R.drawable.yellow_car, 180, 180), this, max_x, max_y);
        background = new Bitmap[2];
        background[0] = scale_bitmap(R.drawable.road_1, max_x, max_y);
        background[1] = scale_bitmap(R.drawable.road_2, max_x, max_y);
        life = scale_bitmap(R.drawable.lifes, 65, 65);
        life_count = 3;
        obstacle = new Obstacle(scale_bitmap(R.drawable.obstacle, 120, 120), this, max_x, max_y, max_x / 2);

    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gameThread.setRun(true);
                gameThread.start();
            }
        }, 100);
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void race_draw(Canvas canvas) {
        canvas.drawBitmap(background[0], 0, 0, null);
        checkGameTime();

        obstacle.drawObstacle(canvas);

        //Life
        for (int i = 0; i < life_count; i++) {
            int x = (int) (canvas.getWidth() - 350 + life.getWidth() * 1.5 * i);
            int y = 30;
            canvas.drawBitmap(life, x, y, null);
        }

        //Car
        if (car.getCar_x() <= obstacle.getObstacle_x() + obstacle.getObstacle_model().getWidth()
                && car.getCar_x() >= obstacle.getObstacle_x()
                && car.getCar_y() <= obstacle.getObstacle_y() + obstacle.getObstacle_model().getHeight()
                && car.getCar_y() >= obstacle.getObstacle_y()
                ||
                car.getCar_x() + car.getCar_model().getWidth() <= obstacle.getObstacle_x() + obstacle.getObstacle_model().getWidth()
                        && car.getCar_x() + car.getCar_model().getWidth() >= obstacle.getObstacle_x()
                        && car.getCar_y() <= obstacle.getObstacle_y() + obstacle.getObstacle_model().getHeight()
                        && car.getCar_y() >= obstacle.getObstacle_y()) {
            life_count--;
            obstacle.setObstacle_y(max_y + 30);
            if (life_count == 0) {
                mainActivity.startEndActivity(System.currentTimeMillis() - start_time);
                gameThread.setRun(false);

            }
        }
        if (isTouch) {
            if (touchX > max_x / 2) {
                car.turn_right();
                isTouch = false;
            } else if (touchX < max_x / 2) {
                car.turn_left();
                isTouch = false;
            }
        }
        car.drawCar(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        isTouch = true;
        touchX = event.getRawX();
        return true;
    }

    private void checkGameTime(){
        race_time = System.currentTimeMillis();

      if (race_time - start_time > 30000){
            obstacle.lvlUp();
        }
    }

    private Bitmap scale_bitmap(Integer img_id, int size_x, int size_y) {
        Bitmap buff = BitmapFactory.decodeResource(getResources(), img_id);
        Bitmap finalBitmap = Bitmap.createScaledBitmap(buff, size_x, size_y, false);
        return finalBitmap;
    }
}
