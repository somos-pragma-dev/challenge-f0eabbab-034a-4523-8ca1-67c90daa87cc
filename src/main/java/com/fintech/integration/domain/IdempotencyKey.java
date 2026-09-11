package com.fintech.integration.domain;

import java.util.Objects;

public final class IdempotencyKey {

    private final String operationNumber;
    private final String channel;
    private final String compositeKey;

    public IdempotencyKey(final String operationNumber, final String channel) {
        Objects.requireNonNull(operationNumber, "operationNumber cannot be null");
        Objects.requireNonNull(channel, "channel cannot be null");
        this.operationNumber = operationNumber;
        this.channel = channel;
        this.compositeKey = buildCompositeKey(operationNumber, channel);
    }

    private static String buildCompositeKey(final String operationNumber, final String channel) {
        return operationNumber.trim().toUpperCase() + "|" + channel.trim().toUpperCase();
    }

    public String getOperationNumber() {
        return operationNumber;
    }

    public String getChannel() {
        return channel;
    }

    public String getCompositeKey() {
        return compositeKey;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final IdempotencyKey that = (IdempotencyKey) o;
        return Objects.equals(compositeKey, that.compositeKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(compositeKey);
    }

    @Override
    public String toString() {
        return compositeKey;
    }

    public int compareTo(final IdempotencyKey other) {
        if (other == null) {
            throw new NullPointerException("Cannot compare with null IdempotencyKey");
        }
        return this.compositeKey.compareTo(other.compositeKey);
    }

    public boolean isSameOperation(final String operationNumber, final String channel) {
        if (operationNumber == null || channel == null) {
            return false;
        }
        return this.compositeKey.equals(buildCompositeKey(operationNumber, channel));
    }
}