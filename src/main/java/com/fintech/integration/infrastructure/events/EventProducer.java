package com.fintech.integration.infrastructure.events;

import com.fintech.integration.domain.Event;
import reactor.core.publisher.Mono;

public interface EventProducer {

    Mono<Void> send(Event event);

    Mono<Void> sendWithKey(Event event, String key);

    Mono<Boolean> isAvailable();

    String getTopic();

    default Mono<Event> publish(Event event) {
        return send(event).thenReturn(event);
    }
}