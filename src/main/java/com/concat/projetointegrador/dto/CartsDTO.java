package com.concat.projetointegrador.dto;

import com.concat.projetointegrador.model.Cart;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartsDTO {

    private Long id;
    private Integer quantity;
    private ProductDTO product;// corrigir esse nome

    public static CartsDTO convertToCartsDTO(Cart cart) {
        return CartsDTO.builder()
                .id(cart.getId())
                .quantity(cart.getQuantity())
                .product(ProductDTO.convertToProductDTO(cart.getProduct()))
                .build();
    }
}
