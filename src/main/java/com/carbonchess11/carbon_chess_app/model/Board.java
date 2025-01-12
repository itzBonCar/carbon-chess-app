package com.carbonchess11.carbon_chess_app.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    private List<List<String>> board;
    private String turn;

    public Board() {
        // Initialize the board as a List of Lists
        this.board = new ArrayList<>();
        String[][] initialSetup = {
                {"r", "n", "b", "q", "k", "b", "n", "r"},
                {"p", "p", "p", "p", "p", "p", "p", "p"},
                {"", "", "", "", "", "", "", ""},
                {"", "", "", "", "", "", "", ""},
                {"", "", "", "", "", "", "", ""},
                {"", "", "", "", "", "", "", ""},
                {"P", "P", "P", "P", "P", "P", "P", "P"},
                {"R", "N", "B", "Q", "K", "B", "N", "R"}
        };

        // Convert the 2D array to a List<List<String>>
        for (String[] row : initialSetup) {
            this.board.add(Arrays.asList(row));
        }

        this.turn = "white"; // White moves first
    }

    public List<List<String>> getBoard() {
        return board;
    }

    public void setBoard(List<List<String>> board) {
        this.board = board;
    }

    public String getTurn() {
        return turn;
    }

    public void setTurn(String turn) {
        this.turn = turn;
    }
}
