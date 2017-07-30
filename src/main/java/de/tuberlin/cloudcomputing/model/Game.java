package de.tuberlin.cloudcomputing.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedJson;

import lombok.Data;

@Data
@DynamoDBTable(tableName = "pandemic")
public class Game {

	public enum State {
		WAITING, RUNNING, WON, LOST
	}

	@Id
	@DynamoDBHashKey
	@DynamoDBAutoGeneratedKey
	private String id;

	@DynamoDBAttribute
	@DynamoDBTypeConvertedJson
	private List<Player> players;

	@DynamoDBAttribute
	@DynamoDBTypeConvertedJson
	private State state;

	{
		players = new ArrayList<Player>();
		state = State.WAITING;
	}
}
