package com.fintech.integration.domain;

import java.time.Instant;
import java.util.UUID;

public record Event(
    String eventId,
    String operationNumber,
    String channel,
    String eventType,
    Instant timestamp,
    int retryCount
) {
    public static Event create(final String operationNumber, final String channel) {
        return new Event(
            UUID.randomUUID().toString(),
            operationNumber,
            channel,
            "DEFAULT",
            Instant.now(),
            0
        );
    }

    public String getIdempotencyKeyValue() {
        return operationNumber + "|" + channel;
    }

    public boolean isDuplicateOf(final Event other) {
        if (other == null) return false;
        return this.operationNumber.equals(other.operationNumber) 
            && this.channel.equals(other.channel);
    }

    public Event withUpdatedTimestamp() {
        return new Event(
            this.eventId,
            this.operationNumber,
            this.channel,
            this.eventType,
            Instant.now(),
            this.retryCount
        );
    }

    public String getEventId() {
        return eventId;
    }

    public String getEventType() {
        return eventType;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public Event withRetryCount(final int newRetryCount) {
        return new Event(
            this.eventId,
            this.operationNumber,
            this.channel,
            this.eventType,
            this.timestamp,
            newRetryCount
        );
    }
}