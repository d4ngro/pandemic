package de.tuberlin.cloudcomputing.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import de.tuberlin.cloudcomputing.model.Game;
import de.tuberlin.cloudcomputing.model.Game.State;
import de.tuberlin.cloudcomputing.model.Player;
import de.tuberlin.cloudcomputing.repository.GameRepository;

@Service
public class GameServiceImpl implements GameService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private GameRepository gameRepo;

	public GameServiceImpl(GameRepository gameRepo) {
		this.gameRepo = gameRepo;
	}

	@Override
	public Game createGame(Player player) {
		Game game = new Game();
		game.getPlayers().add(player);
		game = gameRepo.save(game);
		logger.info("Created game with id {}", game.getId());
		return game;
	}

	@Override
	public Iterable<Game> getAllGames() {
		return gameRepo.findAll();
	}

	@Override
	public Game joinGame(String id, Player player) throws Exception {
		Game game = gameRepo.findOne(id);
		if (game == null) {
			throw new Exception("Didn't join game: game not found");
		}
		if (!game.getState().equals(State.WAITING)) {
			throw new Exception("Didn't join game: game already running");
		}
		game.getPlayers().add(player);
		StringBuffer loggerBuffer = new StringBuffer();
		loggerBuffer.append("Added player {} to game {}");
		if (game.getPlayers().size() == 4) {
			game.setState(State.RUNNING);
			loggerBuffer.append(" and startet game");
		}
		game = gameRepo.save(game);
		logger.info(loggerBuffer.toString(), player.getId(), id);
		return game;
	}
}
