package com.iwe.avengers;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.util.StringUtils;
import com.iwe.avenger.entity.Avenger;
import com.iwe.avenger.exception.AvengerNotFoundException;
import com.iwe.avenger.repository.AvengersDAO;
import com.iwe.avenger.response.FunctionResponse;

public class ListAvengersHandler implements RequestHandler<Avenger, FunctionResponse> {

	private AvengersDAO dao = AvengersDAO.instance();

	@Override
	public FunctionResponse handleRequest(Avenger avenger, Context context) {

		context.getLogger().log("[#] - Iniciando função de pesquisa");

		if (!StringUtils.isNullOrEmpty(avenger.getId())) {

			context.getLogger().log("[#] - Operação identificada: Pesquisar por id");

			try {
				final Avenger avengerFind = dao.find(avenger.getId());
				context.getLogger().log("[#] - Vingador encontrado");
				return FunctionResponse.builder().setStatusCode(200).setObjectBody(avengerFind).build();
			} catch (AvengerNotFoundException e) {
				context.getLogger().log("[#] - Avenger não encontrado");
				return FunctionResponse.builder().setStatusCode(404).build();

			}

		}

		final List<Avenger> avengers = this.dao.findByName(avenger.getName());

		context.getLogger().log("[#] - Operação identificada: Listar todos");

		return FunctionResponse.builder().setStatusCode(200).setObjectBody(avengers).build();

	}
}
