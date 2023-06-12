package org.eai.domain.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

import org.eai.domain.dto.PurchaseDto;
import org.eai.entities.models.PurchaseEntity;
import org.eai.web.models.request.PurchaseRequest;

@Mapper(componentModel = "cdi")
public interface PurchaseMappers {

    @Mapping(target = "id", expression = "java(purchaseEntity.getId())")
    PurchaseDto purchaseEntityToPurchaseDto(PurchaseEntity purchaseEntity);

    default List<PurchaseDto> purchaseRequestListToPurchaseEntityList(List<PurchaseEntity> purchaseEntityList){
        List<PurchaseDto> purchaseDtoList = new ArrayList<>();
        for(PurchaseEntity entity: purchaseEntityList){
            PurchaseDto dto = purchaseEntityToPurchaseDto(entity);
            purchaseDtoList.add(dto);
        }
        return purchaseDtoList;
    }

    default PurchaseEntity purchaseRequestToPurchaseEntity(PurchaseRequest purchaseRequest){

        PurchaseEntity purchaseEntity = new PurchaseEntity();
        purchaseEntity.setDatePurchase(purchaseRequest.getDatePurchase());
        purchaseEntity.setStore(purchaseRequest.getStore());
        purchaseEntity.setProduct(purchaseRequest.getProduct());
        purchaseEntity.setCost(purchaseRequest.getCost());
        return purchaseEntity;
    }
}
