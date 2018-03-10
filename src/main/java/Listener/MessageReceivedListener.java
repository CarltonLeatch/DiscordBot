package main.java.Listener;

import main.java.Application.Main;
import main.java.Game.Hero.Hero;
import main.java.Game.Player.Player;
import main.java.Game.PlayerDao.PlayerDao;


import main.java.Model.Author;
import net.dv8tion.jda.client.requests.restaction.pagination.MentionPaginationAction;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.entities.impl.UserImpl;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.requests.Route;

public class MessageReceivedListener extends ListenerAdapter {

    PlayerDao playerDao = new PlayerDao();
    CommandListener commandListener = new CommandListener();
    @SuppressWarnings("unused")
    public void onMessageReceived(MessageReceivedEvent e) {

        if (!e.getMessage().getContent().startsWith(Main.prefix)) return;

        if (e.getChannelType() == ChannelType.PRIVATE) {
            System.out.println(String.format("[DM] %s#%s: %s", e.getAuthor().getName(), e.getAuthor().getDiscriminator(), e.getMessage().getRawContent()));
        } else {
            System.out.println(String.format("[%s][%s] %s#%s: %s", e.getGuild().getName(), e.getChannel().getName(), e.getAuthor().getName(), e.getAuthor().getDiscriminator(), e.getMessage().getRawContent()));
        }

        Author author = new Author(e.getAuthor().getName(),e.getAuthor().getDiscriminator(),e.getAuthor().getIdLong(),e.getAuthor().isBot());

        System.out.println(author);

        String command = e.getMessage().getStrippedContent().replace(Main.prefix, "").split(" ")[0];

        String[] args = e.getMessage().getContent().replace(Main.prefix, "").replace(command, "").split(" ");

        System.out.println("Message: " + e.getMessage().getRawContent() + "\nCommand " + command);

        commandListener.CommandListener(command,args,e);
    }
}
