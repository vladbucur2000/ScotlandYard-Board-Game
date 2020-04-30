package uk.ac.bris.cs.scotlandyard.ui.ai;

import com.google.common.collect.ImmutableSet;
import com.google.common.graph.EndpointPair;

import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;
import java.util.*;

public class ScoreFunction {

    public int scorer(ImmutableValueGraph < Integer , ImmutableSet<ScotlandYard.Transport>> graph, List<PlayerInfo> players, int location, int previousDestination) {

        int stationScore = 0, detectiveScore = 0, score = 0, danger = 0;
        PlayerInfo mrX = players.get(0); //mrX is the first player in the list

        /** 1st VARIABLE */
        //how far are the detectives ?
        //how many steps should they move per turn to catch me?
        //do they have enough tickets? are they too close?
        OurDijkstra dijkstra = new OurDijkstra();
        int[] distances = dijkstra.compute(graph, location);

        for (PlayerInfo player : players) {
            if (player == mrX) continue;

            int movementRatio = 1000;

            if (distances[player.getLocation()] == 0) return -9999999;

            if (distances[player.getLocation()] == 1) danger += -50000; //too close

            if (distances[player.getLocation()] < player.getEquivalenceTAXI())
                movementRatio = distances[player.getLocation()] / player.totalTickets();

            detectiveScore += movementRatio;
        }


        /** 2nd VARIABLE */
        //To not be caught, I need to choose the node which has more stations
        for (EndpointPair<Integer> incidentEdge : graph.incidentEdges(location))
            for (var i : graph.edgeValueOrDefault(incidentEdge, null))
                if (mrX.hasTicket(i.requiredTicket()) || mrX.hasTicket(ScotlandYard.Ticket.SECRET)) {
                    switch (i) {
                        case TAXI: stationScore += 1; break;
                        case BUS: stationScore += 4; break;
                        case UNDERGROUND: stationScore += 18; break;
                        case FERRY: stationScore += 115; break;
                    }
                }

        if (stationScore == 0) return -9999999;

        /** 3rd VARIABLE */
        //if I reveal my location the detectives will come after me so I rather not be close to my previous location
        int farAway = distances[previousDestination];

        /** FINAL SCORE */
        score = danger * 10000 + stationScore * 80 +  detectiveScore * 500 + farAway * 1000;

        return score;
    }
}
