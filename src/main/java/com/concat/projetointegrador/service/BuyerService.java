package com.concat.projetointegrador.service;

import com.concat.projetointegrador.dto.BuyerResponseDTO;
import com.concat.projetointegrador.exception.EntityNotFound;
import com.concat.projetointegrador.exception.ObjectNotRegistrate;
import com.concat.projetointegrador.model.Buyer;
import com.concat.projetointegrador.model.PurchasedOrder;
import com.concat.projetointegrador.repository.BuyerRepository;
import com.concat.projetointegrador.repository.PurchasedOrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BuyerService {

    private BuyerRepository buyerRepository;
    private PurchasedOrderRepository purchasedOrderRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * find a buyer by its id
     * @param id Long - buyers id
     * @return buyer if it exists
     * @throws EntityNotFound if buyer doenst exist
     */
    public Buyer findById(Long id) {
        Optional<Buyer> buyer = buyerRepository.findById(id);
        if(buyer.isPresent()) {
            return buyer.get();
        }
        throw new EntityNotFound("Comprador não encontrado");
    }

    /**
     * create a new buyer
     * @param buyer - buyer object to insert
     * @return a buyer
     */
    public Buyer create(Buyer buyer) {
        buyer.setPassword(passwordEncoder.encode(buyer.getPassword()));
        Optional<Buyer> buyerOpt = buyerRepository.findByCpf(buyer.getCpf());
        if(buyerOpt.isPresent()) {
            throw new RuntimeException("Comprador já cadastrado");
        }
        return buyerRepository.save(buyer);
    }

    /**
     * fetches all completed orders from a buyer
     * @param buyer - object buyer
     * @return a list of completed orders
     * @throws ObjectNotRegistrate - returns an exception if there are no orders
     */
    public List<PurchasedOrder> findByPurchaseOrderByBuyer(Buyer buyer) {
        List<PurchasedOrder> purchasedOrderByBuyer = purchasedOrderRepository.findPurchasedOrderByBuyer(buyer);
        List<PurchasedOrder> purchasedOrdersFinalized = purchasedOrderByBuyer.stream().filter(
                purchasedOrder -> purchasedOrder.getStatus().equalsIgnoreCase("finalizado")).collect(Collectors.toList());
        if (purchasedOrdersFinalized.isEmpty()) {
            throw new ObjectNotRegistrate("Não existe compras realizadas para esse comprador!");
        }
        return purchasedOrdersFinalized;
    }
}
