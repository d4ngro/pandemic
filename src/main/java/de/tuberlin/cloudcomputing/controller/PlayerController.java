package de.tuberlin.cloudcomputing.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.tuberlin.cloudcomputing.model.Player;

@RestController
@RequestMapping("/api/player")
public class PlayerController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@CrossOrigin
	@PostMapping(value = "/{name}")
	public Player newPlayer(@PathVariable String name) {
		String id = UUID.randomUUID().toString();
		Player player = new Player(id);
		player.setName(name);
		logger.info("player with id {} and name {} created", id, name);
		return player;
	}
}
