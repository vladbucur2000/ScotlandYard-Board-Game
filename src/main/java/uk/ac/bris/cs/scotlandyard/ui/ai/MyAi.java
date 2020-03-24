package uk.ac.bris.cs.scotlandyard.ui.ai;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nonnull;
import javax.swing.*;

import uk.ac.bris.cs.scotlandyard.model.*;

public class MyAi implements Ai {

	@Nonnull @Override public String name() { return "theCrane"; }

	@Nonnull @Override public Move pickMove(
			@Nonnull Board board,
			@Nonnull AtomicBoolean terminate) {

		//??trebuie sa facem o lista cu tichetele fiecarui jucator
		//board.getPlayerTickets(CULOARE);

		int cnt = 0;
		PlayerInfo mrX = null;
		var moves = board.getAvailableMoves().asList();
		int initialLocation = moves.iterator().next().source();

		List<PlayerInfo> detectives = new ArrayList<>();
		int c=0;
		//System.out.println(board.getDetectiveLocation(Piece.Detective.RED).get());
		for (Piece player : board.getPlayers()) {
			c++;
			if (player.isMrX()) {
				mrX = new PlayerInfo(board.getPlayerTickets(player).get(), player, initialLocation);
				continue;
			}
			switch (player.webColour()) {
				case("#f00"):
					detectives.add(new PlayerInfo(board.getPlayerTickets(player).get(), player, board.getDetectiveLocation(Piece.Detective.RED).get())); break;
				case ("#0f0"):
					detectives.add(new PlayerInfo(board.getPlayerTickets(player).get(), player, board.getDetectiveLocation(Piece.Detective.GREEN).get())); break;
				case ("#00f"):
					detectives.add(new PlayerInfo(board.getPlayerTickets(player).get(), player, board.getDetectiveLocation(Piece.Detective.BLUE).get())); break;
				case ("#fff"):
					detectives.add(new PlayerInfo(board.getPlayerTickets(player).get(), player, board.getDetectiveLocation(Piece.Detective.WHITE).get())); break;
				case ("#ff0"):
					detectives.add(new PlayerInfo(board.getPlayerTickets(player).get(), player, board.getDetectiveLocation(Piece.Detective.YELLOW).get())); break;
			}
		}


		for(var i : detectives){
			System.out.println ( i.getPiece() + "..echivaltena: " + i.getEquivalenceTAXI() +"..locatie:" + i.getLocation());
		}

		System.out.println("--------------------------------------------- HATZZZZZ MOBILAAAA------");
		try {
			TimeUnit.SECONDS.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}


		//ScoreFunction scoreFunction = new ScoreFunction();
		//scoreFunction.scorer();
		// returns a random move, replace with your own implementation

		return moves.get(new Random().nextInt(moves.size()));
	}

}
