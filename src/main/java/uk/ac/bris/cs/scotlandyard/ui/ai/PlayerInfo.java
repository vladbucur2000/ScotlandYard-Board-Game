package uk.ac.bris.cs.scotlandyard.ui.ai;

import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;
import java.util.*;


public class PlayerInfo {
    private Map<ScotlandYard.Ticket, Integer> tickets;
    private final Piece colour;
    private int location;

    public PlayerInfo (Map<ScotlandYard.Ticket, Integer> tickets,Piece colour, int location ) {
        this.tickets  = new HashMap<>(tickets);
        this.colour   = colour;
        this.location = location;
    }

    public Piece getPiece() {
        return colour;
    }
    public int getLocation() {
        return location;
    }
    public Map<ScotlandYard.Ticket, Integer> getTickets() {
        return tickets;
    }
    public Boolean hasTicket(ScotlandYard.Ticket ticket) {
        if( tickets.get(ticket) == 0 ) return false;
        return true;
    }
    public int  getEquivalenceTAXI(){
        for(var i : )
        return i;
    }

}
