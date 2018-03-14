package main.java.Listener;


import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandListener {

    public void CommandListener(String command, String[] args, MessageReceivedEvent e) {
        switch (command.toLowerCase()) {
            case "start": {
                e.getChannel().sendMessage("test").complete();
                break;
            }
            case "test" : {
                e.getChannel().sendMessage("no więc tak");
            }
            default: {
                System.out.println("wrong command");
                e.getChannel().sendMessage(String.format("Wrong command, %s !\n Type !help to get more information", e.getAuthor())).complete();
                break;
            }
        }
    }
}
