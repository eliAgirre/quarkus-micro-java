package org.eai.web.models.request;

import java.time.OffsetDateTime;

public class PurchaseDateAndStoreRequest {

    private OffsetDateTime datePurchase;
    private String store;

    public PurchaseDateAndStoreRequest(){
        // Empty
    }

    public OffsetDateTime getDatePurchase() {
        return datePurchase;
    }

    public void setDatePurchase(OffsetDateTime datePurchase) {
        this.datePurchase = datePurchase;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    @Override
    public String toString() {
        return "PurchaseDateAndStoreRequest{" +
                "datePurchase=" + datePurchase +
                ", store='" + store + '\'' +
                '}';
    }
}
