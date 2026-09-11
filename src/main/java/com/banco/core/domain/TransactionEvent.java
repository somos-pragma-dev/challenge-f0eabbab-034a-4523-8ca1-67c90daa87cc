package com.banco.core.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class TransactionEvent {

    @NotBlank(message = "El eventId es obligatorio")
    private String eventId;

    @NotBlank(message = "El transactionId es obligatorio")
    private String transactionId;

    @NotBlank(message = "El eventType es obligatorio")
    private String eventType;

    @NotBlank(message = "El accountId es obligatorio")
    private String accountId;

    @NotNull(message = "El amount es obligatorio")
    @Positive(message = "El amount debe ser positivo")
    private Double amount;

    @NotBlank(message = "El currency es obligatorio")
    private String currency;

    @NotBlank(message = "El transactionType es obligatorio")
    private String transactionType;

    @NotBlank(message = "El timestamp es obligatorio")
    private String timestamp;

    private String correlationId;

    @NotBlank(message = "El idempotencyKey es obligatorio")
    private String idempotencyKey;

    private String description;

    private String sourceSystem;

    private Map<String, Object> metadata;

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public void setIdempotencyKey(String idempotencyKey) {
        this.idempotencyKey = idempotencyKey;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSourceSystem(String sourceSystem) {
        this.sourceSystem = sourceSystem;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    public boolean isDebit() {
        return "DEBIT".equalsIgnoreCase(this.transactionType);
    }

    public boolean isCredit() {
        return "CREDIT".equalsIgnoreCase(this.transactionType);
    }

    public String getBusinessKey() {
        return String.format("%s:%s:%s", this.accountId, this.transactionId, this.idempotencyKey);
    }

    public void addMetadata(String key, Object value) {
        if (this.metadata == null) {
            this.metadata = new HashMap<>();
        }
        this.metadata.put(key, value);
    }

    public Object getMetadata(String key) {
        return this.metadata != null ? this.metadata.get(key) : null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionEvent that = (TransactionEvent) o;
        return Objects.equals(eventId, that.eventId) && 
               Objects.equals(transactionId, that.transactionId) &&
               Objects.equals(idempotencyKey, that.idempotencyKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, transactionId, idempotencyKey);
    }

    @Override
    public String toString() {
        return String.format("TransactionEvent{eventId='%s', transactionId='%s', eventType='%s', " +
                "accountId='%s', amount=%s, currency='%s', transactionType='%s', correlationId='%s', " +
                "idempotencyKey='%s'}",
                eventId, transactionId, eventType, accountId, amount, currency, 
                transactionType, correlationId, idempotencyKey);
    }

    public static TransactionEventBuilder builder() {
        return new TransactionEventBuilder();
    }

    public static class TransactionEventBuilder {
        private String eventId;
        private String transactionId;
        private String eventType = "TRANSACTION_CREATED";
        private String accountId;
        private Double amount;
        private String currency = "USD";
        private String transactionType;
        private String timestamp = Instant.now().toString();
        private String correlationId;
        private String idempotencyKey;
        private String description;
        private String sourceSystem = "CORE_BANKING";
        private Map<String, Object> metadata = new HashMap<>();

        public TransactionEventBuilder eventId(String eventId) {
            this.eventId = eventId;
            return this;
        }

        public TransactionEventBuilder transactionId(String transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public TransactionEventBuilder eventType(String eventType) {
            this.eventType = eventType;
            return this;
        }

        public TransactionEventBuilder accountId(String accountId) {
            this.accountId = accountId;
            return this;
        }

        public TransactionEventBuilder amount(Double amount) {
            this.amount = amount;
            return this;
        }

        public TransactionEventBuilder currency(String currency) {
            this.currency = currency;
            return this;
        }

        public TransactionEventBuilder transactionType(String transactionType) {
            this.transactionType = transactionType;
            return this;
        }

        public TransactionEventBuilder timestamp(String timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public TransactionEventBuilder correlationId(String correlationId) {
            this.correlationId = correlationId;
            return this;
        }

        public TransactionEventBuilder idempotencyKey(String idempotencyKey) {
            this.idempotencyKey = idempotencyKey;
            return this;
        }

        public TransactionEventBuilder description(String description) {
            this.description = description;
            return this;
        }

        public TransactionEventBuilder sourceSystem(String sourceSystem) {
            this.sourceSystem = sourceSystem;
            return this;
        }

        public TransactionEventBuilder metadata(Map<String, Object> metadata) {
            this.metadata = metadata;
            return this;
        }

        public TransactionEvent build() {
            TransactionEvent event = new TransactionEvent();
            event.setEventId(this.eventId);
            event.setTransactionId(this.transactionId);
            event.setEventType(this.eventType);
            event.setAccountId(this.accountId);
            event.setAmount(this.amount);
            event.setCurrency(this.currency);
            event.setTransactionType(this.transactionType);
            event.setTimestamp(this.timestamp);
            event.setCorrelationId(this.correlationId);
            event.setIdempotencyKey(this.idempotencyKey);
            event.setDescription(this.description);
            event.setSourceSystem(this.sourceSystem);
            event.setMetadata(this.metadata);
            return event;
        }
    }
}