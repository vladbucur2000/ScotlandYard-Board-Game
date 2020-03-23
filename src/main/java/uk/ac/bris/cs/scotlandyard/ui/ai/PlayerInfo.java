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
    public int getEquivalenceTAXI(){
        int score = 0;
        for(Map.Entry<ScotlandYard.Ticket, Integer> element : tickets.entrySet()) {
            ScotlandYard.Ticket ticket = element.getKey();
            Integer noTickets = element.getValue();

            switch (ticket) {
                case TAXI: score += noTickets;
                case BUS: score += (4 * noTickets);
                case UNDERGROUND: score += (18 * noTickets);
                default : break;
            }
        }

        return score;
    }

    public int totalTickets() {
        int ans = 0;
        for (Map.Entry<ScotlandYard.Ticket, Integer> element : tickets.entrySet()) {
            Integer noTickets = element.getValue();
            ans += noTickets;
        }

        return ans;
    }

}
