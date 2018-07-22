package com.iworks.justiceleague;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.iworks.justiceleague.entity.HeroEntity;
import com.iworks.justiceleague.exception.HeroDoesNotExistException;
import com.iworks.justiceleague.repository.HeroDAO;
import com.iworks.justiceleague.util.ApiGatewayResponse;

public class UpdateHeroHandler implements RequestHandler<HeroEntity, ApiGatewayResponse> {

    private static final HeroDAO heroDao = HeroDAO.instance();

    @Override
    public ApiGatewayResponse handleRequest (HeroEntity clienteRequest, Context context) {
        try {
            heroDao.update(clienteRequest);
            return ApiGatewayResponse.builder()
                    .setStatusCode(200)
                    .build();
        } catch (HeroDoesNotExistException e) {
            return ApiGatewayResponse.builder()
                    .setStatusCode(400)
                    .build();
        }
    }
}
