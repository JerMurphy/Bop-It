package mobiledev.unb.ca.bopit;

public class Highscore {
    private int id;
    private String name;
    private int score;

    public Highscore(int id,String name, int score)
    {
        this.id=id;
        this.name=name;
        this.score=score;
    }

    public int getScore() {
        return score;
    }
}
