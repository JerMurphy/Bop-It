package mobiledev.unb.ca.bopit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import java.util.List;

public class Highscores extends Activity {
    private GestureDetectorCompat mDetector;
    private DBHelper mDBHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);
        mDetector = new GestureDetectorCompat(this, new MyGestureListener());
        mDBHelper = new DBHelper(this);

        // if score > 0, it's a new score!.. or they suck
        int score = getIntent().getIntExtra("newScore", 0);
        if (score == 0) {
            viewHighscores();
        }
        else { //new highscore
            mDBHelper.addHighscore(new Highscore("Bop it Bro", score));
            viewHighscores();
        }

    }

    // get all current high scores, and edit the recycler view
    public void viewHighscores() {
        List<Highscore> hsList = mDBHelper.getAllHighscores();
        for (Highscore hs: hsList) {
            String log = ("Id: " + hs.getId() + ", Name: " + hs.getName() + ", Score: " + hs.getScore());
            Log.d("Highscore", log);

        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Intent intent = new Intent(Highscores.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            return true;
        }
    }

} //end of file