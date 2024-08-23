package com.camcorderio.productservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {

    private Long id;

    @NotBlank(message = "Product name must not be blank.")
    private String name;

    @NotBlank(message = "Description must not be blank.")
    private String description;

    @NotNull(message = "Price must not be null.")
    @Min(value = 0, message = "Price must be a positive value.")
    private Integer price;

    @NotNull(message = "Stock quantity must not be null.")
    @Min(value = 0, message = "Stock must be a positive value.")
    private Integer stock;
}
