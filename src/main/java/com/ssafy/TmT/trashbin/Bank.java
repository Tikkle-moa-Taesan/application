package com.ssafy.TmT.trashbin;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name = "bank")
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bankId;

    @Column(name = "bank_code", length = 10, nullable = false, unique = true)
    private String bankCode;

    @Column(name = "bank_name", length = 100, nullable = false)
    private String bankName;

    @Column(name = "swift_code", length = 11)
    private String swiftCode;

    @Column(name = "branch_code", length = 10)
    private String branchCode;

    @Column(name = "address", length = 200)
    private String address;

    @Column(name = "contact_number", length = 20)
    private String contactNumber;

    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;
}
