package main.java.Model;

import java.util.ArrayList;
import java.util.List;

public class Vote {

    private int id;
    private String title;
    private List<Arguments> arguments;
    private List<String> AccountId;

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

    public List<Arguments> getArguments() {
        return arguments;
    }

    public void setArguments(List<Arguments> arguments) {
        this.arguments = arguments;
    }

    public List<String> getAccountId() {
        return AccountId;
    }

    public void setAccountId(List<String> accountId) {
        AccountId = accountId;
    }
}



