package org.eai.database.repositories;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.TypedQuery;

import io.quarkus.panache.common.Page;

import org.eai.database.utils.DatabaseConstants;
import org.eai.database.utils.PagedResult;
import org.eai.domain.dto.PurchaseDatesAndStoreDto;
import org.eai.entities.models.PurchaseEntity;

import java.util.List;

@ApplicationScoped
public class PurchaseRepository implements PanacheRepository<PurchaseEntity> {

    public PagedResult<PurchaseEntity> findPurchasesPaginated(Page page) {
        PanacheQuery<PurchaseEntity> query = find(DatabaseConstants.FIND_PURCHASES);
        query.page(page);
        return PagedResult.of(query);
    }

    public List<PurchaseEntity> findPurchaseListByStore(String store){
        TypedQuery<PurchaseEntity> query = getEntityManager().createNamedQuery(DatabaseConstants.FIND_PURCHASE_BY_STORE, PurchaseEntity.class);
        query.setParameter(DatabaseConstants.COLUMN_STORE, store);
        return query.getResultList();
    }

    public List<PurchaseEntity> findPurchaseListBetweenDatesAndStore(PurchaseDatesAndStoreDto purchaseDatesAndStoreDto){
        TypedQuery<PurchaseEntity> query = getEntityManager().createNamedQuery(DatabaseConstants.FIND_PURCHASE_BY_DATEPRUCHASE_AND_STORE, PurchaseEntity.class);
        query.setParameter(DatabaseConstants.COLUMN_DATE_PURCHASE, purchaseDatesAndStoreDto.getDatePurchase());
        query.setParameter(DatabaseConstants.COLUMN_STORE, purchaseDatesAndStoreDto.getStore());
        return query.getResultList();
    }
}
