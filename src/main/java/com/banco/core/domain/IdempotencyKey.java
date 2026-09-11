package com.banco.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.Objects;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class IdempotencyKey {

    private String key;
    private String businessKey;
    private String eventId;
    private String transactionId;
    private Instant createdAt;
    private Instant expiresAt;
    private IdempotencyStatus status;
    private int retryCount;
    private String lastError;

    public enum IdempotencyStatus {
        PENDING,
        PROCESSING,
        COMPLETED,
        FAILED,
        DUPLICATE
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public void setStatus(IdempotencyStatus status) {
        this.status = status;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public void setLastError(String lastError) {
        this.lastError = lastError;
    }

    public boolean isExpired() {
        return expiresAt != null && Instant.now().isAfter(expiresAt);
    }

    public boolean isCompleted() {
        return status == IdempotencyStatus.COMPLETED;
    }

    public boolean isProcessing() {
        return status == IdempotencyStatus.PROCESSING;
    }

    public boolean isDuplicate() {
        return status == IdempotencyStatus.DUPLICATE;
    }

    public boolean canRetry() {
        return status == IdempotencyStatus.FAILED && retryCount < 3;
    }

    public void markAsProcessing() {
        this.status = IdempotencyStatus.PROCESSING;
        log.debug("Marcando clave de idempotencia {} como PROCESSING", this.key);
    }

    public void markAsCompleted() {
        this.status = IdempotencyStatus.COMPLETED;
        log.info("Clave de idempotencia {} marcada como COMPLETED", this.key);
    }

    public void markAsFailed(String error) {
        this.status = IdempotencyStatus.FAILED;
        this.lastError = error;
        this.retryCount++;
        log.warn("Clave de idempotencia {} marcada como FAILED. Error: {}. Reintentos: {}", 
                this.key, error, this.retryCount);
    }

    public void markAsDuplicate() {
        this.status = IdempotencyStatus.DUPLICATE;
        log.info("Clave de idempotencia {} detectada como DUPLICATE", this.key);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IdempotencyKey that = (IdempotencyKey) o;
        return Objects.equals(key, that.key) && Objects.equals(businessKey, that.businessKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, businessKey);
    }

    @Override
    public String toString() {
        return String.format("IdempotencyKey{key='%s', businessKey='%s', eventId='%s', " +
                "transactionId='%s', status=%s, retryCount=%d}",
                key, businessKey, eventId, transactionId, status, retryCount);
    }

    public static IdempotencyKeyBuilder builder() {
        return new IdempotencyKeyBuilder();
    }

    public static class IdempotencyKeyBuilder {
        private String key;
        private String businessKey;
        private String eventId;
        private String transactionId;
        private Instant createdAt = Instant.now();
        private Instant expiresAt;
        private IdempotencyStatus status = IdempotencyStatus.PENDING;
        private int retryCount = 0;
        private String lastError;

        public IdempotencyKeyBuilder key(String key) {
            this.key = key;
            return this;
        }

        public IdempotencyKeyBuilder businessKey(String businessKey) {
            this.businessKey = businessKey;
            return this;
        }

        public IdempotencyKeyBuilder eventId(String eventId) {
            this.eventId = eventId;
            return this;
        }

        public IdempotencyKeyBuilder transactionId(String transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public IdempotencyKeyBuilder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public IdempotencyKeyBuilder expiresAt(Instant expiresAt) {
            this.expiresAt = expiresAt;
            return this;
        }

        public IdempotencyKeyBuilder status(IdempotencyStatus status) {
            this.status = status;
            return this;
        }

        public IdempotencyKeyBuilder retryCount(int retryCount) {
            this.retryCount = retryCount;
            return this;
        }

        public IdempotencyKeyBuilder lastError(String lastError) {
            this.lastError = lastError;
            return this;
        }

        public IdempotencyKey build() {
            IdempotencyKey idempotencyKey = new IdempotencyKey();
            idempotencyKey.setKey(this.key);
            idempotencyKey.setBusinessKey(this.businessKey);
            idempotencyKey.setEventId(this.eventId);
            idempotencyKey.setTransactionId(this.transactionId);
            idempotencyKey.setCreatedAt(this.createdAt);
            idempotencyKey.setExpiresAt(this.expiresAt);
            idempotencyKey.setStatus(this.status);
            idempotencyKey.setRetryCount(this.retryCount);
            idempotencyKey.setLastError(this.lastError);
            return idempotencyKey;
        }
    }
}