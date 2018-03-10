package main.java.Model;

public class Author {


    private String username;
    private String descriminator;
    private Long id;
    private boolean bot;

    public Author(String username, String descriminator, Long id, boolean bot) {
        this.username = username;
        this.descriminator = descriminator;
        this.id = id;
        this.bot = bot;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescriminator() {
        return descriminator;
    }

    public void setDescriminator(String descriminator) {
        this.descriminator = descriminator;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isBot() {
        return bot;
    }

    public void setBot(boolean bot) {
        this.bot = bot;
    }
}
