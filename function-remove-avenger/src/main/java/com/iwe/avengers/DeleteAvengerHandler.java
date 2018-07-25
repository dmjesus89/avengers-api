package com.iwe.avengers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.iwe.avenger.entity.AvengerEntity;
import com.iwe.avenger.response.FunctionResponse;

public class DeleteAvengerHandler implements RequestHandler<AvengerEntity, FunctionResponse>{

	@Override
	public FunctionResponse handleRequest(AvengerEntity input, Context context) {

		context.getLogger().log("[#] - Iniciando Operacao de remoção do avenger de id " + input.getId());
			
		return FunctionResponse.builder().setStatusCode(204).build();
	}

}
