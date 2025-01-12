package com.carbonchess11.carbon_chess_app.controller;

import com.carbonchess11.carbon_chess_app.model.Board;
import com.carbonchess11.carbon_chess_app.service.ChessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/chess")
public class ChessController {

    @Autowired
    private ChessService chessService;

    private Board board = new Board(); // Single instance for game state

    private static final Logger logger = LoggerFactory.getLogger(ChessController.class);

    @GetMapping("/chess")
    public String showChessPage() {
        logger.info("ChessController: Handling request for /chess");
        return "chess"; // Corresponds to chess.html in the templates folder
    }


    /**
     * Endpoint to serve the chess game HTML page.
     * @param model the model to pass board data to the view
     * @return the chess.html template
     */
    @GetMapping("/")
    public String chessGame(Model model) {
        model.addAttribute("board", board.getBoard()); // Pass the board data to the view
        model.addAttribute("turn", board.getTurn()); // Pass the turn data to the view
        return "chess"; // Returns chess.html from templates folder
    }

    /**
     * API to get the current state of the chess board.
     * @return a map containing the board and turn information
     */
    @GetMapping("/api/board")
    @ResponseBody
    public Map<String, Object> getBoard() {
        Map<String, Object> response = new HashMap<>();
        response.put("board", board.getBoard());
        response.put("turn", board.getTurn());
        return response;
    }

    /**
     * API to make a move on the chess board.
     * @param from the starting position of the piece (e.g., "e2")
     * @param to the target position of the piece (e.g., "e4")
     * @return a map containing the success status, updated board, and current turn
     */
    @PostMapping("/api/move")
    @ResponseBody
    public Map<String, Object> makeMove(@RequestParam("from") String from, @RequestParam("to") String to) {
        boolean success = chessService.makeMove(board, from, to);
        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("board", board.getBoard());
        response.put("turn", board.getTurn());
        return response;
    }
}