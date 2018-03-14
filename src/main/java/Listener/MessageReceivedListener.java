package main.java.Listener;

import main.java.Application.Main;

import net.dv8tion.jda.core.entities.*;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;


public class MessageReceivedListener extends ListenerAdapter {

    CommandListener commandListener = new CommandListener();

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



        commandListener.CommandListener(command, args, e);
    }
}
