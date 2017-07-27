package de.tuberlin.cloudcomputing.repository;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import de.tuberlin.cloudcomputing.model.Game;

@EnableScan
public interface GameRepository extends CrudRepository<Game, String> {

}
