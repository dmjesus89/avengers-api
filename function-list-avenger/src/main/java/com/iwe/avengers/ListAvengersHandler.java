package com.iwe.avengers;

import java.util.Arrays;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.util.StringUtils;
import com.iwe.avenger.entity.AvengerEntity;
import com.iwe.avenger.response.FunctionResponse;

public class ListAvengersHandler implements RequestHandler<AvengerEntity, FunctionResponse> {

	@Override
	public FunctionResponse handleRequest(AvengerEntity avenger, Context context) {

		context.getLogger().log("[#] - Iniciando função de listagem");
		
		
		if(!StringUtils.isNullOrEmpty(avenger.getId())) {
			context.getLogger().log("[#] - Operação identificada: Pesquisar por id");
			
			return FunctionResponse.builder().setStatusCode(200)
					.setObjectBody(new AvengerEntity("1", "Wolwerine", "Logan")).build();
		}
		
		if (StringUtils.isNullOrEmpty(avenger.getName()) && StringUtils.isNullOrEmpty(avenger.getSecretIdentity())) {
			
			context.getLogger().log("[#] - Operação identificada: Listar todos");
			
			return FunctionResponse.builder().setStatusCode(200)
					.setObjectBody(Arrays.asList(new AvengerEntity("1", "Captain America", "Steve Rogers"),
							new AvengerEntity("2", "Spider Man", "Peter Parker"))).build();
		}

		context.getLogger().log("[#] - Operação identificada: Listar por Parametros");
		
		return FunctionResponse.builder().setStatusCode(200)
				.setObjectBody(new AvengerEntity("1", "Captain America", "Steve Rogers")).build();

	}

}
