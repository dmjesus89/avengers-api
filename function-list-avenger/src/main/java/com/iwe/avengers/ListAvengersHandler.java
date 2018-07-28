package com.iwe.avengers;

import java.util.NoSuchElementException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.iwe.avenger.entity.Avenger;
import com.iwe.avenger.exception.AvengerNotFoundException;
import com.iwe.avenger.repository.AvengersDAO;
import com.iwe.avenger.response.FunctionResponse;

public class ListAvengersHandler implements RequestHandler<Avenger, FunctionResponse> {

	private AvengersDAO dao = AvengersDAO.getInstance();

	@Override
	public FunctionResponse handleRequest(Avenger avenger, Context context) {

		context.getLogger().log("[#] - Iniciando função de pesquisa");

		try {
			final Avenger avengerFind = dao.find(avenger.getId());
			context.getLogger().log("[#] - Vingador encontrado");
			return FunctionResponse.builder().setStatusCode(200).setObjectBody(avengerFind).build();
		} catch (NoSuchElementException e) {
			throw new AvengerNotFoundException("[NotFound] - Avenger id: " + avenger.getId() + " not found");
		}
	}
}
