package com.banco.core.infrastructure.core;

import com.banco.core.domain.TransactionEvent;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Component
@Slf4j
public class CoreBankingClient {

    private static final String CIRCUIT_BREAKER_NAME = "coreBanking";
    private static final String RETRY_NAME = "coreBanking";

    private final RestTemplate restTemplate;
    private final CircuitBreakerRegistry circuitBreakerRegistry;
    private final RetryRegistry retryRegistry;

    @Value("${core.banking.base-url:http://localhost:8080}")
    private String baseUrl;

    @Value("${core.banking.timeout-ms:5000}")
    private int timeoutMs;

    @Value("${core.banking.endpoints.transactions:/api/transactions}")
    private String transactionsEndpoint;

    @Value("${core.banking.endpoints.accounts:/api/accounts}")
    private String accountsEndpoint;

    @Value("${core.banking.endpoints.balance:/api/accounts/{accountId}/balance}")
    private String balanceEndpoint;

    public CoreBankingClient(RestTemplate restTemplate,
                             CircuitBreakerRegistry circuitBreakerRegistry,
                             RetryRegistry retryRegistry) {
        this.restTemplate = restTemplate;
        this.circuitBreakerRegistry = circuitBreakerRegistry;
        this.retryRegistry = retryRegistry;
    }

    public TransactionEvent fetchTransaction(String transactionId) {
        String url = buildUrl(transactionsEndpoint) + "/" + transactionId;
        return executeFetchTransaction(url, transactionId);
    }

    private TransactionEvent executeFetchTransaction(String url, String transactionId) {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(CIRCUIT_BREAKER_NAME);
        Retry retry = retryRegistry.retry(RETRY_NAME);

        Supplier<TransactionEvent> decoratedSupplier = CircuitBreaker.decorateSupplier(
                circuitBreaker,
                Retry.decorateSupplier(
                        retry,
                        () -> {
                            log.debug("Fetching transaction from Core Banking: {}", transactionId);
                            return restTemplate.getForObject(url, TransactionEvent.class);
                        }
                )
        );

        return decoratedSupplier.get();
    }

    public List<TransactionEvent> fetchRecentTransactions(String accountId, int limit) {
        String url = buildUrl(accountsEndpoint) + "/" + accountId + "/transactions?limit=" + limit;
        return executeFetchRecentTransactions(url);
    }

    private List<TransactionEvent> executeFetchRecentTransactions(String url) {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(CIRCUIT_BREAKER_NAME);

        Supplier<List<TransactionEvent>> decoratedSupplier = CircuitBreaker.decorateSupplier(
                circuitBreaker,
                () -> {
                    log.debug("Fetching recent transactions from: {}", url);
                    TransactionEvent[] response = restTemplate.getForObject(url, TransactionEvent[].class);
                    return response != null ? List.of(response) : new ArrayList<>();
                }
        );

        return decoratedSupplier.get();
    }

    public void validateAccount(String accountId) {
        String url = buildUrl(accountsEndpoint) + "/" + accountId + "/validate";
        executeValidateAccount(url);
    }

    private Boolean executeValidateAccount(String url) {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(CIRCUIT_BREAKER_NAME);

        Supplier<Boolean> decoratedSupplier = CircuitBreaker.decorateSupplier(
                circuitBreaker,
                () -> {
                    log.debug("Validating account: {}", extractAccountIdFromUrl(url));
                    return restTemplate.getForObject(url, Boolean.class);
                }
        );

        return decoratedSupplier.get();
    }

    public Double getAccountBalance(String accountId) {
        String url = buildUrl(balanceEndpoint).replace("{accountId}", accountId);
        return executeGetAccountBalance(url);
    }

    private Double executeGetAccountBalance(String url) {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(CIRCUIT_BREAKER_NAME);

        Supplier<Double> decoratedSupplier = CircuitBreaker.decorateSupplier(
                circuitBreaker,
                () -> {
                    log.debug("Getting account balance from: {}", url);
                    return restTemplate.getForObject(url, Double.class);
                }
        );

        return decoratedSupplier.get();
    }

    private String buildUrl(String endpoint) {
        return baseUrl + endpoint;
    }

    private String extractAccountIdFromUrl(String url) {
        String[] parts = url.split("/");
        for (int i = 0; i < parts.length; i++) {
            if ("accounts".equals(parts[i]) && i + 1 < parts.length) {
                return parts[i + 1];
            }
        }
        return "unknown";
    }

    public boolean isCircuitBreakerOpen() {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(CIRCUIT_BREAKER_NAME);
        return circuitBreaker.getState() == CircuitBreaker.State.OPEN;
    }

    public CircuitBreaker.Metrics getCircuitBreakerMetrics() {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(CIRCUIT_BREAKER_NAME);
        return circuitBreaker.getMetrics();
    }

    public String sendTransactionConfirmation(TransactionEvent event) {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(CIRCUIT_BREAKER_NAME);
        Retry retry = retryRegistry.retry(RETRY_NAME);

        Supplier<String> decoratedSupplier = CircuitBreaker.decorateSupplier(
                circuitBreaker,
                Retry.decorateSupplier(
                        retry,
                        () -> {
                            log.info("Sending transaction confirmation to Core Banking. TransactionId: {}",
                                    event.getTransactionId());
                            String url = buildUrl(transactionsEndpoint) + "/" + event.getTransactionId() + "/confirm";
                            restTemplate.postForObject(url, event, String.class);
                            return "CONFIRMED";
                        }
                )
        );

        return decoratedSupplier.get();
    }

    public static class CoreBankingException extends RuntimeException {
        public CoreBankingException(String message) {
            super(message);
        }

        public CoreBankingException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}