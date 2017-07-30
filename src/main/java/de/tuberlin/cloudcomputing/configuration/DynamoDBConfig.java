package de.tuberlin.cloudcomputing.configuration;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ResourceInUseException;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;

@Configuration
@EnableDynamoDBRepositories(basePackages = "de.tuberlin.cloudcomputing.repository")
public class DynamoDBConfig {

	private static final String PANDEMIC_TABLE = "pandemic";
	private static final String GAME_ID = "id";

	@Value("${dynamodb.serviceEndpoint}")
	private String serviceEndpoint;

	@Value("${dynamodb.signingRegion}")
	private String signingRegion;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Bean
	public AmazonDynamoDB amazonDynamoDB() throws Exception {
		AmazonDynamoDB client = null;
		client = AmazonDynamoDBClientBuilder.standard()
				.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(serviceEndpoint, signingRegion))
				.build();
		logger.info("Connected to local DynamoDB");
		createPandemicTableIfNotExists(client);
		return client;
	}

	private void createPandemicTableIfNotExists(AmazonDynamoDB client) throws InterruptedException {
		DynamoDB dynamoDB = new DynamoDB(client);
		try {
			dynamoDB.createTable(PANDEMIC_TABLE, Arrays.asList(new KeySchemaElement(GAME_ID, KeyType.HASH)),
					Arrays.asList(new AttributeDefinition(GAME_ID, ScalarAttributeType.S)),
					new ProvisionedThroughput(10L, 10L)).waitForActive();
			logger.info("Created pandemic table");
		} catch (ResourceInUseException e) {
			logger.info("Didn't create pandemic table: table already exists");
		}
	}
}
