package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "supplier")
public class Supplier extends AppUser implements Serializable {
}
