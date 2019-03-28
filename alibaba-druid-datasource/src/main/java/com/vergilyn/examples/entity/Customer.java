package com.vergilyn.examples.entity;

import java.util.Date;

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
    private Long customerId;

    private Long storeId;
    private String firstName;
    private String lastName;
    private String email;
    private Long addressId;
    private Byte active;
    private Date createDate;
    private Date lastUpdate;
}
