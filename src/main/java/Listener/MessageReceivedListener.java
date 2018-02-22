package main.java.Listener;

import main.java.Application.Main;
import main.java.Game.Hero.Hero;
import main.java.Game.Player.Player;
import main.java.Game.PlayerDao.PlayerDao;


import net.dv8tion.jda.client.requests.restaction.pagination.MentionPaginationAction;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.entities.impl.UserImpl;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.requests.Route;

public class MessageReceivedListener extends ListenerAdapter {

    PlayerDao playerDao = new PlayerDao();
    @SuppressWarnings("unused")
    public void onMessageReceived(MessageReceivedEvent e) {

        if (!e.getMessage().getContent().startsWith(Main.prefix)) return;

        if (e.getChannelType() == ChannelType.PRIVATE) {
            System.out.println(String.format("[DM] %s#%s: %s", e.getAuthor().getName(), e.getAuthor().getDiscriminator(), e.getMessage().getRawContent()));
        } else {
            System.out.println(String.format("[%s][%s] %s#%s: %s", e.getGuild().getName(), e.getChannel().getName(), e.getAuthor().getName(), e.getAuthor().getDiscriminator(), e.getMessage().getRawContent()));
        }

        String command = e.getMessage().getStrippedContent().replace(Main.prefix, "").split(" ")[0];

        String[] args = e.getMessage().getContent().replace(Main.prefix, "").replace(command, "").split(" ");

        System.out.println("Message: " + e.getMessage().getRawContent() + "\nCommand " + command);

        switch (command.toLowerCase()) {
            case "start": {
                playerDao.player(e.getAuthor().getName(), e.getAuthor().getDiscriminator());
                e.getChannel().sendMessage(String.format("%s You have been saved into database ! ", e.getAuthor())).complete();
                break;
            }
            case "getall": {
                String text = "";
                text += String.format("%s %15s %15s\n", "ID", "USERNAME", "DESC");

                for(Player p : playerDao.getAllPlayers()){
                    text += String.format("%d %15s %15s\n",p.getId(),p.getUsername(),p.getDesc());
                }
                e.getChannel().sendMessage(text).complete();
                break;
            }
            case "createhero": {
                String HeroUsername = args[1];
                System.out.println(HeroUsername);
                Hero hero = new Hero(HeroUsername);
                Player p = new Player(e.getAuthor().getName(),e.getAuthor().getDiscriminator());
                playerDao.addHero(playerDao.findOne(p), hero);
                e.getChannel().sendMessage(String.format("Hero %s started his journey ! ", HeroUsername)).complete();
                break;
            }
            case "getalldetails": {
                String text= new String();
                String header = new String("```js\nID\tUsername\tDESC\tHeroUsername\tHeroLevel\n");
                text += header;
                for(Player p : playerDao.getAllPlayers()){
                    text += p.getId() + "\t" + p.getUsername() + "\t" + p.getDesc() +"\t" + p.getHero().getHeroUsername() + "\t" + p.getHero().getLevel() + "\n";
                }
                e.getChannel().sendMessage(text + "```").complete();

                System.out.println();
                break;
            }
            case "grochu":{
                String text = "";
                for(int i =1 ; i <= args.length -1 ; i++){
                    text+= args[i] + " ";
                }
                e.getChannel().sendMessage(text + "jebać Rakso").complete();
                break;
            }
            case "ban":{
                //e.getChannel().sendMessage(String.valueOf(e.getMessage().getMentionedUsers().isEmpty())).complete();
                if(!e.getMessage().getMentionedUsers().isEmpty()) {
                    User u = e.getMessage().getMentionedUsers().get(0);
                    boolean check = false;
                    for(Role r : e.getGuild().getMember(u).getRoles()){
                        if(r.getName().equalsIgnoreCase("Programist"))
                            check = true;
                        else
                            check = false;
                    }
                    if(check == true){
                        e.getChannel().sendMessage("I'm untouchable").complete();
                    }else{
                     //   e.getGuild().getController().ban(u.getId(), 0).complete();
                        e.getChannel().sendMessage("BAN").complete();
                    }
                }else{
                    e.getChannel().sendMessage("You have to mention someone in the first place").complete();
                }
               // System.out.println(e.getGuild().getMembers().get(1));
               // e.getChannel().sendMessage()
              //
                break;
            }
            default : {
                System.out.println("wrong command");
                e.getChannel().sendMessage(String.format("Wrong command, %s !\n Type !help to get more information", e.getAuthor())).complete();
                break;
            }
        }
    }
}
