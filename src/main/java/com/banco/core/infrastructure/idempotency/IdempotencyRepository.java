package com.banco.core.infrastructure.idempotency;

import com.banco.core.domain.IdempotencyKey;
import com.banco.core.domain.IdempotencyKey.IdempotencyStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Repository
public class IdempotencyRepository {

    private static final Logger log = LoggerFactory.getLogger(IdempotencyRepository.class);
    private static final Duration DEFAULT_TTL = Duration.ofHours(24);
    private static final int MAX_RETRY_COUNT = 3;
    private static final long CLEANUP_INTERVAL_MINUTES = 15;

    private final Map<String, IdempotencyKey> storage;
    private final ScheduledExecutorService cleanupExecutor;
    private final Duration ttlDuration;

    public IdempotencyRepository() {
        this(DEFAULT_TTL);
    }

    public IdempotencyRepository(Duration ttlDuration) {
        this.storage = new ConcurrentHashMap<>();
        this.ttlDuration = ttlDuration != null ? ttlDuration : DEFAULT_TTL;
        this.cleanupExecutor = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "idempotency-cleanup");
            t.setDaemon(true);
            return t;
        });
        startCleanupTask();
        log.info("IdempotencyRepository inicializado con TTL de {} minutos", ttlDuration.toMinutes());
    }

    public IdempotencyKey save(IdempotencyKey idempotencyKey) {
        if (idempotencyKey == null) {
            throw new IllegalArgumentException("La clave de idempotencia no puede ser nula");
        }
        if (idempotencyKey.getKey() == null || idempotencyKey.getKey().isBlank()) {
            throw new IllegalArgumentException("La clave de idempotencia debe tener un valor válido");
        }

        String key = idempotencyKey.getKey();
        IdempotencyKey existing = storage.get(key);

        if (existing != null) {
            if (existing.isCompleted()) {
                log.debug("Clave de idempotencia {} ya procesada completamente", key);
                return existing;
            }
            if (existing.isProcessing()) {
                log.warn("Clave de idempotencia {} actualmente en procesamiento", key);
                return existing;
            }
            if (existing.canRetry() && existing.getRetryCount() < MAX_RETRY_COUNT) {
                existing.markAsProcessing();
                log.info("Reintentando clave de idempotencia {} (intento {})", key, existing.getRetryCount() + 1);
                return storage.put(key, existing);
            }
            log.warn("Clave de idempotencia {} excedió máximo de reintentos", key);
            return existing;
        }

        idempotencyKey.markAsProcessing();
        storage.put(key, idempotencyKey);
        log.info("Clave de idempotencia {} almacenada", key);
        return idempotencyKey;
    }

    public Optional<IdempotencyKey> findByKey(String key) {
        if (key == null || key.isBlank()) {
            return Optional.empty();
        }

        IdempotencyKey idempotencyKey = storage.get(key);
        if (idempotencyKey == null) {
            log.debug("No se encontró clave de idempotencia para: {}", key);
            return Optional.empty();
        }

        if (idempotencyKey.isExpired()) {
            log.info("Clave de idempotencia {} expirada, removiendo", key);
            storage.remove(key);
            return Optional.empty();
        }

        return Optional.of(idempotencyKey);
    }

    public boolean existsByKey(String key) {
        return findByKey(key).map(IdempotencyKey::isCompleted).orElse(false);
    }

    public boolean isDuplicate(String key) {
        return findByKey(key).map(ik -> ik.isDuplicate() || ik.isCompleted()).orElse(false);
    }

    public void markAsCompleted(String key) {
        findByKey(key).ifPresent(ik -> {
            ik.markAsCompleted();
            log.info("Clave de idempotencia {} marcada como completada", key);
        });
    }

    public void markAsFailed(String key, String error) {
        findByKey(key).ifPresent(ik -> {
            ik.markAsFailed(error);
            log.warn("Clave de idempotencia {} marcada como fallida: {}", key, error);
        });
    }

    public void markAsDuplicate(String key) {
        findByKey(key).ifPresent(ik -> {
            ik.markAsDuplicate();
            log.info("Clave de idempotencia {} marcada como duplicada", key);
        });
    }

    public void delete(String key) {
        if (key != null) {
            storage.remove(key);
            log.debug("Clave de idempotencia {} eliminada", key);
        }
    }

    public int count() {
        return storage.size();
    }

    public Map<String, IdempotencyKey> findAll() {
        return Map.copyOf(storage);
    }

    public Map<String, IdempotencyKey> findExpiredKeys() {
        return storage.entrySet().stream()
                .filter(e -> e.getValue().isExpired())
                .collect(java.util.stream.Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void cleanupExpired() {
        Map<String, IdempotencyKey> expired = findExpiredKeys();
        expired.keySet().forEach(storage::remove);
        log.info("Limpiadas {} claves de idempotencia expiradas", expired.size());
    }

    private void startCleanupTask() {
        cleanupExecutor.scheduleAtFixedRate(
                this::cleanupExpired,
                CLEANUP_INTERVAL_MINUTES,
                CLEANUP_INTERVAL_MINUTES,
                TimeUnit.MINUTES
        );
    }

    public void shutdown() {
        cleanupExecutor.shutdown();
        try {
            if (!cleanupExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
                cleanupExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            cleanupExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
        log.info("IdempotencyRepository cerrado");
    }
}