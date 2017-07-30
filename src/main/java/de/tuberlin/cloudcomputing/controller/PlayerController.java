package de.tuberlin.cloudcomputing.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.tuberlin.cloudcomputing.model.Player;
import de.tuberlin.cloudcomputing.service.PlayerService;

@RestController
@RequestMapping("/api/player")
public class PlayerController {

	private PlayerService playerService;

	public PlayerController(PlayerService playerService) {
		this.playerService = playerService;
	}

	@CrossOrigin
	@PostMapping(value = "/{name}")
	public Player newPlayer(@PathVariable String name) {
		return playerService.createPlayer(name);
	}
}
