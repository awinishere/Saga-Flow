package com.infrastructure.persistence;

import com.domain.InventoriesReservation;
import com.domain.extensions.InventoriesReservationStatus;
import io.quarkus.test.TestReactiveTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.vertx.RunOnVertxContext;
import io.quarkus.test.vertx.UniAsserter;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class InventoriesReservationRepositoriesTest {

    @Inject
    InventoriesReservationRepositories repositories;

    @Test
    @RunOnVertxContext
    @TestReactiveTransaction
    void shouldFindReservationByOrderId(UniAsserter asserter) {
        UUID orderId = UUID.randomUUID();
        UUID inventoriesId = UUID.randomUUID();

        InventoriesReservation reservation =
                new InventoriesReservation(
                        orderId,
                        inventoriesId,
                        2
                );

        asserter.execute(() ->
                repositories.persist(reservation)
        );

        asserter.assertThat(
                () -> repositories.findByOrderId(orderId),
                result -> {
                    assertNotNull(result);
                    assertEquals(orderId, result.getOrderId());
                    assertEquals(
                            inventoriesId,
                            result.getInventoriesId()
                    );
                    assertEquals(2, result.getQuantity());
                    assertEquals(
                            InventoriesReservationStatus.Reserved,
                            result.getStatus()
                    );
                }
        );
    }

    @Test
    @RunOnVertxContext
    @TestReactiveTransaction
    void shouldReturnNullWhenReservationDoesNotExist(
            UniAsserter asserter
    ) {
        UUID orderId = UUID.randomUUID();

        asserter.assertThat(
                () -> repositories.findByOrderId(orderId),
                result -> assertNull(result)
        );
    }
}