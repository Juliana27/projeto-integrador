package com.concat.projetointegrador.dto;

import com.concat.projetointegrador.model.Product;
import com.concat.projetointegrador.model.Seller;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellerResponseDTO {

    private String name;
    private String lastName;
    private List<ProductDTO> products;

    public static SellerResponseDTO convertToSellerResponseDTO(Seller seller, List<Product> products) {
        return SellerResponseDTO.builder()
                .name(seller.getName())
                .lastName(seller.getLastName())
                .products(products.stream().map(product -> ProductDTO.convertToProductDTO(product)).collect(Collectors.toList()))
                .build();
    }
}
