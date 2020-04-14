package uk.ac.bris.cs.scotlandyard.ui.ai;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Nonnull;

import uk.ac.bris.cs.scotlandyard.model.Ai;
import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;

import static uk.ac.bris.cs.scotlandyard.ui.ai.TheCraneAi.extractDestination;

public class SmartDetectivesAi implements Ai {

    @Nonnull @Override public String name() {
        return "SmartDetectivesAi";
    }

    static private OurDijkstra dijkstra = new OurDijkstra();
    private int mrxLocation = -1;
    private int[] distances;

    @Nonnull @Override public Move pickMove(
            @Nonnull Board board,
            @Nonnull AtomicBoolean terminate) {

        var moves = board.getAvailableMoves().asList();

        boolean reveal = board.getSetup().rounds.get((board.getMrXTravelLog().size() - 1));
        if (reveal) { // I need dijkstra only when mrX reveals his location
            mrxLocation = board.getMrXTravelLog().get(board.getMrXTravelLog().size() - 1).location().get();
            distances = dijkstra.compute(board.getSetup().graph, mrxLocation);
        }

        Move bestMove = null;
        int bestMoveScore = 0;

        if (mrxLocation == -1) {

            /** mrX's location is unknown */
            // get to the node with the biggest number of stations
            // each transportation method weights different (because the travel speed is different)
            for (Move move: moves) {
                ScotlandYard.Ticket transport = move.tickets().iterator().next();
                int destination = extractDestination(move);

                int[] nodes = new int[255]; //store nodeScores
                Arrays.setAll(nodes, p -> 0);

                switch (transport) {
                    case TAXI: nodes[destination] += 1; break;
                    case BUS: nodes[destination] += 4; break;
                    case UNDERGROUND: nodes[destination] += 18; break;
                    case SECRET: nodes[destination] += 115; break;
                }

               if (nodes[destination] > bestMoveScore) {
                   bestMoveScore = nodes[destination];
                   bestMove = move;
               }

            }

            return bestMove;
        }

        /** mrX reveals his location */
        //dijkstra!
        int bestDistance = Integer.MAX_VALUE;

        for (Move move : moves) {
            int destination = extractDestination(move);

            if (distances[destination] < bestDistance) {
                bestDistance = distances[destination];
                bestMove = move;
            }
        }

        return bestMove;
    }
}
