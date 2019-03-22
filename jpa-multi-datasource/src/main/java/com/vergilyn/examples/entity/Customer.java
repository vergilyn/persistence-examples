package com.vergilyn.examples.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author VergiLyn
 * @date 2019-03-21
 */
@Entity
@Table(name = "customer")
@Data
@NoArgsConstructor
@ToString
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "customer_id")
    private Long customerId;
    @Column(name = "store_id")
    private Long storeId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "address_id")
    private Long addressId;
    @Column(name = "active")
    private Byte active;
    @Column(name = "create_date")
    private Date createDate;
    @Column(name = "last_update")
    private Date lastUpdate;
}
