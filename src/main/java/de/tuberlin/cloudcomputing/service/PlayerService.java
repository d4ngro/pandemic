package de.tuberlin.cloudcomputing.service;

import org.springframework.stereotype.Service;

import de.tuberlin.cloudcomputing.model.Player;

@Service
public interface PlayerService {

	public Player createPlayer(String name);
}
