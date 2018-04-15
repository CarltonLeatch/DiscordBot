package main.java.Listener;

import main.java.Application.Main;

import main.java.Model.Arguments;
import main.java.Model.Vote;
import main.java.Repository.VoteRepository;
import net.dv8tion.jda.core.entities.*;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import sun.invoke.empty.Empty;

import java.util.*;


public class MessageReceivedListener extends ListenerAdapter {

    VoteRepository db = new VoteRepository();

    @SuppressWarnings("unused")
    public void onMessageReceived(MessageReceivedEvent e) {

        if (!e.getMessage().getContent().startsWith(Main.prefix)) return;

        if (e.getChannelType() == ChannelType.PRIVATE) {
            e.getChannel().sendMessage("Visit me on Discord Server! ").complete();
            return;
        } else {
            System.out.println(String.format("[PUBLIC MSG] [%s][%s] %s#%s: %s", e.getGuild().getName(), e.getChannel().getName(), e.getAuthor().getName(), e.getAuthor().getDiscriminator(), e.getMessage().getRawContent()));
        }

        String command = e.getMessage().getStrippedContent().replace(Main.prefix, "").split(" ")[0];


        System.out.println("Message: " + e.getMessage().getRawContent() + "\nCommand " + command);


        switch (command.toLowerCase()) {
            case "title": {
                String[] args = e.getMessage().getContent().replace(Main.prefix, "").replace(command, "").split(" ");
                e.getChannel().sendMessage(createPoll(args)).complete();
                break;
            }
            case "arguments": {
                String[] args = e.getMessage().getContent().replace(Main.prefix, "").replace(command, "").split("/");
                e.getChannel().sendMessage(createArgument(args,e)).complete();
                break;
            }

            case "vote": {
                String[] args = e.getMessage().getContent().replace(Main.prefix, "").replace(command, "").split(" ");
                e.getChannel().sendMessage(voting(args, e.getMessage().getAuthor().getId(), e)).complete();
                break;
            }

            case "result": {

                String[] args = e.getMessage().getContent().replace(Main.prefix, "").replace(command, "").split(" ");
                if (args.length <= 1 && args[0].equals("")) {
                    e.getChannel().sendMessage(resultCommand(db.getDb().size()-1,e)).complete();
                } else
                    e.getChannel().sendMessage(resultCommand(Integer.parseInt(args[1]) - 1,e)).complete();
                break;
            }

            case "listallpolls": {
                e.getChannel().sendMessage(listAllPolls()).complete();
                break;
            }

            case "help": {
                e.getChannel().sendMessage("```markdown\nAby zacząć votowanie należy wpisać komendę\n!title [Pytanie]\n" +
                        "Dodawanie argumentów do votowania i start:\n!arguments [arg1]/[arg2]...\nby uzyskać wyniki należy wpisać\n!result ```").complete();
                break;
            }
            case "listid":{
                e.getChannel().sendMessage(String.valueOf(db.getDb().size()-1)).complete();
                break;
            }
            default: {
                e.getChannel().sendMessage("```markdown\nZła komenda\nWpisz !help by otrzymać pomoc```").complete();
            }
        }


    }


    private String voting(String[] args, String author, MessageReceivedEvent e) {
        Vote vote = db.get(db.getDb().size()-1);
        String result = "";
        if (vote == null)
            result += "Najpierw musisz dodać tytuł";
        else if (args.length == 0)
            result += "Musisz wpisać numer argumentu !";

        if (checker(vote, author))
            result += e.getAuthor().getAsMention() + " nie możesz zagłosować drugi raz :)";
        else if (Integer.parseInt(args[1]) > vote.getArguments().size()-1)
            result += "Zły argument";
        else {
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
            db.update(vote, db.getDb().size()-1);
            result += e.getAuthor().getAsMention() + " dziękujemy za oddanie głosu";
        }
        return result;
    }

    private String resultCommand(int id, MessageReceivedEvent e) {
        String result = "";
        if(db.getDb().size() == 0)
            return "```xl\n Obecnie nie ma aktywnej żadnej ankiety.```";
        Vote v = db.get(id);

        if(v.getAccountId().size() == 0)
            return "```markdown\nKtoś musi najpierw zagłosować !```";
        List<Arguments> list = v.getArguments();
        Collections.sort(list, Comparator.comparingInt(Arguments::getId));
       // e.getChannel().sendMessage("@everyone").complete();
         result = "```xl\nGłosowanie nr :" + v.getId() + "\nTytuł głosowania:" + v.getTitle() + "\nIlość argumentów: " + v.getArguments().size() + "\n";
        result += "Zagłosowało: " + v.getAccountId().size() + " osób\n";
        result += String.format("%1s%22s%11s%10s\n","ID","NAME","COUNT","PERCENT");
        for (Arguments a : v.getArguments()) {
            double percent = ((a.getCount() / v.getAccountId().size()) * 100);
            result += String.format("[%d]%20s%10d%12.2f %s\n",a.getId(), a.getArgument(), a.getCount(), percent,"%");
        }
        result += "```";

        return result;
    }

    private boolean checker(Vote v, String AccountId) {
        if (v.getAccountId().size() == 0 || v.getAccountId() == null)
            return false;

        for (String id : v.getAccountId())
            if (id.equals(AccountId))
                return true;

        return false;
    }

    private String listAllPolls() {
        String result;
        if (db.getDb().size() == 0) {
            result = "```xl\nObecnie nie ma żadnej ankiety```";
        } else {
            result = "```xl\nWszystkie ankiety:\nID\tTytuł\n";
            for (Vote v : db.getDb()) {
                result += "[" + v.getId() + "] " + v.getTitle() + "\n";
            }
            result += "```";
        }
        return result;
    }

    private String createPoll(String[] args) {
        Vote vote = new Vote();
        String title = "";

        args[0] = args[0].substring(0);
        for (String s : args)
            title += s + " ";
        vote.setTitle(title);
        List<String> AccoundIdList = new ArrayList<String>();
        vote.setAccountId(AccoundIdList);
        db.save(vote);
        return "```markdown\nPytanie zostało utworzone!\nTeraz dodaj argumenty poleceniem !argument\nNazwa polla:" + vote.getTitle() + "```";
    }

    private String createArgument(String[] args, MessageReceivedEvent e) {
        Vote vote = db.get(db.getDb().size()-1);
        args[0] = args[0].substring(1);
        String result = "```markdown\n";
        if (vote == null)
            result += "Najpierw musisz dodać tytuł";
        else if (args.length == 0)
            result += "Musisz wpisać argumenty !";
        else {
              //  e.getChannel().sendMessage("@everyone").complete();
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
            String message = vote.getTitle() + "\n";
            for (Arguments a : vote.getArguments())
                message += "[" + a.getId() + "] " + a.getArgument() + " <-- by zagłosować wpisz !vote " + a.getId() + "\n";
            db.update(vote, db.getDb().size()-1);
            result += message + "Można zacząć głosowanie !!```";
            System.out.println(db.getDb().size()-1);
        }
        return result;

    }
}

