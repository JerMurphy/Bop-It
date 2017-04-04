package mobiledev.unb.ca.bopit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "mydb";
    public static final String TABLE_NAME = "highscores";
    final static String _ID = "_id";
    public static final String NAME = "name";
    public static final String SCORE = "score";
    private static final String TAG = "DBHelper";

    DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //id, name, score
        String CREATE_TABLE_QUERY =
                "CREATE TABLE " + TABLE_NAME + " (" + _ID
                        + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + NAME + " TEXT, " +
                        SCORE + " INT);";
        db.execSQL(CREATE_TABLE_QUERY);
    }

    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "onUpgrade");

        String DROP_TABLE_QUERY =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(DROP_TABLE_QUERY);
        onCreate(db);
    }

    public void addHighscore(Highscore hs) {
        SQLiteDatabase db = this.getWritableDatabase();

        // get values
        ContentValues values = new ContentValues();
        values.put(NAME, hs.getName());
        values.put(SCORE, hs.getScore());

        // database entry
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    // returns a list, highest to lowest
    public List<Highscore> getAllHighscores() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Highscore> hsList = new ArrayList<Highscore>();

        String SELECT_ALL_QUERY = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + SCORE + " DESC";
        Cursor cursor = db.rawQuery(SELECT_ALL_QUERY, null);
        if (cursor.moveToFirst()) {
            do {
                Highscore hs = new Highscore();
                hs.setId(cursor.getInt(0));
                hs.setName(cursor.getString(1));
                hs.setScore(cursor.getInt(2));

                hsList.add(hs);
            } while (cursor.moveToNext());
        }

        return hsList;
    }

    public int getHighscoreCount() {
        SQLiteDatabase db = this.getReadableDatabase();

        String COUNT_QUERY = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(COUNT_QUERY, null);

        return cursor.getCount();
    }

    public void deleteHighscore(Highscore hs) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, _ID + " = ?",
                new String[] { String.valueOf(hs.getId())} );
        db.close();
    }
}