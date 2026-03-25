package com.dogapi.assertions;

import com.dogapi.config.TestConfig;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public final class ApiAssertions {

    private ApiAssertions() {
    }

    public static void validateCommonApiResponse(Response response, int expectedStatusCode) {
        assertThat(response.statusCode())
                .as("Status code")
                .isEqualTo(expectedStatusCode);

        assertThat(response.getTime())
                .as("Response time in ms")
                .isLessThan(TestConfig.maxResponseTimeMs());

        assertThat(response.getHeader("Content-Type"))
                .as("Content-Type")
                .contains(TestConfig.expectedContentType());

        assertThat(response.asString())
                .as("Response body")
                .isNotBlank();
    }

    public static void validateBreedsListContract(Response response, String expectedBreed) {
        JsonPath json = response.jsonPath();

        assertThat(json.getString("status"))
                .as("API status")
                .isEqualTo("success");

        Map<String, List<String>> breeds = json.getMap("message");
        assertThat(breeds)
                .as("Breeds map")
                .isNotNull()
                .isNotEmpty();

        assertThat(breeds)
                .as("Breeds map should contain expected breed")
                .containsKey(expectedBreed);
    }

    public static void validateBreedImagesSuccessContract(Response response, String breed) {
        JsonPath json = response.jsonPath();

        assertThat(json.getString("status"))
                .as("API status")
                .isEqualTo("success");

        List<String> images = json.getList("message");
        assertThat(images)
                .as("Image list")
                .isNotNull()
                .isNotEmpty();

        for (String imageUrl : images) {
            validateImageUrlFormat(imageUrl);
            assertThat(imageUrl.toLowerCase())
                    .as("Image URL should belong to requested breed")
                    .contains("/breeds/" + breed.toLowerCase());
        }
    }

    public static void validateBreedImagesInvalidBreedContract(Response response) {
        JsonPath json = response.jsonPath();

        assertThat(json.getString("status"))
                .as("API status")
                .isEqualTo("error");

        assertThat(json.getString("message").toLowerCase())
                .as("Error message")
                .contains("not found");
    }

    public static void validateRandomImageContract(Response response) {
        JsonPath json = response.jsonPath();

        assertThat(json.getString("status"))
                .as("API status")
                .isEqualTo("success");

        String imageUrl = json.getString("message");
        assertThat(imageUrl)
                .as("Random image URL")
                .isNotBlank()
                .contains("/breeds/");

        validateImageUrlFormat(imageUrl);
    }

    private static void validateImageUrlFormat(String url) {
        assertThat(url)
                .as("Image URL format")
                .matches("(?i)^https?://.+\\.(jpg|jpeg|png|gif|webp)$");
    }
}
