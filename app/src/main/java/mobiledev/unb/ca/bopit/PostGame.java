package mobiledev.unb.ca.bopit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.List;

public class PostGame extends Activity{
    DBHelper mDBHelper;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<Highscore> hsList = mDBHelper.getAllHighscores();
        int hsCount = mDBHelper.getHighscoreCount(); //total # highscores
        int score = getIntent().getIntExtra("newScore", 0); //user highscore
        int minScore = (hsCount >= 10) ? hsList.get(hsCount-1).getScore() : 0; //lowest highscore on leaderboard
        if ( (hsCount >= 10) && (score > minScore) ) { //need to delete
            mDBHelper.deleteHighscore(hsList.get(hsCount-1));
        }
        if (score > minScore) {
            Intent intent = new Intent(PostGame.this, NewHighscore.class);
            intent.putExtra("newScore", score);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(PostGame.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

    }

}
