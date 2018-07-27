package com.personal.expensemanager.entities;

import com.personal.expensemanager.enums.TransactionType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@Table(name = "transactions")
public class Transaction {
    private int id;
    private int version;
    @ApiModelProperty(value = "The type of the transaction", allowableValues = "INCOME, EXPENSE, TRANSFER")
    private TransactionType transactionType;
    private double amount;
    private Account debitFrom;
    private Account creditTo;
    private Category category;
    private SubCategory subCategory;
    private Date created;
    private Date modified;

    public Transaction() {
    }

    public Transaction(TransactionType transactionType, double amount, Account debitFrom, Account creditTo, Category category, SubCategory subCategory) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.debitFrom = debitFrom;
        this.creditTo = creditTo;
        this.category = category;
        this.subCategory = subCategory;
    }

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Version
    public int getVersion() {
        return version;
    }
    public void setVersion(int version) {
        this.version = version;
    }

    @Column(nullable = false, columnDefinition = "ENUM('INCOME','EXPENSE','TRANSFER')")
    @Enumerated(EnumType.STRING)
    public TransactionType getTransactionType() {
        return transactionType;
    }
    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    @NotNull
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    @ManyToOne
    @JoinColumn(name = "from_accounts_accno")
    public Account getDebitFrom() {
        return debitFrom;
    }
    public void setDebitFrom(Account debitFrom) {
        this.debitFrom = debitFrom;
    }

    @ManyToOne
    @JoinColumn(name = "to_accounts_accno")
    public Account getCreditTo() {
        return creditTo;
    }
    public void setCreditTo(Account creditTo) {
        this.creditTo = creditTo;
    }

    @ManyToOne
    @JoinColumn(name = "categories_id")
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }

    @ManyToOne
    @JoinColumn(name = "subcategories_id")
    public SubCategory getSubCategory() {
        return subCategory;
    }
    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    @CreationTimestamp
    public Date getCreated() {
        return created;
    }
    public void setCreated(Date created) {
        this.created = created;
    }

    @UpdateTimestamp
    public Date getModified() {
        return modified;
    }
    public void setModified(Date modified) {
        this.modified = modified;
    }
}
