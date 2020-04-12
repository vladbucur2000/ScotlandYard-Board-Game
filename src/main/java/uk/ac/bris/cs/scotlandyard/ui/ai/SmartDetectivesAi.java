package uk.ac.bris.cs.scotlandyard.ui.ai;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Nonnull;

import uk.ac.bris.cs.scotlandyard.model.Ai;
import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;

public class SmartDetectivesAi implements Ai {

    @Nonnull @Override public String name() {
        return "smartDetectives";
    }

    @Nonnull @Override public Move pickMove(
            @Nonnull Board board,
            @Nonnull AtomicBoolean terminate) {
        // returns a random move, replace with your own implementation
        var moves = board.getAvailableMoves().asList();
        return moves.get(new Random().nextInt(moves.size()));
    }
}