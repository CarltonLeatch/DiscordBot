package main.java.Game.PlayerDao;

import main.java.Game.Hero.Hero;
import main.java.Game.Player.Player;
import main.java.Game.Repository.PlayerRepository;

import java.util.List;


public class PlayerDao {

    PlayerRepository db = new PlayerRepository();
    int i = 0;

    public Player player(String username, String desc){
        Player p = new Player(username,desc);
        p.setId(++i);
        db.save(p);
        return p;
    }

    public List<Player> getAllPlayers(){
        return db.getAll();
    }

    public void addHero(Player p, Hero h){
        db.addHero(findOne(p),h);
    }

    public Player findOne(Player p){
        return db.findOne(p);
    }
}
