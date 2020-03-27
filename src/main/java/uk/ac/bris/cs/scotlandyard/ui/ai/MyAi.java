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

import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.*;

public class MyAi implements Ai {
	public static ScoreFunction sc = new ScoreFunction();
	public static Move.SingleMove bestMoveuleanu = null;
	public static int currentScore = 0;
	@Nonnull @Override public String name() { return "theCrane"; }

	@Nonnull @Override public Move pickMove(
			@Nonnull Board board,
			@Nonnull AtomicBoolean terminate) {

		PlayerInfo mrX = null;
		var moves = board.getAvailableMoves().asList();
		int initialLocationMRX = moves.iterator().next().source();

		List<PlayerInfo> detectives = new ArrayList<>();
		for (Piece player : board.getPlayers()) {

			if (player.isMrX()) {
				mrX = new PlayerInfo(board.getPlayerTickets(player).get(), player, initialLocationMRX);
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

	    List<PlayerInfo> init = new ArrayList<>();
		init.add(mrX);
		init.addAll(detectives);
		OurNewBoard newBoard = new OurNewBoard(init,board.getSetup());
		Move bestMove = null;
		int bestMoveScore = minimaxAlphaBeta(0,true,newBoard,mrX,detectives,board,Integer.MIN_VALUE,Integer.MAX_VALUE);
		//ScoreFunction scoreFunction = new ScoreFunction();
		//scoreFunction.scorer();
		// returns a random move, replace with your own implementation
		System.out.println("CEL MAI TARE DIN PARCARE : " +bestMoveScore);
		return bestMoveuleanu;
		//return moves.get(new Random().nextInt(moves.size()));
	}



	static int minimaxAlphaBeta(int depth, boolean maximize, OurNewBoard ourBoard, PlayerInfo mrX, List<PlayerInfo> detectives, Board board,int alpha, int beta) {

		if (depth == 5){
			int scorulina = sc.scorer(board.getSetup().graph,ourBoard.players,mrX.getLocation());
			System.out.println(scorulina);
			return scorulina;
		}

		if(maximize){
			int v = Integer.MIN_VALUE;
			for (Move.SingleMove nextMove : ourBoard.getAvailableMoves()){
				PlayerInfo newMrX = new PlayerInfo(mrX.giveTicketBoard(),mrX.getPiece(),mrX.getLocation());

				newMrX.changeLocation(nextMove.destination);
				newMrX.modifyTickets(nextMove.ticket,-1);

				List<PlayerInfo> init = new ArrayList<>();
				init.add(newMrX);
				init.addAll(detectives);

				OurNewBoard newBoard = new OurNewBoard(init,board.getSetup());

				currentScore = minimaxAlphaBeta(depth+1,false,newBoard,newMrX,detectives,board,alpha,beta);

				if(currentScore > alpha){
					alpha = currentScore;
					bestMoveuleanu = nextMove;
				}
				if(beta <= alpha) break;
			}

			return currentScore;
		}

		else{
			int v = Integer.MAX_VALUE;
			for (Move.SingleMove nextMove : ourBoard.getAvailableMoves()){
				PlayerInfo newMrX = new PlayerInfo(mrX.giveTicketBoard(),mrX.getPiece(),mrX.getLocation());
				newMrX.changeLocation(nextMove.destination);
				newMrX.modifyTickets(nextMove.ticket,-1);

				List<PlayerInfo> init = new ArrayList<>();
				init.add(newMrX);
				init.addAll(detectives);

				OurNewBoard newBoard = new OurNewBoard(init,board.getSetup());
				currentScore = minimaxAlphaBeta(depth+1,false,newBoard,newMrX,detectives,board,alpha,beta);

				if(currentScore < beta){
					beta = currentScore;
				}
				if(beta <= alpha) break;
			}
			}
			return currentScore;
		}

	}

