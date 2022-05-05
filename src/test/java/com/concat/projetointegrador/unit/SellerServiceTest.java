package com.concat.projetointegrador.unit;

import com.concat.projetointegrador.exception.EntityNotFound;
import com.concat.projetointegrador.exception.ObjectNotRegistrate;
import com.concat.projetointegrador.model.Category;
import com.concat.projetointegrador.model.Product;
import com.concat.projetointegrador.model.Seller;
import com.concat.projetointegrador.repository.ProductRepository;
import com.concat.projetointegrador.repository.SellerRepository;
import com.concat.projetointegrador.service.SellerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SellerServiceTest {

    public static final long ID = 1L;
    public static final String NAME = "V";
    public static final String USERNAME = "OnlyV";
    public static final String PASSWORD = "NANI!!";
    public static final String LAST_NAME = "?";
    private SellerService service;
    private SellerRepository sellerRepository = Mockito.mock(SellerRepository.class);
    private ProductRepository productRepository = Mockito.mock(ProductRepository.class);
    private Optional<Seller> optionalSeller;
    private Seller seller;
    private Seller objectSeller;
    private List<Product> products;

    @BeforeEach
    private void setUp() {
        service = new SellerService(sellerRepository, productRepository);
        startSeller();
    }

    @Test
    public void whenFindByIDThenReturnASeller() {
        Mockito.when(sellerRepository.findById(Mockito.anyLong())).thenReturn(optionalSeller);
        Seller response = service.findByID(ID);
        Assertions.assertEquals(response.getClass(), seller.getClass());
    }

    @Test
    public void shouldReturnAnErrorWhenNotFoundASeller() {
        Mockito.when(sellerRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        EntityNotFound entityNotFound = Assertions
                .assertThrows(EntityNotFound.class, () -> service.findByID(Mockito.anyLong()));

        Assertions.assertEquals(entityNotFound.getMessage(), "Vendedor não existe.");
    }

    @Test
    public void whenCreateASellerThenReturnASeller() {
        Mockito.when(sellerRepository.save(Mockito.any())).thenReturn(seller);
        Seller response = service.create(objectSeller);
        Assertions.assertEquals(response.getClass(), seller.getClass());
    }

    private void startSeller() {
        seller = Seller.builder().id(ID).username(USERNAME).password(PASSWORD).name(NAME).lastName(LAST_NAME).build();
        objectSeller = Seller.builder().username(USERNAME).password(PASSWORD).name(NAME).lastName(LAST_NAME).build();
        optionalSeller = Optional.of(Seller.builder().id(ID).username(USERNAME).password(PASSWORD).name(NAME).lastName(LAST_NAME).build());
        products = Arrays.asList(Product.builder().id(1L).name("carne").seller(seller).category(Category.CONGELADOS).price(BigDecimal.valueOf(20)).volume(1).build(),
                Product.builder().id(2L).name("frango").seller(seller).category(Category.CONGELADOS).price(BigDecimal.valueOf(10)).volume(1).build());
    }

    @Test
    public void shouldReturnProductsBySeller() {
        Mockito.when(productRepository.findProductBySeller(Mockito.any())).thenReturn(products);
        List<Product> response = service.findAllProductsBySeller(seller);
        Assertions.assertEquals(products, response);
    }

    @Test
    public void shouldReturnErrorWhenProductsBySeller() {
        Mockito.when(productRepository.findProductBySeller(Mockito.any())).thenReturn(Mockito.anyList());
        Throwable exception = assertThrows(ObjectNotRegistrate.class, () -> service.findAllProductsBySeller(seller));
        assertEquals("Não existe produtos cadastrados para esse vendedor!", exception.getMessage());
    }

}
