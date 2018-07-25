package com.iwe.avengers;

import com.amazonaws.services.dynamodbv2.model.InternalServerErrorException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.iwe.avenger.entity.AvengerEntity;
import com.iwe.avenger.repository.AvengersDAO;
import com.iwe.avenger.response.FunctionResponse;

public class CreateAvengerHandler implements RequestHandler<AvengerEntity, FunctionResponse> {

	private AvengersDAO dao = AvengersDAO.instance();

	@Override
	public FunctionResponse handleRequest(AvengerEntity newAvenger, Context context) {

		context.getLogger().log("[#] - Iniciando Operacao de criacao de mais um novo Avenger");

		try {
			final AvengerEntity avengerSalvo = dao.save(newAvenger);
			
			context.getLogger().log("[#] - Avenger criado com sucessor");
			return FunctionResponse.builder().setStatusCode(201).setObjectBody(avengerSalvo).build();
		} catch (InternalServerErrorException e) {
			
			context.getLogger().log(e.getMessage());
			
			return FunctionResponse.builder().setStatusCode(e.getStatusCode()).setObjectBody(e.getMessage()).build();
		}

		
	}

}
