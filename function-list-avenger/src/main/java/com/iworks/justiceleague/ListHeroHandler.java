package com.iworks.justiceleague;

import com.amazonaws.services.dynamodbv2.model.InternalServerErrorException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.iworks.justiceleague.entity.HeroEntity;
import com.iworks.justiceleague.repository.HeroDAO;
import com.iworks.justiceleague.util.ApiGatewayResponse;
import java.util.List;

public class ListHeroHandler implements RequestHandler<HeroEntity, ApiGatewayResponse> {

    private static final HeroDAO heroDao = HeroDAO.instance();

    @Override
    public ApiGatewayResponse handleRequest (HeroEntity clienteRequest, Context context) {
        try {
            List<HeroEntity> list = heroDao
                    .findHeroByNameOrSecretIdentity(clienteRequest.getRealName(), clienteRequest.getSecretIdentity());
            return ApiGatewayResponse.builder()
                    .setStatusCode(200)
                    .setObjectBody(list)
                    .build();
        } catch (InternalServerErrorException e) {
            return ApiGatewayResponse.builder()
                    .setStatusCode(e.getStatusCode())
                    .setObjectBody(e.getMessage())
                    .build();
        }

    }
}
