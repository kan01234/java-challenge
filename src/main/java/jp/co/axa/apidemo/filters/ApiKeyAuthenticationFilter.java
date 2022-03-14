package jp.co.axa.apidemo.filters;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.util.pattern.PathPatternParser;
import reactor.core.publisher.Mono;

@Component
public class ApiKeyAuthenticationFilter implements WebFilter {

  /** constant key of api header */
  public static final String X_API_KEY_HEADER = "X-API-Key";

  /** api key */
  private final String apiKey;

  public ApiKeyAuthenticationFilter(@Value("${self.api.key}") String apiKey) {
    this.apiKey = apiKey;
  }

  private static final PathPatternParser parser = new PathPatternParser();

  private static final PathPatternParserServerWebExchangeMatcher matcher =
      new PathPatternParserServerWebExchangeMatcher(parser.parse("/api/**"));

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    return matcher
        .matches(exchange)
        .flatMap(
            matchResult -> {
              if (matchResult.isMatch()) {
                String apiKeyHeader = exchange.getRequest().getHeaders().getFirst(X_API_KEY_HEADER);

                if (!apiKey.equals(apiKeyHeader)) {
                  throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid API key");
                }
              }

              return chain.filter(exchange);
            });
  }
}
