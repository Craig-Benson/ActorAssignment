package actor.match;

import akka.actor.typed.ActorRef;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

public interface MatchProtocol {
    interface Command{}
    interface MatchData{}

    @AllArgsConstructor(staticName = "of")
    @Getter
    @EqualsAndHashCode
    @ToString
    class Update implements Command {
        private Incident incident;
    }

    @AllArgsConstructor (staticName = "of")
    @Getter
    @EqualsAndHashCode
    @ToString
    class Incident implements MatchProtocol.MatchData {
        private String matchId;
        private int eventId;
        private String participantRef;
        private String timestamp;
        private String eventType;
    }
}
