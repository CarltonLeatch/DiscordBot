package main.java.Model;

import java.util.ArrayList;

public class Vote {

    private int id;
    private String title;
    private ArrayList<Arguments> arguments;
    private long[] AccountId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Arguments> getArguments() {
        return arguments;
    }

    public void setArguments(ArrayList<Arguments> arguments) {
        this.arguments = arguments;
    }

    public long[] getAccountId() {
        return AccountId;
    }

    public void setAccountId(long[] accountId) {
        AccountId = accountId;
    }
}
