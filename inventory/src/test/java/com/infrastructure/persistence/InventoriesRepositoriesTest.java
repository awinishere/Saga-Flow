package com.infrastructure.persistence;

import com.domain.Inventories;
import com.domain.extensions.InventoriesStatus;
import io.quarkus.test.TestReactiveTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.vertx.RunOnVertxContext;
import io.quarkus.test.vertx.UniAsserter;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class InventoriesRepositoriesTest {

    @Inject
    InventoriesRepositories repositories;

    @Test
    @RunOnVertxContext
    @TestReactiveTransaction
    void shouldFindInventoryByProductId(UniAsserter asserter) {
        UUID productId = UUID.randomUUID();

        Inventories inventory = new Inventories(
                productId,
                10
        );

        asserter.execute(() ->
                repositories.persist(inventory)
        );

        asserter.assertThat(
                () -> repositories.findByProductId(productId),
                result -> {
                    assertNotNull(result);
                    assertEquals(productId, result.getProductId());
                    assertEquals(10, result.getQuantity());
                    assertEquals(
                            InventoriesStatus.Active,
                            result.getStatus()
                    );
                }
        );
    }

    @Test
    @RunOnVertxContext
    @TestReactiveTransaction
    void shouldReturnNullWhenProductIdDoesNotExist(
            UniAsserter asserter
    ) {
        UUID productId = UUID.randomUUID();

        asserter.assertThat(
                () -> repositories.findByProductId(productId),
                result -> assertNull(result)
        );
    }
}