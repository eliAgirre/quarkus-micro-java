package org.eai.domain.dto;

import java.time.OffsetDateTime;

public class PurchaseDatesAndStoreDto {

    private OffsetDateTime datePurchase;
    private String store;

    public PurchaseDatesAndStoreDto(){
        // Empty construct
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
        return "PurchaseDatesAndStoreDto{" +
                "datePurchase=" + datePurchase +
                ", store='" + store + '\'' +
                '}';
    }
}
