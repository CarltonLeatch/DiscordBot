package main.java.Repository;

import main.java.Model.Vote;

import java.util.ArrayList;
import java.util.List;

public class VoteRepository {
    private List<Vote> db = new ArrayList<Vote>();
    int id = 0;
    public void save(Vote v){
        v.setId(++id);
        db.add(v);}

    public Vote get(int id){return db.get(id);}

    public List<Vote> getDb() {
        return db;
    }

    public void update(Vote v, int id){
        Vote vote = db.get(id);
        vote.setArguments(v.getArguments());
        db.remove(v);
        db.add(vote);
    }


}
