package com.concat.projetointegrador.controller;

import com.concat.projetointegrador.dto.BuyerDTO;
import com.concat.projetointegrador.dto.BuyerResponseDTO;
import com.concat.projetointegrador.model.Buyer;
import com.concat.projetointegrador.model.PurchasedOrder;
import com.concat.projetointegrador.service.BuyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/buyer")
public class BuyerController {

    @Autowired
    private BuyerService buyerService;

    /** create a new buyer
     * @param buyer - object buyer
     * @return a buyer DTO
     */
    @PostMapping
    public ResponseEntity<BuyerDTO> create(@RequestBody @Valid Buyer buyer) {
        BuyerDTO buyerDTO = BuyerDTO.convertToBuyerDTO(buyerService.create(buyer));
        return new ResponseEntity<>(buyerDTO, HttpStatus.CREATED);
    }

    /**
     * fetches all orders from a buyer
     * @param id Long - buyer id
     * @return an object with the first name , last name and the list of orders
     */
    @GetMapping("/purchaseorder/{id}")
    public ResponseEntity<BuyerResponseDTO> findByPurchaseOrderByBuyer(@PathVariable Long id) {
        Buyer buyer = buyerService.findById(id);
        BuyerResponseDTO buyerResponseDTO = BuyerResponseDTO.convertToBuyerResponseDTO(buyer, buyerService.findByPurchaseOrderByBuyer(buyer));
        return ResponseEntity.ok(buyerResponseDTO);
    }

}
