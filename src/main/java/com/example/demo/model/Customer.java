package com.example.demo.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Customer extends AppUser implements Serializable {
    @OneToMany(mappedBy = "customer" , fetch = FetchType.LAZY)
    private List<Order> orders ;
}
