package mobiledev.unb.ca.bopit;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import java.util.ArrayList;
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

        //Testing
        AddTask addTask = new AddTask();
        addTask.execute("Jonny", "100");

        String searchText = "";
        QueryTask qTask = new QueryTask();
        qTask.execute(searchText);


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
            startActivity(intent);

            return true;
        }
    }

    private class AddTask extends AsyncTask<String, Void, Void> {

        protected Void doInBackground(String... params) {
            // TODO Get the item and number that were passed to this method
            // as params. Add a corresponding row to the the database.
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            String name = params[0];
            String score = params[1];

            ContentValues values = new ContentValues();
            values.put(mDBHelper.NAME, name);
            values.put(mDBHelper.SCORE, score);
            db.insert("highscores", null, values);

            return null;
        }

        protected void onPostExecute(Void result) {

            // TODO You will need to write a bit of extra code to get the
            // UI to behave nicely, e.g., showing and hiding the keyboard
            // at the right time, clearing text fields appropriately. Some
            // of that code will likely go here, but you might also make
            // changes elsewhere in the app. Exactly how you make the
            // UI behave is up to you, but you should make reasonable
            // choices.



        }
    }


    private class QueryTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... params) {
            // TODO Get the query String from params. Query the database to
            // retrieve all rows that have an item that matches this query.
            // Iterate the Cursor returned from querying the database,
            // and return a String representing the results of this query.
            // HINT: See the link in README.md for documentation on Cursor,
            // in particular the methods moveToFirst(), isAfterLast(), and
            // moveToNext().

            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            String qparam = params[0];
            String[] projection = {
                    mDBHelper._ID,
                    mDBHelper.NAME,
                    mDBHelper.SCORE

            };

            String selection = mDBHelper.NAME + " = ?";
            String[] selectionArgs = {qparam};

            String sortOrder = mDBHelper.SCORE + " DESC";

            Cursor cursor = db.query(
                    mDBHelper.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    sortOrder
            );

            List list = new ArrayList<>();

            while(cursor.moveToNext()){
                String temp = cursor.getString(1) + ": " + cursor.getString(2);
                list.add(temp);

            }
            Log.i("Testing- ", list.toString());


            return list.toString();
        }

        protected void onPostExecute(String result) {
            // TODO Set the results TextView to display result, as
            // shown in the sample screenshot in README.md. If result is
            // empty, display a message indicating so. Again, you might
            // need to write a bit of extra code here, or elsewhere, to get
            // the UI to behave nicely.
//            Log.i("Post Query- ", result);
//            if(result != null) {
//                mResultsTextView.setText(result);
//
//            }else{
//                mResultsTextView.setText("No items matching that entry");
//            }

        }
    }
}