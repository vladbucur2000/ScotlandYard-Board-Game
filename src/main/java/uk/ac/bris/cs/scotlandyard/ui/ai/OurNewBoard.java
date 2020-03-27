package uk.ac.bris.cs.scotlandyard.ui.ai;

import com.google.common.collect.ImmutableSet;

import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.*;

import java.util.*;

public class OurNewBoard {

    public List <PlayerInfo> players;
    PlayerInfo mrX;
    ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph;
    private GameSetup setup;

    OurNewBoard(List <PlayerInfo> players, GameSetup setup) {
        this.players = players;
        this.mrX = players.iterator().next();
        this.graph = setup.graph;
        this.setup = setup;
    }

    public Set<Move> getAvailableMoves() {
        Set<Move> set = new HashSet<>();

        set.addAll(makeSingleMoves(setup, mrX, players));
        if (mrX.hasTicket(ScotlandYard.Ticket.DOUBLE)) set.addAll(makeDoubleMove(setup, mrX, players)); //bugs

        return set;
    }



    private static Set<Move.SingleMove> makeSingleMoves(GameSetup setup, PlayerInfo mrX, List <PlayerInfo> players) {
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

    private static Set<Move.DoubleMove> makeDoubleMove (GameSetup setup, PlayerInfo mrX, List <PlayerInfo> players)  {

        Set<Move.SingleMove> a = makeSingleMoves(setup, mrX, players); //a set with the possible first moves
        Set<Move.DoubleMove> doubleMoves = new HashSet<>(); //a set with all the possible double moves

        for (Move.SingleMove mov : a) {
            //all the possible second moves using the initial first moves
            Set<Move.SingleMove> b = makeSingleMoves(setup, mrX, players);


            for (Move.SingleMove mov2 : b) {
                if (mov2.ticket == mov.ticket && !mrX.hasAtLeast2(mov.ticket)) continue;
                Move.DoubleMove ticket = new Move.DoubleMove(mrX.getPiece(), mrX.getLocation(), mov.ticket, mov.destination, mov2.ticket, mov2.destination);
                doubleMoves.add(ticket);
            }

        }

        return doubleMoves;
    }
}