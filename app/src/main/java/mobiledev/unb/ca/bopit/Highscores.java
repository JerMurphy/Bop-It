package mobiledev.unb.ca.bopit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class Highscores extends Activity {
    private GestureDetectorCompat mDetector;
    private DBHelper mDBHelper;
    private List<Highscore> hsList;
    private int hsCount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);
        mDetector = new GestureDetectorCompat(this, new MyGestureListener());
        mDBHelper = new DBHelper(this);
        TextView[] textViewArr = {
                (TextView) findViewById(R.id.hs1),
                (TextView) findViewById(R.id.hs2),
                (TextView) findViewById(R.id.hs3),
                (TextView) findViewById(R.id.hs4),
                (TextView) findViewById(R.id.hs5),
                (TextView) findViewById(R.id.hs6),
                (TextView) findViewById(R.id.hs7),
                (TextView) findViewById(R.id.hs8),
                (TextView) findViewById(R.id.hs9),
                (TextView) findViewById(R.id.hs10)};

        viewHighscores(textViewArr);
    }

    // update the recycler view to show highscores
    public void viewHighscores(TextView[] textViewArr) {
        hsList = mDBHelper.getAllHighscores();
        hsCount = mDBHelper.getHighscoreCount();

        for (int i = 0; i < hsCount; i++) {
            Highscore hs = hsList.get(i);
            String text = hs.getName() + ":\t" + hs.getScore();
            textViewArr[i].setText(text);
        }

        for (Highscore hs : hsList) {
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