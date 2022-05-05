package com.concat.projetointegrador.controller;

import com.concat.projetointegrador.dto.CartsDTO;
import com.concat.projetointegrador.dto.ProductDTO;
import com.concat.projetointegrador.dto.PurchasedOrderDTO;
import com.concat.projetointegrador.dto.PurchasedOrderResponseDTO;
import com.concat.projetointegrador.model.PurchasedOrder;
import com.concat.projetointegrador.service.PurchasedOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class PurchaseOrderController {

    @Autowired
    private PurchasedOrderService purchaseOrderService;

    /**
     * Save a purchase order
     * @param purchasedOrder - purchase order object
     * @return the creation of a purchase order with the status code 201 created
     */
    @PostMapping
    public ResponseEntity<PurchasedOrderDTO> create(@RequestBody PurchasedOrder purchasedOrder) {
        return new ResponseEntity<>(purchaseOrderService.create(purchasedOrder), HttpStatus.CREATED);
    }


    /**
     * Search for a purchase order by id
     * @param id Long - purchase order id
     * @return a purchase order with status code 200 ok
     */
    @GetMapping
    public ResponseEntity<PurchasedOrderResponseDTO> findById(@RequestParam Long id) {
        PurchasedOrder purchasedOrder = purchaseOrderService.findById(id);
        List<CartsDTO> carts = purchasedOrder.getCart().stream()
                .map(cart -> CartsDTO.builder()
                        .id(cart.getId())
                        .quantity(cart.getQuantity())
                        .product(ProductDTO.convertToProductDTO(cart.getProduct()))
                        .build()).collect(Collectors.toList());
        PurchasedOrderResponseDTO purchasedOrderResponseDTO = PurchasedOrderResponseDTO.builder()
                .id(purchasedOrder.getId())
                .date(purchasedOrder.getDate())
                .status(purchasedOrder.getStatus())
                .carts(carts)
                .build();
        return ResponseEntity.ok(purchasedOrderResponseDTO);
    }

    /**
     * Updates a purchase order to "completed" status
     * @param id Long - purchase order id
     * @return returns a purchase order with status "completed" and status code 200 ok
     */
    @PutMapping
    public ResponseEntity<PurchasedOrderResponseDTO> update(@RequestParam Long id) {
        PurchasedOrder updatedPurchasedOrder = purchaseOrderService.update(id);
        List<CartsDTO> carts = updatedPurchasedOrder.getCart().stream()
                .map(cart -> CartsDTO.builder()
                        .id(cart.getId())
                        .quantity(cart.getQuantity())
                        .product(ProductDTO.convertToProductDTO(cart.getProduct()))
                        .build()).collect(Collectors.toList());
        PurchasedOrderResponseDTO purchasedOrderResponseDTO = PurchasedOrderResponseDTO.builder()
                .id(updatedPurchasedOrder.getId())
                .date(updatedPurchasedOrder.getDate())
                .status(updatedPurchasedOrder.getStatus())
                .carts(carts)
                .build();
        return ResponseEntity.ok(purchasedOrderResponseDTO);
    }

}
