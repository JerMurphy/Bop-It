package mobiledev.unb.ca.bopit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);
        mDetector = new GestureDetectorCompat(this, new MyGestureListener());
        mDBHelper = new DBHelper(this);

        DbCalls db = new DbCalls();
        db.execute();
    }

    // argument
    private class DbCalls extends AsyncTask<String, Void, Void> {
        private List<Highscore> hsList;
        private int hsCount;

        @Override
        protected Void doInBackground(String... strings) {
            hsList = mDBHelper.getAllHighscores();
            hsCount = mDBHelper.getHighscoreCount();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
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

            for (int i = 0; i < hsCount; i++) {
                Highscore hs = hsList.get(i);
                String text = hs.getName() + ":\t" + hs.getScore();
                textViewArr[i].setText(text);
            }

            super.onPostExecute(aVoid);
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