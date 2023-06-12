package org.eai.database.resourcepanache;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheRepositoryResource;
import io.quarkus.rest.data.panache.ResourceProperties;

import org.eai.database.repositories.PurchaseRepository;
import org.eai.entities.models.PurchaseEntity;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@ResourceProperties(exposed = false)
public interface PurchaseResourcePanache extends PanacheRepositoryResource<PurchaseRepository, PurchaseEntity, Long> {
}
