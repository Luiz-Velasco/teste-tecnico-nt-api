package com.dogapi.client;

import com.dogapi.config.TestConfig;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class DogApiClient {

    private static RequestSpecification baseRequest() {
        return RestAssured.given()
                .baseUri(TestConfig.baseUrl())
                .header("Accept", "application/json")
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter());
    }

    public Response getAllBreeds() {
        return baseRequest()
                .when()
                .get("/breeds/list/all")
                .then()
                .extract()
                .response();
    }

    public Response getBreedImages(String breed) {
        return baseRequest()
                .when()
                .get("/breed/{breed}/images", breed)
                .then()
                .extract()
                .response();
    }

    public Response getRandomImage() {
        return baseRequest()
                .when()
                .get("/breeds/image/random")
                .then()
                .extract()
                .response();
    }
}
