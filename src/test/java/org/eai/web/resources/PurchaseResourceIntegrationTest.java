package org.eai.web.resources;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;

import static io.restassured.RestAssured.given;

import io.quarkus.test.junit.QuarkusTest;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import javax.transaction.Transactional;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.IOException;
import java.util.Map;

import org.eai.domain.utils.TestConstants;
import org.eai.utils.JsonToObjectsCreator;
import org.eai.utils.TestsUtility;
import org.eai.web.models.request.PurchaseDateAndStoreRequest;
import org.eai.web.models.request.PurchaseRequest;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PurchaseResourceIntegrationTest extends JsonToObjectsCreator {

    private static final String PATH_ROOT = "/purchase";
    private static final String PATH_GET_PURCHASES_PAGINATED = "/purchase/paginated";
    private static final String PATH_GET_LIST_STORE = "/purchase/list/{store}";
    private static final String PATH_GET_LIST_DATE_AND_STORE = "/purchase/listByDatePurchaseAndStore";
    private static final String PATH_PURCHASE_BY_ID = "/purchase/{id}";
    private static final String PATH_PARAM_ID = "id";
    private static final String SIZE_LITERAL = "size()";
    private static final String STORE_LITERAL = "store";
    private static final String PRODUCT_LITERAL = "product";
    private static final Map<String, Long> pathParamsIdExists = Map.of(PATH_PARAM_ID, 2L);
    private static final Map<String, Long> pathParamsIdNotExists = Map.of(PATH_PARAM_ID, 100L);
    private static final Map<String, String> pathParamsStoreSupercor = Map.of(STORE_LITERAL, TestConstants.STORE_SUPERCOR);
    private static final Map<String, String> pathParamsStoreMercadona = Map.of(STORE_LITERAL, TestConstants.STORE_MERCADONA);

    @Test
    @Order(1)
    @Transactional
    void GivenPurchaseRequestIncorrect_WhenAddPurchase_ThenReturnThrowsApiBadRequest() throws IOException {

        PurchaseRequest purchaseRequest = purchaseRequestIncorrect();

        given()
                .body(purchaseRequest)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().post(PATH_ROOT)
                .then().assertThat();
    }

    @Test
    @Transactional
    void GivenPurchaseRequestAttributesAreNull_WhenAddPurchase_ThenThrowsApiBadRequestException() {

        PurchaseRequest purchaseRequest = new PurchaseRequest();

        given()
                .body(purchaseRequest)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().post(PATH_ROOT)
                .then().assertThat();
    }


    @Test
    @Order(2)
    @Transactional
    void GivenPurchaseRequestCorrect_WhenAddPurchase_ThenReturnStatusCreated() throws IOException {

        PurchaseRequest purchaseRequest = purchaseRequest();

        given()
                .body(purchaseRequest)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().post(PATH_ROOT)
                .then().statusCode(Response.Status.CREATED.getStatusCode());
    }



    @Test
    @Order(3)
    void WhenGetAllPurchases_ThenReturnsStatusOk(){
        given()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().get(PATH_ROOT)
                .then().statusCode(Response.Status.OK.getStatusCode())
                .body(SIZE_LITERAL, is(1))
                .body(PATH_PARAM_ID, hasItem(2))
                .body(STORE_LITERAL, hasItem(TestConstants.STORE_SUPERCOR))
                .body(PRODUCT_LITERAL, hasItem(TestConstants.PRODUCT_HUEVOS));
    }

    @Test
    @Order(4)
    void GivenPageAndSize_WhenGetPurchasesPagedList_ThenReturnsStatusOk(){
        given()
                .queryParams(TestsUtility.getPageQueryParam())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().get(PATH_GET_PURCHASES_PAGINATED)
                .then().statusCode(Response.Status.OK.getStatusCode())
                .body(SIZE_LITERAL, is(4))
                .body("numOfResults", is(1))
                .body("page", is(0));
    }

    @Test
    @Order(5)
    void GivenStoreNotExists_WhenGetPurchasesListByStore_ThenReturnsListEmpty(){
        given()
                .pathParams(pathParamsStoreMercadona)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().get(PATH_GET_LIST_STORE)
                .then().statusCode(Response.Status.OK.getStatusCode())
                .body(SIZE_LITERAL, is(0));
    }

    @Test
    @Order(6)
    void GivenStoreExists_WhenGetPurchasesListByStore_ThenReturnsStatusOk(){
        given()
                .pathParams(pathParamsStoreSupercor)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().get(PATH_GET_LIST_STORE)
                .then().statusCode(Response.Status.OK.getStatusCode())
                .body(SIZE_LITERAL, is(1))
                .body(STORE_LITERAL, hasItem(TestConstants.STORE_SUPERCOR))
                .body(PRODUCT_LITERAL, hasItem(TestConstants.PRODUCT_HUEVOS));
    }

    @Test
    @Order(7)
    void GivenPurchaseDateAndStoreRequestIncorrect_WhenGetPurchasesListByDatePurchaseAndStore_ThenThrowsApiBadRequestException() throws IOException {
        PurchaseDateAndStoreRequest purchaseDateAndStoreRequestIncorrect = purchaseDateAndStoreRequestIncorrect();

        given()
                .body(purchaseDateAndStoreRequestIncorrect)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().get(PATH_GET_LIST_DATE_AND_STORE)
                .then().assertThat();
    }

    @Test
    @Order(8)
    void GivenPurchaseDateAndStoreRequest_WhenGetPurchasesListByDatePurchaseAndStore_ThenReturnsStatusOk() throws IOException {
        PurchaseDateAndStoreRequest purchaseDateAndStoreRequest = purchaseDateAndStoreRequest();

        given()
                .body(purchaseDateAndStoreRequest)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().get(PATH_GET_LIST_DATE_AND_STORE)
                .then().statusCode(Response.Status.OK.getStatusCode())
                .body(SIZE_LITERAL, is(1))
                .body(STORE_LITERAL, hasItem(TestConstants.STORE_SUPERCOR))
                .body(PRODUCT_LITERAL, hasItem(TestConstants.PRODUCT_HUEVOS));
    }

    @Test
    @Order(9)
    void GivenPurchaseIdNotExists_WhenGetPurchaseById_ThenReturnsStatusNotFound() {
        given()
                .pathParams(pathParamsIdNotExists)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().get(PATH_PURCHASE_BY_ID)
                .then().statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    @Order(10)
    void GivenPurchaseIdExists_WhenGetPurchaseById_ThenReturnsStatusOk() {
        given()
                .pathParams(pathParamsIdExists)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().get(PATH_PURCHASE_BY_ID)
                .then().statusCode(Response.Status.OK.getStatusCode())
                .body(PATH_PARAM_ID, is(2))
                .body(STORE_LITERAL, is(TestConstants.STORE_SUPERCOR))
                .body(PRODUCT_LITERAL, is(TestConstants.PRODUCT_HUEVOS));
    }

    @Test
    @Order(11)
    @Transactional
    void GivenPurchaseRequestAndPurchaseIdIncorrect_WhenUpdatePurchase_ThenThrowsApiBadRequestException() throws IOException {
        PurchaseRequest purchaseRequestIncorrect = purchaseRequestIncorrect();

        given()
                .pathParams(pathParamsIdExists)
                .body(purchaseRequestIncorrect)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().put(PATH_PURCHASE_BY_ID)
                .then().assertThat();
    }

    @Test
    @Order(12)
    @Transactional
    void GivenPurchaseRequestAndPurchaseIdNotExists_WhenUpdatePurchase_ThenReturnsStatusNotFound() throws IOException {
        PurchaseRequest purchaseRequestToUpdate = purchaseRequestToUpdate();

        given()
                .pathParams(pathParamsIdNotExists)
                .body(purchaseRequestToUpdate)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().put(PATH_PURCHASE_BY_ID)
                .then().statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    @Order(13)
    @Transactional
    void GivenPurchaseRequestAndPurchaseIdCorrect_WhenUpdatePurchase_ThenReturnsStatusOk() throws IOException {
        PurchaseRequest purchaseRequestToUpdate = purchaseRequestToUpdate();

        given()
                .pathParams(pathParamsIdExists)
                .body(purchaseRequestToUpdate)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().put(PATH_PURCHASE_BY_ID)
                .then().statusCode(Response.Status.OK.getStatusCode())
                .body(PATH_PARAM_ID, is(2))
                .body(STORE_LITERAL, is(TestConstants.STORE_MERCADONA));
    }

    @Test
    @Order(14)
    @Transactional
    void GivenPurchaseIdNotExists_WhenDeletePurchaseById_ThenReturnsStatusNotFound() {
        given()
                .pathParams(pathParamsIdNotExists)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().delete(PATH_PURCHASE_BY_ID)
                .then().statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    @Order(15)
    @Transactional
    void GivenPurchaseIdtExists_WhenDeletePurchaseById_ThenReturnsStatusNoContent() {
        given()
                .pathParams(pathParamsIdExists)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().delete(PATH_PURCHASE_BY_ID)
                .then().statusCode(Response.Status.NO_CONTENT.getStatusCode());
    }


}
