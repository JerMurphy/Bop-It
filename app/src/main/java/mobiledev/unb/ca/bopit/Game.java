package mobiledev.unb.ca.bopit;

import android.app.Activity;
import android.os.CountDownTimer;
import android.support.v4.view.GestureDetectorCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import java.util.Random;

import android.widget.ImageView;
import android.widget.TextView;

public class Game extends Activity implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener{

    private static final String DEBUG_TAG = "DEBUG";
    private GestureDetectorCompat mDetector;
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private static int correctAction = 0;
    private ImageView img_view;
    private int score = 0;
    private TextView scoreText;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mDetector = new GestureDetectorCompat(this,this);

        // Set the gesture detector as the double tap listener.
        mDetector.setOnDoubleTapListener(this);
        img_view = (ImageView) findViewById(R.id.game_imgview);
        scoreText = (TextView) findViewById(R.id.score);

        start();
    }

    public void start(){
        Random r = new Random();
        correctAction = r.nextInt(5) + 1; //1-5

        // 3 seconds - why is this timer shit
        timer = new CountDownTimer(3000, 1000) {
            TextView timeLeft = (TextView) findViewById(R.id.timer);
            public void onTick(long millisUntilFinished) {
                timeLeft.setText("" + millisUntilFinished/1000);
            }

            public void onFinish() {
                timeLeft.setText("" + 0);
                resolve(0);
            }
        }.start();

        //tapped
        if(correctAction == 1){
            img_view.setImageResource(R.drawable.tap_screen);
            TextView text = (TextView) findViewById(R.id.action_text);
            text.setText("Tap Screen");
        }
        else if (correctAction == 2){
            img_view.setImageResource(R.drawable.swipe_left);
            TextView text = (TextView) findViewById(R.id.action_text);
            text.setText("Left Swipe");
        }
        else if(correctAction == 3){
            img_view.setImageResource(R.drawable.swipe_right);
            TextView text = (TextView) findViewById(R.id.action_text);
            text.setText("Right Swipe");
        }
        else if (correctAction == 4){
            img_view.setImageResource(R.drawable.swipe_up);
            TextView text = (TextView) findViewById(R.id.action_text);
            text.setText("Up Swipe");
        }
        else if (correctAction == 5){
            img_view.setImageResource(R.drawable.swipe_down);
            TextView text = (TextView) findViewById(R.id.action_text);
            text.setText("Down Swipe");
        }
        else if (correctAction == 6) {
            //tilt left?
            //remember to change random number generator!
        }
        else if (correctAction == 7) {
            //tilt right?
        }
    }

    // check to see if user inputted the correct action
    public void resolve(int userAction) {
        timer.cancel();

        // congrats!
        if (userAction == correctAction) {
            score += 10;
            scoreText.setText("Score: " + score);
            start();
        }
        // you suck!
        else {
            TextView text = (TextView) findViewById(R.id.action_text);
            text.setText("Click anywhere to restart!");
            img_view.setImageResource(R.drawable.wrong);
            correctAction = 0; // indicates game over
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);

        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent event) {
        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        try {
            if (Math.abs(e1.getY() - e2.getY()) > Math.abs(e1.getX() - e2.getX())) {
                // up or down swipe
                if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Log.d(DEBUG_TAG, "action: up swipe");
                    resolve(4);
                }
                if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Log.d(DEBUG_TAG, "action: down swipe");
                    resolve(5);
                }
            } else {
                // right to left swipe
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Log.d(DEBUG_TAG, "action: left swipe");
                    resolve(2);
                }
                // left to right swipe
                else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE  && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Log.d(DEBUG_TAG, "action: right swipe");
                    resolve(3);
                }
            }
        } catch(Exception e){
            Log.d(DEBUG_TAG, "Exception: " + e.getMessage());
        }

        return false;
    }

    @Override
    public void onLongPress(MotionEvent event) {
        Log.d(DEBUG_TAG, "onLongPress: " + event.toString());
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent event) {
        Log.d(DEBUG_TAG, "onShowPress: " + event.toString());
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent event) {
        Log.d(DEBUG_TAG, "onDoubleTap: " + event.toString());

        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent event) {
        Log.d(DEBUG_TAG, "onDoubleTapEvent: " + event.toString());

        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) {
        Log.d(DEBUG_TAG, "onSingleTapConfirmed: " + event.toString());

        if (correctAction == 0) {
            score = 0;
            scoreText.setText("Score: " + score);
            start();
        }
        else {
            resolve(1);
        }

        return false;
    }

}
