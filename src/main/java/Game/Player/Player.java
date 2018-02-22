package main.java.Game.Player;

import main.java.Game.Hero.Hero;

public class Player {

    private int id;
    private String username;
    private String desc;
    private Hero hero;

    public Player( String username, String desc) {
        this.username = username;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }
}
