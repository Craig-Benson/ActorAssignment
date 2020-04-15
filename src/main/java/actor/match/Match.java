package actor.match;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Match extends AbstractBehavior<MatchProtocol.Command> {

    private Map<Integer,MatchProtocol.Incident> incidentMap = new HashMap<>();

    public static Behavior<MatchProtocol.Command> create(String id, MatchProtocol.Incident incident) {
        return Behaviors.setup(context -> new Match(context,id, incident));
    }

    private final String id;

    private Match(ActorContext<MatchProtocol.Command> context, String id, MatchProtocol.Incident incident) {
        super(context);
        this.id = id;

        incidentMap.put(incident.getEventId(),incident);
    }

    @Override
    public Receive<MatchProtocol.Command> createReceive() {
        return newReceiveBuilder()
                .onMessage(MatchProtocol.Update.class, this::onUpdate).build();
    }

    private Behavior<MatchProtocol.Command> onUpdate(MatchProtocol.Update command) {

        getContext().getLog().info("Hello {}!{}", id, command.getIncident());
        int key = command.getIncident().getEventId();

        if(incidentMap.containsKey(key))
        {
           incidentMap.replace(key,command.getIncident());
        }else{
            System.out.println("out");
            incidentMap.put(key,command.getIncident());
        }

        return this;
    }

}