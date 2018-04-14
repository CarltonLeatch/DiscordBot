package main.java.Listener;

import main.java.Application.Main;

import main.java.Model.Arguments;
import main.java.Model.Vote;
import main.java.Repository.VoteRepository;
import net.dv8tion.jda.core.entities.*;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.*;


public class MessageReceivedListener extends ListenerAdapter {

    VoteRepository db = new VoteRepository();
    int id = 0;

    @SuppressWarnings("unused")
    public void onMessageReceived(MessageReceivedEvent e) {

        if (!e.getMessage().getContent().startsWith(Main.prefix)) return;

        if (e.getChannelType() == ChannelType.PRIVATE) {
            System.out.println(String.format("[PRIVATE MSG] %s#%s: %s", e.getAuthor().getName(), e.getAuthor().getDiscriminator(), e.getMessage().getRawContent()));
        } else {
            System.out.println(String.format("[PUBLIC MSG] [%s][%s] %s#%s: %s", e.getGuild().getName(), e.getChannel().getName(), e.getAuthor().getName(), e.getAuthor().getDiscriminator(), e.getMessage().getRawContent()));
        }

        String command = e.getMessage().getStrippedContent().replace(Main.prefix, "").split(" ")[0];


        System.out.println("Message: " + e.getMessage().getRawContent() + "\nCommand " + command);


        switch (command) {
            case "title": {
                String[] args = e.getMessage().getContent().replace(Main.prefix, "").replace(command, "").split(" ");
                Vote vote = new Vote();
                String title = "";
                for (String s : args)
                    title += s + " ";
                vote.setTitle(title);
                List<String> AccoundIdList = new ArrayList<String>();
                vote.setAccountId(AccoundIdList);
                e.getChannel().sendMessage("Tytuł vote'a został ustawiony").complete();
                db.save(vote);

                System.out.println(vote.getTitle() + "\n" + vote.getId());
                break;
            }
            case "argument": {
                String[] args = e.getMessage().getContent().replace(Main.prefix, "").replace(command, "").split("/");
                Vote vote = db.get(id);


                if (vote == null)
                    e.getChannel().sendMessage("Najpierw musisz dodać tytuł").complete();
                else if (args.length == 0)
                    e.getChannel().sendMessage("Musisz wpisać argumenty !").complete();
                else {
                //    e.getChannel().sendMessage("@everyone").complete();
                    List<Arguments> argumentsList = new ArrayList<Arguments>();
                    int count = 0;
                    int argId = 0;
                    for (String s : args) {
                        Arguments a = new Arguments();
                        a.setArgument(s);
                        a.setCount(count);
                        a.setId(argId++);
                        argumentsList.add(a);
                        vote.setArguments(argumentsList);
                    }
                    String message = "```markdown\n" + vote.getTitle() +"\n";
                    for (Arguments a : vote.getArguments())
                       message += "[" + a.getId() + "] " + a.getArgument() + " <---- by zagłosować wpisz !vote " + a.getId() + "\n";
                    db.update(vote, id);
                    e.getChannel().sendMessage(message +"Można zacząć głosowanie ! ```").complete();
                    System.out.println(id);
                }
            }
            break;


        case "vote": {
            String[] args = e.getMessage().getContent().replace(Main.prefix, "").replace(command, "").split(" ");
            Vote vote = db.get(id);
            System.out.println(vote + " " + e.getMessage().getAuthor().getId());
            if (vote == null)
                e.getChannel().sendMessage("Najpierw musisz dodać tytuł").complete();
            else if (args.length == 0 )
                e.getChannel().sendMessage("Musisz wpisać numer argumentu !").complete();

             if (checker(vote, e.getMessage().getAuthor().getId()))
                e.getChannel().sendMessage(e.getAuthor().getAsMention() + " nie możesz zagłosować drugi raz :)").complete();
            else if ( Integer.parseInt(args[1]) > vote.getArguments().size())
            e.getChannel().sendMessage("Zły argument").complete();
            else{
                List<String> AccoundIdList = new ArrayList<String>();
                AccoundIdList = vote.getAccountId();
                AccoundIdList.add(e.getMessage().getAuthor().getId());
                vote.setAccountId(AccoundIdList);
                System.out.println(args[1]);
                Arguments a = vote.getArguments().get(Integer.parseInt(args[1]));
                int count = a.getCount();
                count++;
                a.setCount(count);
                vote.getArguments().remove(Integer.parseInt(args[1]));
                vote.getArguments().add(a);
                db.update(vote, id);
                e.getChannel().sendMessage(e.getAuthor().getAsMention() + " dziękujemy za oddanie głosu").complete();
            }
            break;
        }
        case "result": {
            System.out.println(id);
            Vote v = db.get(id);
            List<Arguments> list = v.getArguments();
            Collections.sort(list, Comparator.comparingInt(Arguments::getId));


                String result = "```markdown\nGłosowanie nr :" + v.getId() +"\nTytuł głosowania: " + v.getTitle() + "\nIlość argumentów: " + v.getArguments().size()+"\n";
            for(Arguments a : v.getArguments()) {
                result += "[" + a.getId() + "] " +a.getArgument() + "\t" + a.getCount() + "\t" + (a.getCount()/v.getAccountId().size() * 100 ) + "%\n";
            }
            e.getChannel().sendMessage(result + "```").complete();
            id++;
            break;
        }


            case "help" : {
                e.getChannel().sendMessage("```markdown\nAby zacząć votowanie należy wpisać komendę\n !title [Pytanie]\n" +
                        "Dodawanie argumentów do votowania i start:\n !argument [arg1]/[arg2]...\n by uzyskać wyniki należy wpisać\n!result ```").complete();
                break;
            }
        default: {
            e.getChannel().sendMessage("```markdown\nZła komenda\nWpisz !help by otrzymać pomoc```").complete();
        }
    }


}


    private boolean checker(Vote v, String AccountId) {
        if (v.getAccountId().size() == 0 || v.getAccountId() == null)
            return false;

        for (String id : v.getAccountId())
            if (id.equals(AccountId))
                return true;

        return false;
    }
}
