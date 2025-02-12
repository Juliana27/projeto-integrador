package com.concat.projetointegrador.integration;

import com.concat.projetointegrador.dto.BuyerResponseDTO;
import com.concat.projetointegrador.dto.ProductResponseDTO;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql(value = {"/test-schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class BuyerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private String payload() {
        return "{\"name\":\"joao\",\"lastName\": \"Silva\",\"cpf\": 12345478911,\"username\": \"jabcnetoBUY1\",\"password\": 123}";
    }

    @Test
    public void shouldCreateABuyerAndReturn201() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/buyer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload())
                .with(user("ADMIN").password("123")))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andReturn();
    }

    @Test
    public void shouldFindByPurchaseOrderByBuyerAndReturn201() throws Exception {
        SimpleGrantedAuthority admin = new SimpleGrantedAuthority("ADMIN");
        ArrayList<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        simpleGrantedAuthorities.add(admin);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/buyer/purchaseorder/{id}", 4)
                .with(user("ADMIN").password("123")
                .authorities(simpleGrantedAuthorities)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
        String jsonReturned = result.getResponse().getContentAsString();
        BuyerResponseDTO buyerResponseDTO = objectMapper.readValue(jsonReturned, BuyerResponseDTO.class);
        assertEquals("Buyer", buyerResponseDTO.getName());
    }
}
