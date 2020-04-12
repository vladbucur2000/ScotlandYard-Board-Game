package uk.ac.bris.cs.scotlandyard.ui.ai;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;
import java.util.*;


public class PlayerInfo {
    private Map<ScotlandYard.Ticket, Integer> tickets;
    private Board.TicketBoard ticketBoards;
    private final Piece colour;
    private int location;

    public PlayerInfo (Board.TicketBoard tickets, Piece colour, int location ) {
        this.ticketBoards = tickets;
        Map<ScotlandYard.Ticket, Integer> hm = new HashMap<ScotlandYard.Ticket, Integer>();
        hm.put(ScotlandYard.Ticket.TAXI , tickets.getCount(ScotlandYard.Ticket.TAXI));
        hm.put(ScotlandYard.Ticket.BUS , tickets.getCount(ScotlandYard.Ticket.BUS));
        hm.put(ScotlandYard.Ticket.UNDERGROUND , tickets.getCount(ScotlandYard.Ticket.UNDERGROUND));
        hm.put(ScotlandYard.Ticket.SECRET , tickets.getCount(ScotlandYard.Ticket.SECRET));
        hm.put(ScotlandYard.Ticket.DOUBLE , tickets.getCount(ScotlandYard.Ticket.DOUBLE));
        this.tickets = hm;
        this.colour = colour;
        this.location = location;
    }

    public Piece getPiece() {
        return colour;
    }

    public Board.TicketBoard giveTicketBoard(){
        return ticketBoards;
    }

    public int getLocation() {
        return location;
    }

    public void changeLocation(int location) {
        this.location = location;
    }
    public Map<ScotlandYard.Ticket, Integer> getTickets() {
        return tickets;
    }
    public void modifyTickets(Iterable<ScotlandYard.Ticket> t, int x){
        for (ScotlandYard.Ticket ticket : t) {
            int number = tickets.remove(ticket);
            tickets.put(ticket, number + x);
        }
    }

    public Boolean hasTicket(ScotlandYard.Ticket ticket) {
        if( tickets.get(ticket) == 0 ) return false;
        return true;
    }

    public boolean hasAtLeast2 (ScotlandYard.Ticket ticket) {
        if (tickets.get(ticket) >= 2) return true;
        return false;
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
