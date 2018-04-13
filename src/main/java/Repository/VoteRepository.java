package main.java.Repository;

import main.java.Model.Vote;

import java.util.ArrayList;
import java.util.List;

public class VoteRepository {
    private List<Vote> db = new ArrayList<Vote>();

    public void save(Vote v){ db.add(v);}

    public Vote get(int id){return db.get(id);}

    public List<Vote> getDb() {
        return db;
    }
}
