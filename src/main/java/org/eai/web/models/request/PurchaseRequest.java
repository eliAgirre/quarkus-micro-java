package org.eai.web.models.request;

import java.time.OffsetDateTime;

public class PurchaseRequest {

    private OffsetDateTime datePurchase;
    private String store;
    private String product;
    private Double cost;

    public PurchaseRequest(){
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

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "PurchaseRequest{" +
                "datePurchase=" + datePurchase +
                ", store='" + store + '\'' +
                ", product='" + product + '\'' +
                ", cost=" + cost +
                '}';
    }
}
