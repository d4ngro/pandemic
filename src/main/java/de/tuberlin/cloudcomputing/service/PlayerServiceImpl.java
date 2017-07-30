package de.tuberlin.cloudcomputing.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import de.tuberlin.cloudcomputing.model.Player;

@Service
public class PlayerServiceImpl implements PlayerService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public Player createPlayer(String name) {
		String id = UUID.randomUUID().toString();
		Player player = new Player(id);
		player.setName(name);
		logger.info("Created player with id {} and name {}", id, name);
		return player;
	}
}
