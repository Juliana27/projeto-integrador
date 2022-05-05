package com.concat.projetointegrador.service;

import com.concat.projetointegrador.exception.EntityNotFound;
import com.concat.projetointegrador.exception.ObjectNotRegistrate;
import com.concat.projetointegrador.model.Product;
import com.concat.projetointegrador.model.Seller;
import com.concat.projetointegrador.repository.ProductRepository;
import com.concat.projetointegrador.repository.SellerRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SellerService {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private SellerRepository sellerRepository;
    private ProductRepository productRepository;

    /**
     * Find a Seller by ID or throw an EntityNotFound Exception
     * @param id A Long type ID from a seller
     * @return Seller
     */
    public Seller findByID(Long id) {
        Optional<Seller> seller = sellerRepository.findById(id);
        if (seller.isPresent()) {
        return seller.get();
        }
            throw new EntityNotFound("Vendedor não existe.");
    }

    /**
     * Save a Seller in database
     * @param seller is an object Seller to save
     * @return the object Seller with your id
     */
    public Seller create(Seller seller) {
            seller.setPassword(passwordEncoder.encode(seller.getPassword()));
            return sellerRepository.save(seller);
    }

    /**
     * search a list of products by seller
     * @param seller - object seller
     * @return returns a list of products if it exists
     * @throws ObjectNotRegistrate - returns an exception if there are no products
     */
    public List<Product> findAllProductsBySeller(Seller seller) {
        List<Product> products = productRepository.findProductBySeller(seller);
        if (products.isEmpty()) {
            throw new ObjectNotRegistrate("Não existe produtos cadastrados para esse vendedor!");
        }
        return products;
    }
}
