package com.concat.projetointegrador.dto;

import com.concat.projetointegrador.model.Buyer;
import com.concat.projetointegrador.model.PurchasedOrder;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyerResponseDTO {

    private String name;
    private String lastName;
    private List<PurchasedOrderResponseDTO> purchaseOrder;

    public static BuyerResponseDTO convertToBuyerResponseDTO(Buyer buyer, List<PurchasedOrder> purchaseOrderByBuyerList) {
        return BuyerResponseDTO.builder()
                .name(buyer.getName())
                .lastName(buyer.getLastName())
                .purchaseOrder(purchaseOrderByBuyerList.stream().map(purchasedOrder ->
                        PurchasedOrderResponseDTO.convertToPurchasedOrderResponseDTO(purchasedOrder)).collect(Collectors.toList()))
                .build();
    }
}
