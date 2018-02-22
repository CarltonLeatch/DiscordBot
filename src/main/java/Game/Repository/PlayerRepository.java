package main.java.Game.Repository;

import main.java.Game.Hero.Hero;
import main.java.Game.Player.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerRepository {

    List<Player> db = new ArrayList<Player>();

    public void save(Player p){
        db.add(p);
    }

    public List<Player> getAll(){
        return db;
    }

    public void addHero(Player p, Hero h){
       db.get(p.getId()-1).setHero(h);
    }

    public Player findOne(Player p){
        for(Player player : db){
            if(player.getUsername().equalsIgnoreCase(p.getUsername()) && player.getDesc().equalsIgnoreCase(p.getDesc()))
                p = player;
        }
        return p;
    }
}
