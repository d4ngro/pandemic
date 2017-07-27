package de.tuberlin.cloudcomputing.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.tuberlin.cloudcomputing.model.Game;
import de.tuberlin.cloudcomputing.model.Game.State;
import de.tuberlin.cloudcomputing.model.Player;
import de.tuberlin.cloudcomputing.repository.GameRepository;

@RestController
@RequestMapping("/api/game")
public class GameController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private GameRepository gameRepo;

	@CrossOrigin
	@PostMapping(value = "/")
	public Game newGame(@RequestBody Player player) {
		Game game = new Game();
		game.addPlayer(player);
		gameRepo.save(game);
		logger.info("game created with id {}", game.getId());
		return game;
	}

	@CrossOrigin
	@PostMapping(value = "/{id}/join")
	public Game joinGame(@RequestBody Player player, @PathVariable String id) {
		Game game = gameRepo.findOne(id);
		if (game != null) {
			game.addPlayer(player);
			logger.info("player {} added to game {}", player.getId(), id);
			if (game.getPlayers().size() == 4) {
				game.setState(State.RUNNING);
				logger.info("game {} started", id);
			}
			gameRepo.save(game);
		}
		return game;
	}

	@CrossOrigin
	@GetMapping(value = "/")
	public Iterable<Game> getGames() {
		return gameRepo.findAll();
	}
}
