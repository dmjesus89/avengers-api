package com.iwe.avengers;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.util.StringUtils;
import com.iwe.avenger.entity.AvengerEntity;
import com.iwe.avenger.repository.AvengersDAO;
import com.iwe.avenger.response.FunctionResponse;

public class ListAvengersHandler implements RequestHandler<AvengerEntity, FunctionResponse> {

	private AvengersDAO dao = AvengersDAO.instance();

	@Override
	public FunctionResponse handleRequest(AvengerEntity avenger, Context context) {

		context.getLogger().log("[#] - Iniciando função de pesquisa");

		if (!StringUtils.isNullOrEmpty(avenger.getId())) {

			context.getLogger().log("[#] - Operação identificada: Pesquisar por id");

			final AvengerEntity avengerFind = dao.find(avenger.getId());

			if (avengerFind == null) {

				context.getLogger().log("[#] - Avenger não encontrado");
				return FunctionResponse.builder().setStatusCode(404).build();

			} else {

				context.getLogger().log("[#] - Vingador encontrado");
				return FunctionResponse.builder().setStatusCode(200).setObjectBody(avengerFind).build();
			}

		}

		final List<AvengerEntity> avengers = this.dao.find(avenger.getName(), avenger.getSecretIdentity());

		context.getLogger().log("[#] - Operação identificada: Listar todos");

		return FunctionResponse.builder().setStatusCode(200).setObjectBody(avengers).build();
	}

}
