package com.iwe.avengers;

import java.util.NoSuchElementException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.iwe.avenger.entity.Avenger;
import com.iwe.avenger.exception.AvengerNotFoundException;
import com.iwe.avenger.repository.AvengersDAO;
import com.iwe.avenger.response.FunctionResponse;

public class DeleteAvengerHandler implements RequestHandler<Avenger, FunctionResponse> {

	private final AvengersDAO dao = AvengersDAO.getInstance();

	@Override
	public FunctionResponse handleRequest(final Avenger avenger, final Context context) {

		context.getLogger().log("[#] - Iniciando Operacao de remoção do avenger de id " + avenger.getId());

		try {
			dao.find(avenger.getId());
		} catch (NoSuchElementException e) {
			throw new AvengerNotFoundException("[NotFound] - Avenger id: " + avenger.getId() + " not found");
		}

		dao.delete(avenger);

		return FunctionResponse.builder().setStatusCode(204).build();
	}

}
