package main.java.Listener;

import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class ReadyListener extends ListenerAdapter{

    public void onReady(ReadyEvent e){
        System.out.println(String.format("[%s#%s] I'm Online !", e.getJDA().getSelfUser(), e.getJDA().getSelfUser().getDiscriminator()));
    }

}
