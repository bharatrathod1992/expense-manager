package com.personal.expensemanager.entities;

import com.personal.expensemanager.enums.AccountType;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "accounts")
@Data
public class Account {
    private int accno;
    private int version;
    private String name;
    private AccountType accountType;
    private double amount;
    private Date created;
    private Date modified;

    public Account() {
    }
    public Account(int accno, String name, AccountType accountType, double amount) {
        this.accno = accno;
        this.name = name;
        this.accountType = accountType;
        this.amount = amount;
    }

    @Id
    public int getAccno() {
        return accno;
    }
    public void setAccno(int accno) {
        this.accno = accno;
    }

    @Version
    public int getVersion() {
        return version;
    }

    @NonNull
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    @Column(nullable = false, columnDefinition = "ENUM('BANK','CASH','WALLET')")
    @Enumerated(EnumType.STRING)
    public AccountType getAccountType() {
        return accountType;
    }
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    @CreationTimestamp
    public Date getCreated() {
        return created;
    }

    @UpdateTimestamp
    public Date getModified() {
        return modified;
    }
}
