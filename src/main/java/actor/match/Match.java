package actor.match;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public class Match extends AbstractBehavior<MatchProtocol.Command> {

    private List<MatchProtocol.Incident> incidentList = new ArrayList<>();



    public static Behavior<MatchProtocol.Command> create(String id, MatchProtocol.Incident incident) {
        return Behaviors.setup(context -> new Match(context, id,incident));
    }

    private final String id;
    private final MatchProtocol.Incident incident;

    private Match(ActorContext<MatchProtocol.Command> context, String id, MatchProtocol.Incident incident) {
        super(context);
        this.id = id;
        this.incident = incident;

    }

    @Override
    public Receive<MatchProtocol.Command> createReceive() {
        return newReceiveBuilder()
                .onMessage(MatchProtocol.Update.class, this::onUpdate).build();
    }

    private Behavior<MatchProtocol.Command> onUpdate(MatchProtocol.Update command) {

        getContext().getLog().info("Hello {}!{}", id, command.getIncident());

//        for (MatchProtocol.Incident incident: incidentList) {
        if(incidentList.contains(command.getIncident()))
        {
            System.out.println("in");
        }else{
            System.out.println("out");
            incidentList.add(command.getIncident());
        }

//            if(command.getIncident().getEventId() == incident.getEventId()){
//
//                incidentList.remove(incident);
//                incidentList.add(incidentList.indexOf(incident),command.getIncident());
//            }else{
//                incidentList.add(command.getIncident());
//            }


//        }



        return this;
    }

}