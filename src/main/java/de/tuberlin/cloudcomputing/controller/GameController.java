package de.tuberlin.cloudcomputing.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.tuberlin.cloudcomputing.model.Game;
import de.tuberlin.cloudcomputing.model.Player;
import de.tuberlin.cloudcomputing.service.GameService;

@RestController
@RequestMapping("/api/game")
public class GameController {

	private GameService gameService;

	public GameController(GameService gameService) {
		this.gameService = gameService;
	}

	@CrossOrigin
	@PostMapping(value = "/")
	public Game newGame(@RequestBody Player player) {
		return gameService.createGame(player);
	}

	@CrossOrigin
	@PostMapping(value = "/{id}/join")
	public Game joinGame(@RequestBody Player player, @PathVariable String id) throws Exception {
		return gameService.joinGame(id, player);
	}

	@CrossOrigin
	@GetMapping(value = "/")
	public Iterable<Game> getGames() {
		return gameService.getAllGames();
	}
}
