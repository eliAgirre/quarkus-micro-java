package org.eai.domain.services;

import io.quarkus.test.junit.QuarkusTest;

import org.junit.jupiter.api.*;

import javax.inject.Inject;
import javax.transaction.Transactional;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.eai.domain.utils.EnumStatus;
import org.eai.domain.utils.TestConstants;
import org.eai.domain.dto.PurchaseDto;

import org.eai.utils.JsonToObjectsCreator;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PurchaseServiceTest extends JsonToObjectsCreator {

    @Inject
    PurchaseService purchaseService;

    @Test
    @Order(2)
    void WhenGetAllPurchases_ThenReturnsPurchaseEntityList(){
        List<PurchaseDto> purchaseDtoList = purchaseService.getAllPurchases();
        assertNotNull(purchaseDtoList);
        PurchaseDto purchaseDto = purchaseDtoList.get(0);
        assertNotNull(purchaseDtoList);
        assertEquals(1, purchaseDtoList.size());
        assertEquals(1, purchaseDto.getId().longValue());
        assertEquals(TestConstants.STORE_SUPERCOR, purchaseDto.getStore());
        assertEquals(TestConstants.PRODUCT_HUEVOS, purchaseDto.getProduct());
        assertEquals(TestConstants.COST, purchaseDto.getCost().doubleValue());
        assertEquals(EnumStatus.ALTA.toString(), purchaseDto.getStatus());
    }

    @Test
    @Order(1)
    @Transactional
    void GivenPurchaseRequest_WhenAddPurchase_ThenReturnsPurchaseId() throws IOException {
        Long purchaseId = purchaseService.addPurchase(purchaseRequest());
        assertNotNull(purchaseId);
        assertEquals(1, purchaseId);
    }

    @Test
    @Order(3)
    void GivenPurchaseIdNotExists_WhenGetPurchaseById_ThenReturnsPurchaseDto(){
        PurchaseDto purchaseDto = purchaseService.getPurchaseById(100L);
        assertNull(purchaseDto);
    }

    @Test
    @Order(4)
    void GivenPurchaseIdExists_WhenGetPurchaseById_ThenReturnsPurchaseDto(){
        PurchaseDto purchaseDto = purchaseService.getPurchaseById(1L);
        assertNotNull(purchaseDto);
        assertEquals(1, purchaseDto.getId().longValue());
        assertEquals(TestConstants.STORE_SUPERCOR, purchaseDto.getStore());
        assertEquals(TestConstants.PRODUCT_HUEVOS, purchaseDto.getProduct());
        assertEquals(TestConstants.COST, purchaseDto.getCost().doubleValue());
        assertEquals(EnumStatus.ALTA.toString(), purchaseDto.getStatus());
    }

    @Test
    @Order(5)
    void GivenStoreNotExists_WhenGetPurchaseByStore_ThenReturnsPurchaseDtoList(){
        List<PurchaseDto> purchaseDtoList = purchaseService.getPurchaseByStore(TestConstants.STORE_ALDI);
        assertNotNull(purchaseDtoList);
        assertEquals(0, purchaseDtoList.size());
    }

    @Test
    @Order(6)
    void GivenStoreExists_WhenGetPurchaseByStore_ThenReturnsPurchaseDtoList(){
        List<PurchaseDto> purchaseDtoList = purchaseService.getPurchaseByStore(TestConstants.STORE_SUPERCOR);
        assertNotNull(purchaseDtoList);
        PurchaseDto purchaseDto = purchaseDtoList.get(0);
        assertNotNull(purchaseDtoList);
        assertEquals(1, purchaseDtoList.size());
        assertEquals(1, purchaseDto.getId().longValue());
        assertEquals(TestConstants.STORE_SUPERCOR, purchaseDto.getStore());
        assertEquals(TestConstants.PRODUCT_HUEVOS, purchaseDto.getProduct());
        assertEquals(TestConstants.COST, purchaseDto.getCost().doubleValue());
        assertEquals(EnumStatus.ALTA.toString(), purchaseDto.getStatus());
    }

    @Test
    @Order(7)
    void GivenPurchaseDateAndStore_WhenGetPurchaseByDateAndStore_ThenReturnsPurchaseDtoList() throws IOException {
        List<PurchaseDto> purchaseDtoList = purchaseService.getPurchaseByDateAndStore(purchaseDateAndStoreRequest());
        assertNotNull(purchaseDtoList);
        PurchaseDto purchaseDto = purchaseDtoList.get(0);
        assertNotNull(purchaseDtoList);
        assertEquals(1, purchaseDtoList.size());
        assertEquals(1, purchaseDto.getId().longValue());
        assertEquals(TestConstants.STORE_SUPERCOR, purchaseDto.getStore());
        assertEquals(TestConstants.PRODUCT_HUEVOS, purchaseDto.getProduct());
        assertEquals(TestConstants.COST, purchaseDto.getCost().doubleValue());
        assertEquals(EnumStatus.ALTA.toString(), purchaseDto.getStatus());
    }

    @Test
    @Order(8)
    @Transactional
    void GivenPurchaseRequestAndPurchaseIdNotExists_WhenUpdatePurchase_ThenReturnsPurchaseDto() throws IOException {
        PurchaseDto purchaseDto = purchaseService.updatePurchase(purchaseRequestToUpdate(), 100L);
        assertNull(purchaseDto);
    }

    @Test
    @Order(9)
    @Transactional
    void GivenPurchaseRequestAndPurchaseIdExists_WhenUpdatePurchase_ThenReturnsPurchaseDto() throws IOException {
        PurchaseDto purchaseDto = purchaseService.updatePurchase(purchaseRequestToUpdate(), 1L);
        assertNotNull(purchaseDto);
        assertNotEquals(TestConstants.STORE_SUPERCOR, purchaseDto.getStore());
        assertEquals(TestConstants.STORE_MERCADONA, purchaseDto.getStore());
    }

    @Test
    @Order(10)
    @Transactional
    void GivenPurchaseId_WhenDeletePurchaseById_ThenReturnsBoolean(){
        boolean purchaseDeleted = purchaseService.deletePurchaseById(1L);
        assertTrue(purchaseDeleted);
    }

    @Test
    @Order(11)
    @Transactional
    void GivenPurchaseIdNotExists_WhenDeletePurchaseById_ThenReturnsBoolean(){
        boolean purchaseDeleted = purchaseService.deletePurchaseById(100L);
        assertFalse(purchaseDeleted);
    }
}