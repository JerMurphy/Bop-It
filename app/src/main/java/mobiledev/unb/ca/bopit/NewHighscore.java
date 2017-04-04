package mobiledev.unb.ca.bopit;

import android.app.Activity;
import android.content.Intent;
import android.gesture.Gesture;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NewHighscore extends Activity {
    GestureDetectorCompat mDetector;
    DBHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_highscore);
        mDetector = new GestureDetectorCompat(this, new NewHighscore.MyGestureListener());
        mDBHelper = new DBHelper(this);

        final EditText name_text = (EditText) findViewById(R.id._name);

        final int score = getIntent().getIntExtra("newScore", 0); //user highscore
        TextView score_text = (TextView) findViewById(R.id.score);
        score_text.setText(String.valueOf(score));

        Button submit_btn = (Button) findViewById(R.id.submit_btn);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            String name;

            @Override
            public void onClick(View v) {
                name = (name_text.getText().toString().matches("")) ? "Bop it Bro" : name_text.getText().toString(); //Bop it Bro for empty input
                Highscore hs = new Highscore(name, score);
                mDBHelper.addHighscore(hs); //add score to database

                Intent intent = new Intent(NewHighscore.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });



    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            hideSoftKeyboard(NewHighscore.this);

            return true;
        }
    }


}
