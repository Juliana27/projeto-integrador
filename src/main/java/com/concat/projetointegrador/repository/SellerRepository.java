package com.concat.projetointegrador.repository;

import com.concat.projetointegrador.model.Product;
import com.concat.projetointegrador.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {

}
