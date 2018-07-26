package com.iwe.avenger.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.http.HttpStatus;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.InternalServerErrorException;
import com.iwe.avenger.entity.AvengerEntity;

public class AvengersDAO {

	private static final DynamoDBMapper mapper = DynamoDBManager.mapper();

	private static volatile AvengersDAO instance;

	private AvengersDAO() {
	}

	public static AvengersDAO instance() {

		if (instance == null) {
			synchronized (AvengersDAO.class) {
				if (instance == null)
					instance = new AvengersDAO();
			}
		}
		return instance;
	}

	public AvengerEntity find(String id) {
		final AvengerEntity avenger = mapper.load(AvengerEntity.class, id);
		return Optional.ofNullable(avenger).get();
	}

	public AvengerEntity save(AvengerEntity avenger) {

		try {
			mapper.save(avenger);
		} catch (Exception e) {
			InternalServerErrorException internalError = new InternalServerErrorException(e.getMessage());
			internalError.setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
			throw internalError;
		}
		return avenger;
	}

	public List<AvengerEntity> find(final String name, final String secretIdentity) {

		final Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		eav.put(":name", new AttributeValue().withS(name));
		eav.put(":secretIdentity", new AttributeValue().withS(secretIdentity));

		AvengerEntity avenger = new AvengerEntity();
		avenger.setSecretIdentity(secretIdentity);
		
		DynamoDBQueryExpression<AvengerEntity> queryExpression = new DynamoDBQueryExpression<AvengerEntity>()
				.withKeyConditionExpression("secret_identity = :secretIdentity AND real_name = :name")
				.withIndexName("secret_identity-index")
				.withConsistentRead(false)
				.withExpressionAttributeValues(eav);

		return mapper.query(AvengerEntity.class, queryExpression);
	}

}