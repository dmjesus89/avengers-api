package com.iwe.avenger.repository;

import java.util.Optional;

import org.apache.http.HttpStatus;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.InternalServerErrorException;
import com.iwe.avenger.entity.AvengerEntity;

public class AvengersDAO {

	//private static final Logger logger = LogManager.getLogger(AvengersDAO.class);
	
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
//		logger.info("[#] - Buscando o Avenger com o id " + id + " no DynamoDB");
		final AvengerEntity avenger = mapper.load(AvengerEntity.class, id);
		return Optional.ofNullable(avenger).get();
	}

	public AvengerEntity save(AvengerEntity avenger) {

//		logger.info("[#] - Salvando o Avenger: " + avenger.getName() + " no DynamoDB");

		try {
			mapper.save(avenger);
//			logger.info("[#] - Avenger salco com sucesso");
		} catch (Exception e) {
			InternalServerErrorException internalError = new InternalServerErrorException(e.getMessage());
			internalError.setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
			throw internalError ;
		}
		return avenger;
	}
}