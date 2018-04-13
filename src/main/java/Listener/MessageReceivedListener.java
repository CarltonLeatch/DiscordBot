package main.java.Listener;

import main.java.Application.Main;

import main.java.Model.Arguments;
import main.java.Model.Vote;
import main.java.Repository.VoteRepository;
import net.dv8tion.jda.core.entities.*;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;


public class MessageReceivedListener extends ListenerAdapter {

        VoteRepository db = new VoteRepository();
    @SuppressWarnings("unused")
    public void onMessageReceived(MessageReceivedEvent e) {

        if (!e.getMessage().getContent().startsWith(Main.prefix)) return;

        if (e.getChannelType() == ChannelType.PRIVATE) {
            System.out.println(String.format("[PRIVATE MSG] %s#%s: %s", e.getAuthor().getName(), e.getAuthor().getDiscriminator(), e.getMessage().getRawContent()));
        } else {
            System.out.println(String.format("[PUBLIC MSG] [%s][%s] %s#%s: %s", e.getGuild().getName(), e.getChannel().getName(), e.getAuthor().getName(), e.getAuthor().getDiscriminator(), e.getMessage().getRawContent()));
        }

        String command = e.getMessage().getStrippedContent().replace(Main.prefix, "").split(" ")[0];
        String[] args = e.getMessage().getContent().replace(Main.prefix, "").replace(command, "").split(" ");


        System.out.println("Message: " + e.getMessage().getRawContent() + "\nCommand " + command);


            int id = 0;

        switch (command) {
            case "title": {
                Vote vote = new Vote();
                vote.setId(id++);
                id = vote.getId();
                String title= "";
                for(String s : args)
                    title += s + " ";
                vote.setTitle(title);
                e.getChannel().sendMessage("Tytuł został ustawiony").complete();
                System.out.println(vote);
               db.save(vote);

                break;
            }
            case "argument":{
                Vote vote = db.get(id);
                if(vote == null)
                    e.getChannel().sendMessage("Najpierw musisz dodać tytuł").complete();

                    for(String s : args){
                        Arguments a = new Arguments();
                        a.setArgument(s);
                        vote.getArguments().add(a);
                    }
                    for(Arguments a : vote.getArguments())
                        e.getChannel().sendMessage(a.getArgument()).complete();
                db.save(vote);
                break;
            }

            default: {
                e.getChannel().sendMessage("Wpisz !help by otrzymać pomoc").complete();
            }
        }


    }
}
