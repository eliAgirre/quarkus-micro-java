package org.eai.domain.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import org.eai.domain.dto.PurchaseDatesAndStoreDto;
import org.eai.web.models.request.PurchaseDateAndStoreRequest;

@Mapper(componentModel = "cdi")
public interface PurchaseDatesAndStoreMappers {

    @Mapping(target = "datePurchase", expression = "java(purchaseDateAndStoreRequest.getDatePurchase())")
    PurchaseDatesAndStoreDto toDto(PurchaseDateAndStoreRequest purchaseDateAndStoreRequest);
}
