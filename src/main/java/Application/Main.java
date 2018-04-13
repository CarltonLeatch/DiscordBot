package main.java.Application;

import main.java.Listener.MessageReceivedListener;
import main.java.Listener.ReadyListener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public class Main extends ListenerAdapter {

    public static String prefix;
    public static JDA jda;


    public static void main(String args[]) {

        prefix = "!";
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        builder.setToken("NDM0Mjk2MjEzNjAxNTE3NTY4.DbIVjA._xSdignBSiBkJjUhu-Xto2NZ8Ns");
        builder.setAutoReconnect(true);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setGame(Game.of("type !help to get help"));
        jda = null;
        try{
            jda = builder.buildAsync();
        }catch (LoginException | IllegalArgumentException | RateLimitedException e){
            e.printStackTrace();
        }
        jda.addEventListener(new ReadyListener());
        jda.addEventListener(new MessageReceivedListener());
    }
}


