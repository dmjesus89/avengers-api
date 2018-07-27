package com.iwe.avengers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.iwe.avenger.entity.Avenger;
import com.iwe.avenger.response.FunctionResponse;

public class UpdateAvengerHandler implements RequestHandler<Avenger, FunctionResponse>{

	@Override
	public FunctionResponse handleRequest(Avenger input, Context context) {

		context.getLogger().log("[#] - Iniciando Operacao de atualizacao do avenger de id " + input.getId());
			
		return FunctionResponse.builder().setStatusCode(200).setObjectBody(input).build();
	}

}
