package de.tuberlin.cloudcomputing.service;

import org.springframework.stereotype.Service;

import de.tuberlin.cloudcomputing.model.Game;
import de.tuberlin.cloudcomputing.model.Player;

@Service
public interface GameService {

	public Game createGame(Player player);

	// TODO create and throw specific exceptions
	public Game joinGame(String id, Player player) throws Exception;

	public Iterable<Game> getAllGames();
}
