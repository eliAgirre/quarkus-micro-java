package org.eai.entities.models;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "purchases")
@NamedQuery(name = "PurchaseEntity.findPurchases", query = "Select p FROM PurchaseEntity as p ORDER BY p.creationDate DESC")
@NamedQuery(name = "PurchaseEntity.findPurchaseListByStore", query = "Select p FROM PurchaseEntity as p WHERE p.store = :store")
@NamedQuery(name = "PurchaseEntity.findPurchaseListByPurchaseDateAndStore", query = "Select p FROM PurchaseEntity as p WHERE p.datePurchase <= :datePurchase AND p.store = :store")
public class PurchaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "datePurchase")
    private OffsetDateTime datePurchase;

    @Column(name = "store")
    private String store;

    private String product;

    private Double cost;

    @Column(name = "creationDate")
    private OffsetDateTime creationDate;

    @Column(name = "modificationDate")
    private OffsetDateTime modificationDate;

    private String status;

    public PurchaseEntity() {
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
        return "PurchaseEntity{" +
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
