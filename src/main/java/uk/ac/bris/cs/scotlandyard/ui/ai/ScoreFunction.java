package uk.ac.bris.cs.scotlandyard.ui.ai;

import com.google.common.collect.ImmutableSet;
import com.google.common.graph.EndpointPair;

import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;
import java.util.*;

public class ScoreFunction {

    public int scorer(ImmutableValueGraph < Integer , ImmutableSet<ScotlandYard.Transport>> graph, List<PlayerInfo> players, int location) {
        int stationScore = 0;
        int detectiveScore = 0;
        int score = 0;
        PlayerInfo mrX = players.get(0);

        /** 1st VARIABLE */
        OurDijkstra dijkstra = new OurDijkstra();
        int[] distances = dijkstra.compute(graph, location);

        for (PlayerInfo player : players) {
            if (player == mrX) continue;

            int movementRatio = 1000;
            if (distances[player.getLocation()] < player.getEquivalenceTAXI()) movementRatio = distances[player.getLocation()] / player.totalTickets();
            detectiveScore += movementRatio;
        }


        /** 2nd VARIABLE */

        for (EndpointPair<Integer> incidentEdge : graph.incidentEdges(location))
            for (var i : graph.edgeValueOrDefault(incidentEdge, null))
                if (mrX.hasTicket(i.requiredTicket()) || mrX.hasTicket(ScotlandYard.Ticket.SECRET)) switch (i) {
                    case TAXI:
                        stationScore += 1;
                    case BUS:
                        stationScore += 4;
                    case UNDERGROUND:
                        stationScore += 18;
                    case FERRY:
                        stationScore += 115;
                    default:
                        throw new NullPointerException("Ticket is null");
                }


        ////////////////////////////////////////////
        score = stationScore + detectiveScore / (players.size() - 1);
///////////////////////////////////////////////////////

        return score;

    }
}
