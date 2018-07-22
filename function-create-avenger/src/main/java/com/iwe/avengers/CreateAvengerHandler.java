package com.iwe.avengers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.iwe.avenger.entity.AvengerEntity;
import com.iwe.avenger.response.FunctionResponse;

public class CreateAvengerHandler implements RequestHandler<AvengerEntity, FunctionResponse>{

	@Override
	public FunctionResponse handleRequest(AvengerEntity input, Context context) {

		context.getLogger().log("[#] - Iniciando Operacao de criacao de mais um novo Avenger");
		
		input.setId("1");
		
		return FunctionResponse.builder().setStatusCode(201).setObjectBody(input).build();
	}

}
