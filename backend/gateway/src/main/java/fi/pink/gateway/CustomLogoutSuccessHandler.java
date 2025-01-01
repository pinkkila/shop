package fi.pink.gateway;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.server.DefaultServerRedirectStrategy;
import org.springframework.security.web.server.ServerRedirectStrategy;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.RedirectServerLogoutSuccessHandler;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class CustomLogoutSuccessHandler implements ServerLogoutSuccessHandler {

    private final ServerRedirectStrategy redirectStrategy = new DefaultServerRedirectStrategy();

    private final RedirectServerLogoutSuccessHandler serverLogoutSuccessHandler = new RedirectServerLogoutSuccessHandler();

    private final ReactiveClientRegistrationRepository clientRegistrationRepository;

    private String postLogoutRedirectUri;

    public CustomLogoutSuccessHandler(
        ReactiveClientRegistrationRepository clientRegistrationRepository) {
        Assert.notNull(clientRegistrationRepository, "clientRegistrationRepository cannot be null");
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    @Override
    public Mono<Void> onLogoutSuccess(WebFilterExchange exchange, Authentication authentication) {
        // @formatter:off
        return Mono.just(authentication)
            .filter(OAuth2AuthenticationToken.class::isInstance)
            .filter((token) -> authentication.getPrincipal() instanceof OidcUser)
            .map(OAuth2AuthenticationToken.class::cast)
            .map(OAuth2AuthenticationToken::getAuthorizedClientRegistrationId)
            .flatMap(this.clientRegistrationRepository::findByRegistrationId)
            .flatMap((clientRegistration) -> {
                URI endSessionEndpoint = endSessionEndpoint(clientRegistration);
                if (endSessionEndpoint == null) {
                    return Mono.empty();
                }
                String idToken = idToken(authentication);
                String postLogoutRedirectUri = postLogoutRedirectUri(exchange.getExchange().getRequest(), clientRegistration);
                return Mono.just(endpointUri(endSessionEndpoint, idToken, postLogoutRedirectUri));
            })
            .switchIfEmpty(
                this.serverLogoutSuccessHandler.onLogoutSuccess(exchange, authentication).then(Mono.empty())
            )
//            .flatMap((endpointUri) -> this.redirectStrategy.sendRedirect(exchange.getExchange(), URI.create(endpointUri)));
            // This will return 202 to awoid redirect.
            .flatMap((endpointUri) -> {
                ServerHttpResponse response = exchange.getExchange().getResponse();
                response.setStatusCode(HttpStatus.ACCEPTED);
                response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

                // Construct a JSON response with the logout URL
//                String jsonResponse = "{\"logoutUrl\": \"" + endpointUri + "\"}";
                String jsonResponse = "{\"logoutUrl\": \"" + endpointUri + "/shop/\"}";
                DataBuffer buffer = response.bufferFactory().wrap(jsonResponse.getBytes(StandardCharsets.UTF_8));

                // Write the response and return
                return response.writeWith(Mono.just(buffer));
            });
        // @formatter:on
    }


    private URI endSessionEndpoint(ClientRegistration clientRegistration) {
        if (clientRegistration != null) {
            Object endSessionEndpoint = clientRegistration.getProviderDetails()
                .getConfigurationMetadata()
                .get("end_session_endpoint");
            if (endSessionEndpoint != null) {
                return URI.create(endSessionEndpoint.toString());
            }
        }
        return null;
    }

    private String endpointUri(URI endSessionEndpoint, String idToken, String postLogoutRedirectUri) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUri(endSessionEndpoint);
        builder.queryParam("id_token_hint", idToken);
        if (postLogoutRedirectUri != null) {
            builder.queryParam("post_logout_redirect_uri", postLogoutRedirectUri);
        }
        return builder.encode(StandardCharsets.UTF_8).build().toUriString();
    }

    private String idToken(Authentication authentication) {
        return ((OidcUser) authentication.getPrincipal()).getIdToken().getTokenValue();
    }

    private String postLogoutRedirectUri(ServerHttpRequest request, ClientRegistration clientRegistration) {
        if (this.postLogoutRedirectUri == null) {
            return null;
        }
        // @formatter:off
        UriComponents uriComponents = UriComponentsBuilder.fromUri(request.getURI())
            .replacePath(request.getPath().contextPath().value())
            .replaceQuery(null)
            .fragment(null)
            .build();

        Map<String, String> uriVariables = new HashMap<>();
        String scheme = uriComponents.getScheme();
        uriVariables.put("baseScheme", (scheme != null) ? scheme : "");
        uriVariables.put("baseUrl", uriComponents.toUriString());

        String host = uriComponents.getHost();
        uriVariables.put("baseHost", (host != null) ? host : "");

        String path = uriComponents.getPath();
        uriVariables.put("basePath", (path != null) ? path : "");

        int port = uriComponents.getPort();
        uriVariables.put("basePort", (port == -1) ? "" : ":" + port);

        uriVariables.put("registrationId", clientRegistration.getRegistrationId());

        return UriComponentsBuilder.fromUriString(this.postLogoutRedirectUri)
            .buildAndExpand(uriVariables)
            .toUriString();
        // @formatter:on
    }


    public void setPostLogoutRedirectUri(String postLogoutRedirectUri) {
        Assert.notNull(postLogoutRedirectUri, "postLogoutRedirectUri cannot be null");
        this.postLogoutRedirectUri = postLogoutRedirectUri;
    }


}


