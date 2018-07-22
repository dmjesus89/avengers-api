package com.iworks.justiceleague;

import com.amazonaws.services.dynamodbv2.model.InternalServerErrorException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.iworks.justiceleague.entity.HeroEntity;
import com.iworks.justiceleague.repository.HeroDAO;
import com.iworks.justiceleague.util.ApiGatewayResponse;

public class CreateHeroHandler implements RequestHandler<HeroEntity, ApiGatewayResponse> {

    private static final HeroDAO heroDao = HeroDAO.instance();

    @Override
    public ApiGatewayResponse handleRequest (HeroEntity hero, Context context) {
        try {
            HeroEntity heroSave = heroDao.save(hero);
            return ApiGatewayResponse.builder()
                    .setStatusCode(201)
                    .setObjectBody(heroSave)
                    .build();
        } catch (InternalServerErrorException e) {
            return ApiGatewayResponse.builder()
                    .setStatusCode(e.getStatusCode())
                    .setObjectBody(e.getMessage())
                    .build();
        }
    }
}
