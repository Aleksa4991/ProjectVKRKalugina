package org.urfu.adservice.services;

import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;


@Service
public class UserServiceConnector {

    @Value("${userservice.host}")
    private String uriUserServiceHost;

    @Value("${userservice.port}")
    private String uriUserServicePort;

    public Mono<Object> retrieveuserserviceData(String uri) {
        WebClient client = WebClient.builder().baseUrl(uriUserServiceHost + ":" + uriUserServicePort)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();

        Mono<Object> response = client.method(HttpMethod.GET).uri(uri).accept(MediaType.APPLICATION_JSON)
                .acceptCharset(Charset.forName("UTF-8")).retrieve().bodyToMono(Object.class);

        return response;
    }
}
