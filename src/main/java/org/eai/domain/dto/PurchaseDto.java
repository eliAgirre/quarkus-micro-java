package org.eai.domain.dto;

import java.time.OffsetDateTime;

public class PurchaseDto {

    private Long id;
    private OffsetDateTime datePurchase;
    private String store;
    private String product;
    private Double cost;
    private OffsetDateTime creationDate;
    private OffsetDateTime modificationDate;
    private String status;

    public PurchaseDto(){
        // Empty construct
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public OffsetDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(OffsetDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public OffsetDateTime getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(OffsetDateTime modificationDate) {
        this.modificationDate = modificationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PurchaseDto{" +
                "id=" + id +
                ", datePurchase=" + datePurchase +
                ", store='" + store + '\'' +
                ", product='" + product + '\'' +
                ", cost=" + cost +
                ", creationDate=" + creationDate +
                ", modificationDate=" + modificationDate +
                ", status='" + status + '\'' +
                '}';
    }
}
