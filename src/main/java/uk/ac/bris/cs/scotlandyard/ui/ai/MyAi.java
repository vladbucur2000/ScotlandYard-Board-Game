package uk.ac.bris.cs.scotlandyard.ui.ai;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Nonnull;

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


		for (Piece player : board.getPlayers()) {
			if (player.isMrX()) {
				mrX = new PlayerInfo(board.getPlayerTickets(Piece.MrX), Piece.MrX, initialLocation);
				mrX.
				continue;
			}

			switch (player) {
				case player.webColour().equals("#f00"):
					detectives.add(new PlayerInfo(board.getPlayerTickets(player), player, board.getDetectiveLocation(Piece.Detective.RED)));
				case player.webColour().equals("#0f0"):
					detectives.add(new PlayerInfo(board.getPlayerTickets(player), player, board.getDetectiveLocation(Piece.Detective.GREEN)));
				case player.webColour().equals("#00f"):
					detectives.add(new PlayerInfo(board.getPlayerTickets(player), player, board.getDetectiveLocation(Piece.Detective.BLUE)));
				case player.webColour().equals("#fff"):
					detectives.add(new PlayerInfo(board.getPlayerTickets(player), player, board.getDetectiveLocation(Piece.Detective.WHITE)));
				case player.webColour().equals("#ff0"):
					detectives.add(new PlayerInfo(board.getPlayerTickets(player), player, board.getDetectiveLocation(Piece.Detective.YELLOW)));
			}
		}

		for ( var x : detectives)
			System.out.println(x.getLocation());



		//ScoreFunction scoreFunction = new ScoreFunction();
		//scoreFunction.scorer();
		// returns a random move, replace with your own implementation

		return moves.get(new Random().nextInt(moves.size()));
	}

}
