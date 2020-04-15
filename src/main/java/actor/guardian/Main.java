package actor.guardian;

import actor.match.Match;
import actor.match.MatchProtocol;
import actor.match.MatchSupervisor;
import akka.actor.typed.ActorSystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final ActorSystem<MatchProtocol.Update> actorSystem = ActorSystem.create(MatchSupervisor.create(), "Guardian");

        String fileName = "match_list.csv";
        File file = new File(fileName);

        try {
            Scanner inputStream = new Scanner(file, String.valueOf(StandardCharsets.UTF_8));
            while (inputStream.hasNext()) {
                String data = inputStream.nextLine();
                String[] parsedValues = data.split(",");

                MatchProtocol.Incident incident = MatchProtocol.Incident.of(parsedValues[0], Integer.parseInt(parsedValues[1]), parsedValues[2], parsedValues[3], parsedValues[4]);
                actorSystem.tell(MatchProtocol.Update.of(incident));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}


/*
public  class MatchSupervisor extends AbstractBehavior<MatchSupervisor> {

private final ActorRef<Match> startMatch;
private String name;
    public MatchSupervisor(ActorContext<MatchSupervisor> context) {
        super(context);
        this.startMatch = getContext().spawn(Match.create(),"child");
    }

    public static Behavior<MatchSupervisor> create() {

        return Behaviors.setup(MatchSupervisor::new);
    }

    @Override
    public Receive<MatchSupervisor> createReceive() {

return null;
    }
 */



/*public class Match extends AbstractBehavior<Match> {


    public Match(ActorContext<Match> context) {
        super(context);
    }

    public static Behavior<Match> create() {
        return Behaviors.setup(Match::new);
    }

    @Override

    public Receive<Match> createReceive() {
        return null;
    }
*/