package com.concat.projetointegrador.controller.sellerController;

import com.concat.projetointegrador.model.sellerModel.Seller;
import com.concat.projetointegrador.service.sellerService.SellerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class SellerController {

    private final SellerService sellerService;

    @PostMapping("/seller")
        public ResponseEntity<Seller> create(@RequestBody @Valid Seller seller, UriComponentsBuilder uriComponentsBuilder) {

        Seller newSeller = sellerService.create(seller);

        URI uri = uriComponentsBuilder.path("/api/seller/{id}")
                .buildAndExpand(newSeller.getId())
                .toUri();

        return ResponseEntity.created(uri).body(newSeller);

    }

    @GetMapping("sellers")
        public ResponseEntity<List<Seller>> findAll() {

        List<Seller> sellers = sellerService.findAll();

        return ResponseEntity.ok(sellers);

    }

    @GetMapping("/seller/{id}")
        public ResponseEntity<Optional<Seller>> findByID(@PathVariable Long id) {//validar se é numero

                Optional<Seller> seller = sellerService.findByID(id);

                return ResponseEntity.ok(seller);

    }

    @PutMapping("/seller/{id}")
        public ResponseEntity<Seller> updateByID(@PathVariable Long id, @RequestBody @Valid Seller seller) {

            Seller updatedSeller = sellerService.updateByID(seller, id);

            return ResponseEntity.ok(updatedSeller);

    }

    @DeleteMapping("/seller/{id}")
        public ResponseEntity<Void> deleteByID(@PathVariable Long id) {

            sellerService.deleteByID(id);

            return ResponseEntity.noContent().build();

    }

}
