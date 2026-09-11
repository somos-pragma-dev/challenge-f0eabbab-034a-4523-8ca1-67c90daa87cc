package com.fintech.integration.infrastructure.core;

import com.fintech.integration.domain.Event;
import com.fintech.integration.domain.IdempotencyKey;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface EventRepository {

    Mono<Boolean> existsByIdempotencyKey(IdempotencyKey key);

    Mono<Event> save(Event event);

    Mono<Optional<Event>> findByIdempotencyKey(IdempotencyKey key);

    Mono<List<Event>> findAll();

    Mono<List<Event>> findByChannel(String channel);

    Mono<List<Event>> findByEventType(String eventType);

    Mono<List<Event>> findByTimestampBetween(Instant start, Instant end);

    Mono<Long> count();

    Mono<Boolean> deleteByIdempotencyKey(IdempotencyKey key);

    Mono<Void> deleteAll();

    Mono<List<Event>> findByRetryCountLessThan(int maxRetries);

    Mono<Event> incrementRetryCount(Event event);
}