package com.concat.projetointegrador.dto;

import com.concat.projetointegrador.model.PurchasedOrder;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PurchasedOrderResponseDTO {

    private Long id;
    private LocalDate date;
    private String status;
    private List<CartsDTO> carts;

    public static PurchasedOrderResponseDTO convertToPurchasedOrderResponseDTO(PurchasedOrder purchasedOrder) {
        return PurchasedOrderResponseDTO.builder()
                .id(purchasedOrder.getId())
                .date(purchasedOrder.getDate())
                .status(purchasedOrder.getStatus())
                .carts(purchasedOrder.getCart().stream().map(cart -> CartsDTO.convertToCartsDTO(cart)).collect(Collectors.toList()))
                .build();
    }
}
