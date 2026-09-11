package com.fintech.integration.infrastructure.core;

import com.fintech.integration.domain.Event;
import com.fintech.integration.domain.IdempotencyKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class InMemoryEventRepository implements EventRepository {

    private static final Logger log = LoggerFactory.getLogger(InMemoryEventRepository.class);

    private final Map<String, Event> eventsById = new ConcurrentHashMap<>();
    private final Map<String, Event> eventsByIdempotencyKey = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Mono<Boolean> existsByIdempotencyKey(final IdempotencyKey key) {
        if (key == null || key.getCompositeKey() == null) {
            log.warn("IdempotencyKey inválida proporcionada");
            return Mono.just(false);
        }
        final boolean exists = eventsByIdempotencyKey.containsKey(key.getCompositeKey());
        log.debug("Verificando existencia de clave idempotente {}: {}", key.getCompositeKey(), exists);
        return Mono.just(exists);
    }

    @Override
    public Mono<Event> save(final Event event) {
        if (event == null) {
            log.error("Intento de guardar evento nulo");
            return Mono.error(new IllegalArgumentException("El evento no puede ser nulo"));
        }

        final String idempotencyKeyValue = event.getIdempotencyKeyValue();
        final Event existingEvent = eventsByIdempotencyKey.get(idempotencyKeyValue);

        if (existingEvent != null) {
            log.info("Evento duplicado detectado para clave idempotente: {}", idempotencyKeyValue);
            return Mono.just(existingEvent);
        }

        final String eventId = "EVT-" + idGenerator.getAndIncrement();
        final Event eventToSave = new Event(
            eventId,
            event.operationNumber(),
            event.channel(),
            event.eventType(),
            event.payload(),
            event.timestamp(),
            event.idempotencyKey(),
            0,
            event.metadata()
        );

        eventsById.put(eventId, eventToSave);
        eventsByIdempotencyKey.put(idempotencyKeyValue, eventToSave);

        log.info("Evento guardado exitosamente con ID: {}, clave idempotente: {}", 
            eventId, idempotencyKeyValue);
        return Mono.just(eventToSave);
    }

    @Override
    public Mono<Optional<Event>> findByIdempotencyKey(final IdempotencyKey key) {
        if (key == null || key.getCompositeKey() == null) {
            return Mono.just(Optional.empty());
        }
        final Event event = eventsByIdempotencyKey.get(key.getCompositeKey());
        return Mono.just(Optional.ofNullable(event));
    }

    @Override
    public Mono<List<Event>> findAll() {
        final List<Event> allEvents = eventsById.values().stream()
            .sorted((e1, e2) -> e2.timestamp().compareTo(e1.timestamp()))
            .collect(Collectors.toList());
        log.debug("Recuperados {} eventos en total", allEvents.size());
        return Mono.just(allEvents);
    }

    @Override
    public Mono<List<Event>> findByChannel(final String channel) {
        if (channel == null || channel.isBlank()) {
            return Mono.just(List.of());
        }
        final List<Event> events = eventsById.values().stream()
            .filter(e -> channel.equals(e.channel()))
            .sorted((e1, e2) -> e2.timestamp().compareTo(e1.timestamp()))
            .collect(Collectors.toList());
        log.debug("Recuperados {} eventos para el canal: {}", events.size(), channel);
        return Mono.just(events);
    }

    @Override
    public Mono<List<Event>> findByEventType(final String eventType) {
        if (eventType == null || eventType.isBlank()) {
            return Mono.just(List.of());
        }
        final List<Event> events = eventsById.values().stream()
            .filter(e -> eventType.equals(e.eventType()))
            .sorted((e1, e2) -> e2.timestamp().compareTo(e1.timestamp()))
            .collect(Collectors.toList());
        log.debug("Recuperados {} eventos para el tipo: {}", events.size(), eventType);
        return Mono.just(events);
    }

    @Override
    public Mono<List<Event>> findByTimestampBetween(final Instant start, final Instant end) {
        if (start == null || end == null || start.isAfter(end)) {
            log.warn("Rango de tiempo inválido: start={}, end={}", start, end);
            return Mono.just(List.of());
        }
        final List<Event> events = eventsById.values().stream()
            .filter(e -> !e.timestamp().isBefore(start) && !e.timestamp().isAfter(end))
            .sorted((e1, e2) -> e2.timestamp().compareTo(e1.timestamp()))
            .collect(Collectors.toList());
        log.debug("Recuperados {} eventos entre {} y {}", events.size(), start, end);
        return Mono.just(events);
    }

    @Override
    public Mono<Long> count() {
        final long count = eventsById.size();
        log.debug("Total de eventos en repositorio: {}", count);
        return Mono.just(count);
    }

    @Override
    public Mono<Boolean> deleteByIdempotencyKey(final IdempotencyKey key) {
        if (key == null || key.getCompositeKey() == null) {
            return Mono.just(false);
        }
        final Event removed = eventsByIdempotencyKey.remove(key.getCompositeKey());
        if (removed != null) {
            eventsById.remove(removed.getEventId());
            log.info("Evento eliminado para clave idempotente: {}", key.getCompositeKey());
            return Mono.just(true);
        }
        return Mono.just(false);
    }

    @Override
    public Mono<Void> deleteAll() {
        eventsById.clear();
        eventsByIdempotencyKey.clear();
        log.info("Todos los eventos eliminados del repositorio");
        return Mono.empty();
    }

    @Override
    public Mono<List<Event>> findByRetryCountLessThan(final int maxRetries) {
        if (maxRetries < 0) {
            return Mono.just(List.of());
        }
        final List<Event> events = eventsById.values().stream()
            .filter(e -> e.retryCount() < maxRetries)
            .sorted((e1, e2) -> e1.timestamp().compareTo(e2.timestamp()))
            .collect(Collectors.toList());
        log.debug("Recuperados {} eventos con reintentos menores a {}", events.size(), maxRetries);
        return Mono.just(events);
    }

    @Override
    public Mono<Event> incrementRetryCount(final Event event) {
        if (event == null) {
            return Mono.error(new IllegalArgumentException("El evento no puede ser nulo"));
        }
        final int newRetryCount = event.retryCount() + 1;
        final Event updatedEvent = new Event(
            event.getEventId(),
            event.operationNumber(),
            event.channel(),
            event.eventType(),
            event.payload(),
            event.timestamp(),
            event.idempotencyKey(),
            newRetryCount,
            event.metadata()
        );
        eventsById.put(event.getEventId(), updatedEvent);
        eventsByIdempotencyKey.put(event.getIdempotencyKeyValue(), updatedEvent);
        log.info("Incrementado retryCount para evento {}: {} -> {}", 
            event.getEventId(), event.retryCount(), newRetryCount);
        return Mono.just(updatedEvent);
    }
}