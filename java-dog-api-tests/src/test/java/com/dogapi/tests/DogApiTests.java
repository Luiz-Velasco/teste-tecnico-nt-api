package com.dogapi.tests;

import com.dogapi.assertions.ApiAssertions;
import com.dogapi.client.DogApiClient;
import com.dogapi.config.TestConfig;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class DogApiTests {

    private DogApiClient dogApiClient;

    @BeforeEach
    void setUp() {
        dogApiClient = new DogApiClient();
    }

    @AfterEach
    void tearDown() {
        RestAssured.reset();
    }

    static Stream<String> validBreedsProvider() {
        return TestConfig.validBreeds().stream();
    }

    @Test
    @Tag("smoke")
    @Tag("contract")
    @DisplayName("GET /breeds/list/all - deve retornar lista de racas com contrato valido")
    void shouldValidateAllBreedsList() {
        Response response = dogApiClient.getAllBreeds();

        ApiAssertions.validateCommonApiResponse(response, 200);
        ApiAssertions.validateBreedsListContract(response, TestConfig.validBreed());
    }

    @ParameterizedTest(name = "GET /breed/breed/images com raca valida: {0}")
    @MethodSource("validBreedsProvider")
    @Tag("regression")
    @Tag("contract")
    @DisplayName("GET /breed/{breed}/images com raca valida - deve retornar lista de imagens validas")
    void shouldValidateBreedImagesForValidBreed(String breed) {
        Response response = dogApiClient.getBreedImages(breed);

        ApiAssertions.validateCommonApiResponse(response, 200);
        ApiAssertions.validateBreedImagesSuccessContract(response, breed);
    }

    @Test
    @Tag("negative")
    @Tag("contract")
    @DisplayName("GET /breed/{breed}/images com raca invalida - deve retornar erro controlado")
    void shouldValidateBreedImagesForInvalidBreed() {
        Response response = dogApiClient.getBreedImages(TestConfig.invalidBreed());

        ApiAssertions.validateCommonApiResponse(response, 404);
        ApiAssertions.validateBreedImagesInvalidBreedContract(response);
    }

    @Test
    @Tag("smoke")
    @Tag("contract")
    @DisplayName("GET /breeds/image/random - deve retornar imagem valida")
    void shouldValidateRandomImage() {
        Response response = dogApiClient.getRandomImage();

        ApiAssertions.validateCommonApiResponse(response, 200);
        ApiAssertions.validateRandomImageContract(response);
    }
}
