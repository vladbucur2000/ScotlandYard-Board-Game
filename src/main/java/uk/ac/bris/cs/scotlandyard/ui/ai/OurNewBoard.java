package uk.ac.bris.cs.scotlandyard.ui.ai;

import com.google.common.collect.ImmutableSet;

import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.*;

import javax.annotation.Nonnull;
import java.util.*;

public class OurNewBoard {

    private List <PlayerInfo> players;
    PlayerInfo mrX;
    ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph;
    private GameSetup setup;

    OurNewBoard(List <PlayerInfo> players, GameSetup setup) {
        this.players = players;
        this.mrX = players.iterator().next();
        this.graph = setup.graph;
        this.setup = setup;
    }

    Set<Move> getAvailableMoves() {
        Set<Move> set = new HashSet<>();

        set.addAll(makeSingleMoves(setup, mrX, players));
        return set;
       // if (mrX.hasTicket(ScotlandYard.Ticket.DOUBLE)) set.addAll(makeDoubleMove()); //bugs
    }



    static Set<Move.SingleMove> makeSingleMoves(GameSetup setup, PlayerInfo mrX, List <PlayerInfo> players) {
        Set<Move.SingleMove> singleMoves = new HashSet<>(); //a set with all possible single moves

        for (int destination : setup.graph.adjacentNodes(mrX.getLocation())) {
            var occupied = false;

            //find out if destination is occupied by a detective
            for (final var p : players) {
                if (p.getLocation() == destination && p != mrX) occupied = true;
            }

            if (occupied) continue; //if occupied skip

            //add moves if the player has the required ticket
            for (ScotlandYard.Transport t : setup.graph.edgeValueOrDefault(mrX.getLocation(), destination, ImmutableSet.of())) {
                if (mrX.hasTicket(t.requiredTicket()))
                    singleMoves.add(new Move.SingleMove(mrX.getPiece(), mrX.getLocation(), t.requiredTicket(), destination));
            }

            //add moves to the destination via a Secret ticket if there are any left with the player
            if (mrX.hasTicket(ScotlandYard.Ticket.SECRET))
                for (ScotlandYard.Transport t : setup.graph.edgeValueOrDefault(mrX.getLocation(), destination, ImmutableSet.of()))
                    singleMoves.add(new Move.SingleMove(mrX.getPiece(),mrX.getLocation(), ScotlandYard.Ticket.SECRET, destination));

        }
        return singleMoves;

    }
}