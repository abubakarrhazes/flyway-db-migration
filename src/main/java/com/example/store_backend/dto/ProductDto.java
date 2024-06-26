package com.example.store_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private String prodName;
    private String manufacturer;
    private Double price;
    private Integer quantity;


}
