package com.order_management.order_service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PurchaseOrderIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void placeOrder_shouldReturn201() throws Exception {
        String jsonRequest = """
                {
                              "customerId": "f19b2d8a-7c33-4bc2-a8c3-1234567890ab",
                              "items": [
                                {
                                  "productCategory": "Electronics",
                                  "productName": "Test Laptop",
                                  "totalQuantity": 2,
                                  "totalPrice": 1500.00,
                                  "orderLineItems": [
                                    {
                                      "sku": "SKU-123",
                                      "quantity": 2,
                                      "price": 750.00,
                                      "attributes": [
                                        {
                                          "name": "Color",
                                          "value": "Silver"
                                        },
                                        {
                                          "name": "Warranty",
                                          "value": "2 Years"
                                        }
                                      ]
                                    }
                                  ]
                                }
                              ]
                            }
                """;

        mockMvc.perform(post("/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());
    }
}
