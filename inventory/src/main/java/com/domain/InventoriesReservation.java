package com.domain;

import com.domain.extensions.InventoriesReservationStatus;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "inventories_reservations",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_inventories_reservation_order",
                        columnNames = "order_id"
                )
        }
)
public class InventoriesReservation extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    @Column(name = "inventories_id", nullable = false)
    private UUID inventoriesId;

    @Column(nullable = false)
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InventoriesReservationStatus status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    protected InventoriesReservation() {
    }

    public InventoriesReservation(
            UUID orderId,
            UUID inventoriesId,
            Integer quantity
    ) {
        this.orderId = orderId;
        this.inventoriesId = inventoriesId;
        this.quantity = quantity;
        this.status = InventoriesReservationStatus.Reserved;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public UUID getInventoriesId() {
        return inventoriesId;
    }

    public void setInventoriesId(UUID inventoriesId) {
        this.inventoriesId = inventoriesId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public InventoriesReservationStatus getStatus() {
        return status;
    }

    public void setStatus(InventoriesReservationStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}