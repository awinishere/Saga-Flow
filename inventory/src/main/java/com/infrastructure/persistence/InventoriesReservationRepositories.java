package com.infrastructure.persistence;

import com.domain.InventoriesReservation;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class InventoriesReservationRepositories
    implements PanacheRepositoryBase<InventoriesReservation, UUID>
{
    public Uni<InventoriesReservation> findByOrderId(UUID orderId) {
        return find(
                "SELECT inventoriesReservation " +
                        "FROM InventoriesReservation inventoriesReservation " +
                        "WHERE inventoriesReservation.orderId = ?1",
                orderId
        ).firstResult();
    }
}
