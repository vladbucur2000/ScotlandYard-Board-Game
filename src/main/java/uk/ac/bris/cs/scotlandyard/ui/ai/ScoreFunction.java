package uk.ac.bris.cs.scotlandyard.ui.ai;

import com.google.common.collect.ImmutableSet;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.Graph;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.ui.ai.PlayerInfo;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;
import java.util.*;

public class ScoreFunction {

    public int scorer(ImmutableValueGraph < Integer , ImmutableSet<ScotlandYard.Transport>> graph , List<PlayerInfo> players, int location){
        private int stationScore = 0;
        private int detectiveScore = 0;
        private int score = 0;
        PlayerInfo mrX = players.get(0);

        /** 1 VARIABLE */

        for (EndpointPair<Integer> incidentEdge : graph.incidentEdges(location)) {

            for(var i : graph.edgeValueOrDefault(incidentEdge,null)) {
                if (mrX.hasTicket(i.requiredTicket())) {
                    switch (i) {
                        case TAXI: stationScore += 1;
                        case BUS:  stationScore += 4;
                        case UNDERGROUND: stationScore += 18;
                        case FERRY: stationScore += 115;
                        default: throw new NullPointerException("Ticket is null");
                    }
                }
            }
        }

        /** 2 VARIABLE */

        OurDijkstra dijkstra = new OurDijkstra();
        int[] distances = dijkstra.compute(graph,location);

        /** ECHIVALARE


    }
}
