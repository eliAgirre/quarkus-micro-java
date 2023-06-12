package org.eai.utils;

import org.eai.web.models.request.PurchaseDateAndStoreRequest;
import org.eai.web.models.request.PurchaseRequest;

import java.io.IOException;

public class JsonToObjectsCreator extends BaseJsonToObjectsCreator {

    public PurchaseRequest purchaseRequest() throws IOException {
        return getObjectFromFile("/json/purchaseRequest/purchaseRequest.json", PurchaseRequest.class);
    }

    public PurchaseRequest purchaseRequestIncorrect() throws IOException {
        return getObjectFromFile("/json/purchaseRequest/purchaseRequestIncorrect.json", PurchaseRequest.class);
    }

    public PurchaseDateAndStoreRequest purchaseDateAndStoreRequest() throws IOException {
        return getObjectFromFile("/json/purchaseDateAndStoreRequest/purchaseDateAndStoreRequest.json", PurchaseDateAndStoreRequest.class);
    }

    public PurchaseDateAndStoreRequest purchaseDateAndStoreRequestIncorrect() throws IOException {
        return getObjectFromFile("/json/purchaseDateAndStoreRequest/purchaseDateAndStoreRequestIncorrect.json", PurchaseDateAndStoreRequest.class);
    }

    public PurchaseRequest purchaseRequestToUpdate() throws IOException {
        return getObjectFromFile("/json/purchaseRequest/purchaseRequestToUpdate.json", PurchaseRequest.class);
    }

}
