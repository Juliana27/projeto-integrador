package com.concat.projetointegrador.unit;

import com.concat.projetointegrador.dto.ProductDTO;
import com.concat.projetointegrador.exception.EntityNotFound;
import com.concat.projetointegrador.exception.ObjectNotRegistrate;
import com.concat.projetointegrador.model.*;
import com.concat.projetointegrador.repository.BuyerRepository;
import com.concat.projetointegrador.repository.PurchasedOrderRepository;
import com.concat.projetointegrador.service.BuyerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BuyerServiceTest {

    private final PurchasedOrderRepository purchasedOrderRepository = mock(PurchasedOrderRepository.class);
    private final BuyerRepository buyerRepository = mock(BuyerRepository.class);
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final BuyerService buyerService = new BuyerService(buyerRepository, purchasedOrderRepository, passwordEncoder);

    private Buyer mockBuyer() {
        return new Buyer(1L, "AnyName", "AnyLastName", "U", "S", 11111111L);
    }

    private Product mockProduct() {
        return Product.builder()
                .id(1L)
                .price(BigDecimal.valueOf(10))
                .category(Category.CONGELADOS)
                .name("frango")
                .volume(1)
                .seller(Seller.builder().id(1L).name("Jonas").lastName("Viana").build())
                .build();
    }

    private List<Cart> mockCart() {
        return Arrays.asList(Cart.builder()
                .id(1L)
                .product(mockProduct())
                .quantity(10)
                .build());
    }


    private List<PurchasedOrder> mockPurchasedOrder() {
        return Arrays.asList(PurchasedOrder.builder()
                    .id(1L)
                    .buyer(mockBuyer())
                    .status("finalizado")
                    .cart(mockCart()).build());
        }

    @Test
    void shouldReturnABuyerById() {
        when(buyerRepository.findById(anyLong())).thenReturn(Optional.of(mockBuyer()));
        Buyer result = buyerService.findById(1L);
        Buyer actual = mockBuyer();

        assertEquals(actual.getClass(), result.getClass());
    }

    @Test
    void shouldReturnExceptionWhenBuyerDoesntExist() {
        EntityNotFound exception = assertThrows(EntityNotFound.class, () -> buyerService.findById(1L));
        String expectedMessage = "Comprador não encontrado";
        String actualMessage = exception.getMessage();

        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    void shouldCreateBuyer() {
        Buyer buyer;
        Mockito.when(buyerRepository.findByCpf(Mockito.anyLong())).thenReturn(Optional.empty());
        Mockito.when(buyerRepository.save(Mockito.any())).thenReturn(mockBuyer());
        buyer = buyerService.create(mockBuyer());
        assertEquals("U", buyer.getName());
    }

    @Test
    void shouldReturnBuyerAlreadyRegistered() {
        Mockito.when(buyerRepository.findByCpf(Mockito.anyLong())).thenReturn(Optional.of(mockBuyer()));
        Mockito.when(buyerRepository.save(Mockito.any())).thenReturn(mockBuyer());
        Throwable exception = assertThrows(RuntimeException.class, () -> buyerService.create(mockBuyer()));
        assertEquals("Comprador já cadastrado", exception.getMessage());
    }

    @Test
    void shouldFindByPurchaseOrderByBuyer() {
        Mockito.when(purchasedOrderRepository.findPurchasedOrderByBuyer(Mockito.any())).thenReturn(mockPurchasedOrder());
        List<PurchasedOrder> purchasedOrderByBuyerList = buyerService.findByPurchaseOrderByBuyer(mockBuyer());
        assertEquals("finalizado", purchasedOrderByBuyerList.get(0).getStatus());
    }

    @Test
    void shouldReturnErrorFindByPurchaseOrderByBuyer() {
        Mockito.when(purchasedOrderRepository.findPurchasedOrderByBuyer(Mockito.any())).thenReturn(new ArrayList<>());
        Throwable exception = assertThrows(ObjectNotRegistrate.class, () -> buyerService.findByPurchaseOrderByBuyer(mockBuyer()));
        assertEquals("Não existe compras realizadas para esse comprador!", exception.getMessage());
    }
}