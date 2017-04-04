package mobiledev.unb.ca.bopit;

public class Highscore {
    private int id = 0;
    private String name;
    private int score;

    public Highscore() {}

    public Highscore(String name, int score) {
        this.name=name;
        this.score=score;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}
