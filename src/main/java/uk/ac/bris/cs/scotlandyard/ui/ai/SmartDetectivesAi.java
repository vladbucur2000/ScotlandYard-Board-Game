package uk.ac.bris.cs.scotlandyard.ui.ai;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Nonnull;

import uk.ac.bris.cs.scotlandyard.model.Ai;
import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;

import static uk.ac.bris.cs.scotlandyard.ui.ai.TheCraneAi.extractDestination;

public class SmartDetectivesAi implements Ai {

    @Nonnull @Override public String name() {
        return "SmartDetectivesAi";
    }

    OurDijkstra dijkstra = new OurDijkstra();
    int mrxLocation = -1;
    int[] distances;

    @Nonnull @Override public Move pickMove(
            @Nonnull Board board,
            @Nonnull AtomicBoolean terminate) {
        // returns a random move, replace with your own implementation
        var moves = board.getAvailableMoves().asList();


        boolean reveal = board.getSetup().rounds.get((board.getMrXTravelLog().size() - 1));
        if (reveal) {
            mrxLocation = board.getMrXTravelLog().get(board.getMrXTravelLog().size() - 1).location().get();
            distances = dijkstra.compute(board.getSetup().graph, mrxLocation);
        }

        if (mrxLocation == -1) {
            return moves.get(new Random().nextInt(moves.size()));
        }

        Move bestMove = null;
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
