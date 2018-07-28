package com.iwe.avenger.repository;

import java.util.Optional;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.iwe.avenger.entity.Avenger;

public class AvengersDAO {

	private static final DynamoDBMapper mapper = DynamoDBManager.mapper();

	private static AvengersDAO instance;

	private AvengersDAO() {
	}

	public static AvengersDAO getInstance() {
		if (instance == null) {
			instance = new AvengersDAO();
		}
		return instance;
	}

	public Avenger find(final String id) {
		final Avenger avenger = mapper.load(Avenger.class, id);
		return Optional.ofNullable(avenger).get();
	}

	public Avenger save(final Avenger avenger) {
		mapper.save(avenger);
		return avenger;
	}

	public void delete(Avenger input) {
		mapper.delete(input);
	}

}