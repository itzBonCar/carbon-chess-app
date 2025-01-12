package com.carbonchess11.carbon_chess_app.service;

import com.carbonchess11.carbon_chess_app.model.Board;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChessService {

    public boolean makeMove(Board board, String from, String to) {
        List<List<String>> state = board.getBoard();
        int[] fromPos = parsePosition(from);
        int[] toPos = parsePosition(to);

        if (fromPos == null || toPos == null || !isValidMove(state, fromPos, toPos, board.getTurn())) {
            return false;
        }

        // Execute the move
        state.get(toPos[0]).set(toPos[1], state.get(fromPos[0]).get(fromPos[1]));
        state.get(fromPos[0]).set(fromPos[1], "");

        // Change the turn
        board.setTurn(board.getTurn().equals("white") ? "black" : "white");
        return true;
    }

    private int[] parsePosition(String position) {
        if (position.length() != 2) return null;
        int row = 8 - Character.getNumericValue(position.charAt(1));
        int col = position.charAt(0) - 'a';
        return (row >= 0 && row < 8 && col >= 0 && col < 8) ? new int[]{row, col} : null;
    }

    private boolean isValidMove(List<List<String>> state, int[] from, int[] to, String turn) {
        String piece = state.get(from[0]).get(from[1]);
        if (piece.isEmpty() || !isCorrectTurn(piece, turn)) return false;

        // Prevent moving to a square occupied by the same color
        String destination = state.get(to[0]).get(to[1]);
        if (!destination.isEmpty() && isSameColor(piece, destination)) return false;

        return true; // Simplified move validation
    }

    private boolean isCorrectTurn(String piece, String turn) {
        return (Character.isUpperCase(piece.charAt(0)) && turn.equals("white"))
                || (Character.isLowerCase(piece.charAt(0)) && turn.equals("black"));
    }

    private boolean isSameColor(String piece1, String piece2) {
        return Character.isUpperCase(piece1.charAt(0)) == Character.isUpperCase(piece2.charAt(0));
    }
}
