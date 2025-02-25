package br.com.fiap.triage_service;


import br.com.fiap.triage_service.dtos.CognitoTokenResponseDto;
import br.com.fiap.triage_service.dtos.TokenDto;
import br.com.fiap.triage_service.dtos.UrlDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

@RestController
public class AuthController {

    @Value("${spring.security.oauth2.client.registration.cognito.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.cognito.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.provider.cognito.issuerUri}")
    private String cognitoUri;

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    @GetMapping("/auth/url")
    public ResponseEntity<UrlDto> auth() {
        String url = cognitoUri +
                "/oauth2/authorize?" +
                "response_type=code" +
                "&client_id=" + clientId +
                "&redirect_uri=http://localhost:4200/oauth2/idpresponse" +
                "&scope=email+openid+profile";
        return ResponseEntity.ok(new UrlDto(url));
    }

    @GetMapping("/auth/callback")
    public ResponseEntity<TokenDto> callback(@RequestParam("code") String code) throws URISyntaxException {

        String urlStr = cognitoUri + "/oauth2/token?"
                + "grant_type=authorization_code" +
                "&client_id=" + clientId +
                "&code=" + code +
                "&redirect_uri=http://localhost:4200/oauth2/idpresponse";

        String authenticationInfo = clientId + ":" + clientSecret;
        String basicAuthenticationInfo = Base64.getEncoder().encodeToString(authenticationInfo.getBytes());

        HttpRequest request;
        try {
            request = HttpRequest.newBuilder(new URI(urlStr))
                    .header("Content-type", "application/x-www-form-urlencoded")
                    .header("Authorization", "Basic " + basicAuthenticationInfo)
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();
        } catch (URISyntaxException e) {
            throw new RuntimeException("Unable to build Cognito URL");
        }

        HttpClient client = HttpClient.newHttpClient();

        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Unable to request Cognito");
        }

        if (response.statusCode() != 200) {
            throw new RuntimeException("Authentication failed");
        }

        CognitoTokenResponseDto token;
        try {
            token = JSON_MAPPER.readValue(response.body(), CognitoTokenResponseDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to decode Cognito response");
        }

        return ResponseEntity.ok(new TokenDto(token.id_token()));
    }
}