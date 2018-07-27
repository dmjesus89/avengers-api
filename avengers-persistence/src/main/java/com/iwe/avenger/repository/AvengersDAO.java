package com.iwe.avenger.repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.apache.http.HttpStatus;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.InternalServerErrorException;
import com.iwe.avenger.entity.Avenger;
import com.iwe.avenger.exception.AvengerNotFoundException;

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

	public Avenger find(String id) {
		
		try {
			final Avenger avenger = mapper.load(Avenger.class, id);
			return Optional.ofNullable(avenger).get();
		} catch (NoSuchElementException e) {
			throw new AvengerNotFoundException();
		}
		
	}

	public Avenger save(Avenger avenger) {

		try {
			mapper.save(avenger);
		} catch (Exception e) {
			InternalServerErrorException internalError = new InternalServerErrorException(e.getMessage());
			internalError.setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
			throw internalError;
		}
		return avenger;
	}

	public List<Avenger> findByName(final String name) {

		final DynamoDBQueryExpression<Avenger> queryExpression = new DynamoDBQueryExpression<Avenger>();
		
		final Avenger avenger = new Avenger();
		avenger.setName(name);

        queryExpression.withHashKeyValues(avenger)
                       .withIndexName("HeroName-index")
                       .withConsistentRead(false);

		return mapper.query(Avenger.class, queryExpression);
	}

}