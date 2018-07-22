package com.iworks.justiceleague.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.http.HttpStatus;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.InternalServerErrorException;
import com.iworks.justiceleague.entity.HeroEntity;
import com.iworks.justiceleague.exception.HeroDoesNotExistException;

public class HeroDAO {

	private static final DynamoDBMapper mapper = DynamoDBManager.mapper();

	private static volatile HeroDAO instance;

	private HeroDAO() {
	}

	public static HeroDAO instance() {

		if (instance == null) {
			synchronized (HeroDAO.class) {
				if (instance == null)
					instance = new HeroDAO();
			}
		}
		return instance;
	}

	public List<HeroEntity> findAllHeroes() {
		return mapper.scan(HeroEntity.class, new DynamoDBScanExpression());
	}

	public void deleteEvent(String heroId) throws HeroDoesNotExistException {
		Optional<HeroEntity> heroEntity = findHeroById(heroId);
		if (heroEntity.isPresent()) {
			mapper.delete(heroEntity.get());
		} else {
			throw new HeroDoesNotExistException("Hero does not exist");
		}
	}

	public Optional<HeroEntity> findHeroById(String heroId) {
		HeroEntity event = mapper.load(HeroEntity.class, heroId);
		return Optional.ofNullable(event);
	}

	public List<HeroEntity> findHeroByNameOrSecretIdentity(String name, String secretIdentity) {
		List<HeroEntity> heroes = null;

		Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		String queryString = "";
		if (name != null && !name.isEmpty()) {
			eav.put(":real_name", new AttributeValue().withS(name));
			queryString += "real_name = :real_name";
		}

		if (secretIdentity != null && !secretIdentity.isEmpty()) {
			eav.put(":secret_identity", new AttributeValue().withS(secretIdentity));
			if (name != null) {
				queryString += " and secret_identity = :secret_identity";
			} else {
				queryString += " secret_identity = :secret_identity";
			}
		}
		
		if (eav.isEmpty()) {
			return findAllHeroes();
		}

		try {
			DynamoDBScanExpression scanExpression = new DynamoDBScanExpression().withFilterExpression(queryString)
					.withExpressionAttributeValues(eav);
			heroes = mapper.scan(HeroEntity.class, scanExpression);
		} catch (Exception e) {
			InternalServerErrorException internalError = new InternalServerErrorException(e.getMessage());
			internalError.setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
			throw internalError ;
		}
		return heroes;

	}

	public HeroEntity update(HeroEntity hero) throws HeroDoesNotExistException {
		if (hero.getId() != null) {
			Optional<HeroEntity> heroEntity = findHeroById(hero.getId());
			if (!heroEntity.isPresent()) {
				throw new HeroDoesNotExistException("Hero does not exist");
			}
		}

		return save(hero);
	}

	public HeroEntity save(HeroEntity hero) throws HeroDoesNotExistException {
		try {
			mapper.save(hero);
		} catch (Exception e) {
			InternalServerErrorException internalError = new InternalServerErrorException(e.getMessage());
			internalError.setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
			throw internalError ;
		}
		return hero;
	}
}