package mobiledev.unb.ca.bopit;

import android.app.Activity;
import android.support.v4.view.GestureDetectorCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import java.util.Random;

import android.widget.ImageView;
import android.widget.TextView;

public class Game extends Activity implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener{

    private static final String DEBUG_TAG = "Gestures";
    private GestureDetectorCompat mDetector;
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private static int currentMovement = 0;
    ImageView img_view;
    TextView scoreText;
    int score = 0;

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
        int n = r.nextInt(5) + 1; //1-5
        currentMovement = n;

        //tapped
        if(n == 1){
            img_view.setImageResource(R.drawable.tap_screen);
            TextView text = (TextView) findViewById(R.id.test_text);
            text.setText("Tap Screen");
        }
        else if (n == 2){ //left
            img_view.setImageResource(R.drawable.swipe_left);
            TextView text = (TextView) findViewById(R.id.test_text);
            text.setText("Left Swipe");
        }
        else if(n ==3){
            img_view.setImageResource(R.drawable.swipe_right);
            TextView text = (TextView) findViewById(R.id.test_text);
            text.setText("Right Swipe");
        }
        else if (n == 4){ //left
            img_view.setImageResource(R.drawable.swipe_up);
            TextView text = (TextView) findViewById(R.id.test_text);
            text.setText("Up Swipe");
        }
        else if (n == 5){ //left
            img_view.setImageResource(R.drawable.swipe_down);
            TextView text = (TextView) findViewById(R.id.test_text);
            text.setText("Down Swipe");
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
                if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Log.d(DEBUG_TAG, "onScroll: " + e1.toString() + e2.toString());
                    Log.d(DEBUG_TAG, "up confirmed");

                    if (currentMovement == 4) {
                        score += 10;
                        scoreText.setText("Score: " + score);
                        start();
                    } else {

                        img_view.setImageResource(R.drawable.wrong);
                    }

                }
                if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Log.d(DEBUG_TAG, "onScroll: " + e1.toString() + e2.toString());
                    Log.d(DEBUG_TAG, "down confirmed");

                    if (currentMovement == 5) {
                        score += 10;
                        scoreText.setText("Score: " + score);
                        start();
                    } else {

                        img_view.setImageResource(R.drawable.wrong);
                    }
                }
            } else {
                // right to left swipe
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Log.d(DEBUG_TAG, "onScroll: " + e1.toString() + e2.toString());
                    Log.d(DEBUG_TAG, "left confirmed");

                    if (currentMovement == 2) {
                        score += 10;
                        scoreText.setText("Score: " + score);
                        start();
                    } else {
                        img_view.setImageResource(R.drawable.wrong);
                    }
                }
                // left to right swipe
                else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE  && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Log.d(DEBUG_TAG, "onScroll: " + e1.toString() + e2.toString());
                    Log.d(DEBUG_TAG, "right confirmed");

                    if (currentMovement == 3) {
                        score += 10;
                        scoreText.setText("Score: " + score);
                        start();
                    } else {
                        img_view.setImageResource(R.drawable.wrong);
                    }
                } else {
                    Log.d(DEBUG_TAG, "onScroll: " + e1.toString() + e2.toString());
                }
            }
        }catch(Exception e){
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

        if(currentMovement == 1){
            score += 10;
            scoreText.setText("Score: " + score);
            start();
        } else{
            img_view.setImageResource(R.drawable.wrong);
        }

        return true;
    }

}
