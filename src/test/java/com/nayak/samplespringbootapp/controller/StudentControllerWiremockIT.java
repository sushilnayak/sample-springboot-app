package com.nayak.samplespringbootapp.controller;

import com.github.jenspiegsa.wiremockextension.Managed;
import com.github.jenspiegsa.wiremockextension.WireMockExtension;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.nayak.samplespringbootapp.model.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.github.jenspiegsa.wiremockextension.ManagedWireMockServer.with;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@ExtendWith({WireMockExtension.class, SpringExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerWiremockIT {

    @Managed
    private WireMockServer wireMockServer = with(wireMockConfig().dynamicPort());

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void basicTest() {

        wireMockServer.stubFor(get("/api/v1/students").willReturn(aResponse().withStatus(HttpStatus.CREATED.value())
                .withHeader("Content-Type", APPLICATION_JSON_VALUE)
                .withBody("[{\"id\":1,\"name\":\"a\",\"age\":1,\"height\":29.0,\"weight\":10.0},{\"id\":2,\"name\":\"b\",\"age\":2,\"height\":30.0,\"weight\":15.0}]")
        ));

        Student[] students = testRestTemplate.getForObject("http://localhost:" + wireMockServer.port() + "/api/v1/students", Student[].class);

        assertThat(students).hasSize(2);
    }
}
