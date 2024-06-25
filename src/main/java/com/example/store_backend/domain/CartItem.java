package com.example.store_backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "cart_item_tbl")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer cartItemId;


    @OneToOne
    @JsonIgnoreProperties(value={
            "productId",
            "seller",
            "quantity"

    })
    private Product cartProduct;

    private Integer cartItemQuantity;

}