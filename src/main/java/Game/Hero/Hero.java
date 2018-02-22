package main.java.Game.Hero;



public class Hero {

    private Long id;
    private String heroUsername;
    private int level;

    public Hero(String heroUsername) {
        this.heroUsername = heroUsername;
        this.level = 1;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHeroUsername() {
        return heroUsername;
    }

    public void setHeroUsername(String heroUsername) {
        this.heroUsername = heroUsername;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
