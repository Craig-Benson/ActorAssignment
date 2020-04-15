package actor.match;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

import java.util.HashMap;
import java.util.Map;

public  class MatchSupervisor extends AbstractBehavior<MatchProtocol.Update> {

    Map<String, ActorRef<MatchProtocol.Command>> matches;

    public static Behavior<MatchProtocol.Update> create() {
        return Behaviors.setup(MatchSupervisor::new);
    }

    public MatchSupervisor(ActorContext<MatchProtocol.Update> context) {
        super(context);
        matches = new HashMap<>();
    }

    @Override
    public Receive<MatchProtocol.Update> createReceive() {
        return newReceiveBuilder().onMessage(MatchProtocol.Update.class, this::updateOrCreateMatch).build();
    }

    private Behavior<MatchProtocol.Update> updateOrCreateMatch(MatchProtocol.Update update) {
        String matchId = update.getIncident().getMatchId();
        int eventId = update.getIncident().getEventId();
        MatchProtocol.Incident incident = update.getIncident();

        if (!matches.containsKey(matchId)) {
            matches.put(matchId, getContext().spawn(Match.create(matchId,incident), matchId));
            return this;
        }
        ActorRef<MatchProtocol.Command> matchActor = matches.get(matchId);
        matchActor.tell(MatchProtocol.Update.of(update.getIncident()));

        matches.get(matchId).tell(update);
        return this;
    }
}




