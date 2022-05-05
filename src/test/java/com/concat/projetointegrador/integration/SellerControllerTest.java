package com.concat.projetointegrador.integration;

import com.concat.projetointegrador.dto.InboundOrderDTO;
import com.concat.projetointegrador.dto.SellerDTO;
import com.concat.projetointegrador.dto.SellerResponseDTO;
import com.concat.projetointegrador.model.InboundOrder;
import com.concat.projetointegrador.model.Seller;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql(value = {"/test-schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class SellerControllerTest {

    @Autowired
    private MockMvc mock;

    @Test
    public void shouldCreateAInboundOrder() throws Exception {
        String payload = "{\"name\":\"joao\",\"lastName\":\"Silva\",\"username\":\"sell\",\"password\":123}";
        SimpleGrantedAuthority supervisor = new SimpleGrantedAuthority("Supervisor");
        ArrayList<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        simpleGrantedAuthorities.add(supervisor);

        MvcResult result = mock.perform(post("/seller")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));

        String jsonReturned = result.getResponse().getContentAsString();
        SellerDTO inboundOrder = objectMapper.readValue(jsonReturned, SellerDTO.class);

        assertEquals(5, inboundOrder.getId());

    }

    @Test
    public void shouldFindAInboundOrderById() throws Exception {

        SimpleGrantedAuthority supervisor = new SimpleGrantedAuthority("Supervisor");
        ArrayList<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        simpleGrantedAuthorities.add(supervisor);

        MvcResult result = mock.perform(get("/seller/{id}", 3)
                .with(
                        user("Supervisor")
                                .password("123")
                                .authorities(simpleGrantedAuthorities)
                )
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));

        String jsonReturned = result.getResponse().getContentAsString();
        InboundOrder inboundOrder = objectMapper.readValue(jsonReturned, InboundOrder.class);

        assertEquals(3, inboundOrder.getId());

    }

    @Test
    public void shouldFindAllProductsBySeller() throws Exception {
        SimpleGrantedAuthority supervisor = new SimpleGrantedAuthority("Supervisor");
        ArrayList<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        simpleGrantedAuthorities.add(supervisor);

        MvcResult result = mock.perform(get("/seller/products/{id}", 3)
                .with(user("Supervisor").password("123")
                .authorities(simpleGrantedAuthorities)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));

        String jsonReturned = result.getResponse().getContentAsString();
        SellerResponseDTO sellerResponseDTO = objectMapper.readValue(jsonReturned, SellerResponseDTO.class);

        assertEquals("Seller", sellerResponseDTO.getName());
    }

}


