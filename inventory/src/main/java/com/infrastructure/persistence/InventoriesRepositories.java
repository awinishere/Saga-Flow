package com.infrastructure.persistence;

import com.domain.Inventories;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class InventoriesRepositories
    implements PanacheRepositoryBase<Inventories, UUID>
{
    public Uni<Inventories> findByProductId(UUID productId){
        return find("productId = ?1", productId)
                .firstResult();
    }
}
