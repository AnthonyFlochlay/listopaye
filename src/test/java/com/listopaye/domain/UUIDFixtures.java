package com.listopaye.domain;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class UUIDFixtures {

    private static final ConcurrentMap<String, UUID> uuids = new ConcurrentHashMap<>();

    public static UUID uuidOf(String id) {
        UUID newUuid = UUID.randomUUID();
        UUID oldUuid = uuids.putIfAbsent(id, newUuid);
        return oldUuid == null
                ? newUuid
                : oldUuid;
    }
}
