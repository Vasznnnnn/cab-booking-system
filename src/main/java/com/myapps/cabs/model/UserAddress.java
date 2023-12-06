package com.myapps.cabs.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "USER_ADDRESSES")
public class UserAddress {

    @Id
    @Column(name = "KERBEROS_ID")
    private String kerberosId;

    @Column(name = "USER_ID")
    private String userId;

    @ElementCollection
    @CollectionTable(name = "USER_ADDRESSES", joinColumns = {@JoinColumn(name = "USER_ID")})
    @AttributeOverrides({
            @AttributeOverride(name = "type", column = @Column(name = "ADDRESS_TYPE")),
            @AttributeOverride(name = "description", column = @Column(name = "DESCRIPTION")),
            @AttributeOverride(name = "coordinates", column = @Column(name = "CO_ORDINATES"))
    })
    private List<Address> addresses;

}
