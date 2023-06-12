package org.eai.domain.services;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import io.quarkus.panache.common.Page;
import org.jboss.logging.Logger;

import org.eai.database.repositories.PurchaseRepository;

import org.eai.database.utils.PagedResult;

import org.eai.domain.dto.PurchaseDto;
import org.eai.domain.mappers.PurchaseMappers;
import org.eai.domain.utils.ConstantsDomain;
import org.eai.domain.utils.EnumStatus;
import org.eai.domain.utils.Utilities;

import org.eai.entities.models.PurchaseEntity;

import org.eai.web.models.request.PurchaseRequest;

import org.eai.domain.mappers.PurchaseDatesAndStoreMappers;
import org.eai.web.models.request.PurchaseDateAndStoreRequest;

@ApplicationScoped
public class PurchaseService {

    private static final Logger LOGGER = Logger.getLogger(PurchaseService.class.getName());

    @Inject
    PurchaseRepository purchaseRepository;

    @Inject
    PurchaseMappers purchaseMappers;

    @Inject
    PurchaseDatesAndStoreMappers purchaseDatesAndStoreMappers;

    public List<PurchaseDto> getAllPurchases(){
        LOGGER.infof(ConstantsDomain.LOG_SERVICE_GET_ALL_PURCHASES);
        return purchaseMappers.purchaseRequestListToPurchaseEntityList(purchaseRepository.listAll());
    }

    public PagedResult<PurchaseDto> getPurchasesPaginated(Page page) {
        LOGGER.infof(ConstantsDomain.LOG_SERVICE_GET_PAGE_RESULT, page);
        PagedResult<PurchaseDto> result = new PagedResult<>();
        PagedResult<PurchaseEntity> purchases = purchaseRepository.findPurchasesPaginated(page);
        List<PurchaseDto> purchaseDtoList = purchases.getList().stream()
                .map(purchaseEntity -> purchaseMappers.purchaseEntityToPurchaseDto(purchaseEntity)).collect(Collectors.toList());
        result.setList(purchaseDtoList);
        result.setNumOfResults(purchases.getNumOfResults());
        result.setPage(purchases.getPage());
        result.setSize(purchases.getSize());
        return result;
    }

    public Long addPurchase(PurchaseRequest purchaseRequest){
        LOGGER.infof(ConstantsDomain.LOG_SERVICE_ADD_PURCHASE, purchaseRequest);
        PurchaseEntity purchaseEntity = addAttributesPurchaseEntity(purchaseMappers.purchaseRequestToPurchaseEntity(purchaseRequest));
        purchaseRepository.persist(purchaseEntity);
        return purchaseEntity.getId();
    }

    public PurchaseDto getPurchaseById(Long id){
        LOGGER.infof(ConstantsDomain.LOG_SERVICE_GET_PURCHASE_BY_ID, id);
        Optional<PurchaseEntity> purchaseEntity = purchaseRepository.findByIdOptional(id);
        if(purchaseEntity.isPresent()){
            PurchaseEntity purchase = purchaseEntity.get();
            return purchaseMappers.purchaseEntityToPurchaseDto(purchase);
        }
        else{
            return null;
        }
    }

    public List<PurchaseDto> getPurchaseByStore(String store){
        LOGGER.infof(ConstantsDomain.LOG_SERVICE_GET_PURCHASE_BY_STORE, store);
        List<PurchaseEntity> purchaseEntityList = purchaseRepository.findPurchaseListByStore(store);
        return purchaseMappers.purchaseRequestListToPurchaseEntityList(purchaseEntityList);
    }

    public List<PurchaseDto> getPurchaseByDateAndStore(PurchaseDateAndStoreRequest purchaseDateAndStoreRequest){
        LOGGER.infof(ConstantsDomain.LOG_SERVICE_GET_PURCHASE_BETWEEN_DATE_AND_STORE, purchaseDateAndStoreRequest);
        List<PurchaseEntity> purchaseEntityList = purchaseRepository.findPurchaseListBetweenDatesAndStore(
                                                        purchaseDatesAndStoreMappers.toDto(purchaseDateAndStoreRequest));
        return purchaseMappers.purchaseRequestListToPurchaseEntityList(purchaseEntityList);
    }

    public PurchaseDto updatePurchase(PurchaseRequest purchaseRequest, Long id) {
        LOGGER.infof(ConstantsDomain.LOG_SERVICE_UPDATE_PURCHASE, purchaseRequest);
        Optional<PurchaseEntity> purchaseOptional = purchaseRepository.findByIdOptional(id);
        if(purchaseOptional.isPresent()){
            PurchaseEntity purchaseEntity = purchaseOptional.get();
            purchaseRepository.persist(updateAttributesPurchaseEntity(purchaseRequest, purchaseEntity));
            return purchaseMappers.purchaseEntityToPurchaseDto(purchaseEntity);
        }
        else{
            return null;
        }
    }

    public boolean deletePurchaseById(Long id){
        LOGGER.infof(ConstantsDomain.LOG_SERVICE_DELETE_PURCHASE_BY_ID, id);
        return purchaseRepository.deleteById(id);
    }

    private PurchaseEntity addAttributesPurchaseEntity(PurchaseEntity purchaseEntity){
        OffsetDateTime now = Utilities.getOffsetDateTimeNow();

        purchaseEntity.setCreationDate(now);
        purchaseEntity.setStatus(EnumStatus.ALTA.toString());
        return purchaseEntity;
    }

    private PurchaseEntity updateAttributesPurchaseEntity(PurchaseRequest purchaseRequest, PurchaseEntity purchaseEntity){
        OffsetDateTime now = Utilities.getOffsetDateTimeNow();

        purchaseEntity.setDatePurchase(purchaseRequest.getDatePurchase());
        purchaseEntity.setStore(purchaseRequest.getStore());
        purchaseEntity.setProduct(purchaseRequest.getProduct());
        purchaseEntity.setCost(purchaseRequest.getCost());

        purchaseEntity.setModificationDate(now);
        purchaseEntity.setStatus(EnumStatus.MODIF.toString());
        return purchaseEntity;
    }

}
