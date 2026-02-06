package com.omar.portal.shipments;

import com.omar.portal.audit.AuditService;
import com.omar.portal.auth.TestSecurityConfig;
import com.omar.portal.common.Role;
import com.omar.portal.users.User;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ShipmentController.class)
@Import(TestSecurityConfig.class)
class ShipmentControllerTest {
    @Autowired MockMvc mvc;
    @MockBean ShipmentRepository shipments;
    @MockBean AuditService auditService;

    @Test
    void createShipment() throws Exception {
        Shipment shipment = new Shipment();
        shipment.setTrackingNumber("TRK123");
        shipment.setStatus("CREATED");
        shipment.setOrigin("NY");
        shipment.setDestination("CA");
        when(shipments.save(any())).thenReturn(shipment);

        User principal = new User();
        principal.setEmail("user@x.com");
        principal.setRole(Role.USER);

        var auth = new UsernamePasswordAuthenticationToken(principal, null, List.of());
        mvc.perform(post("/api/shipments")
                        .with(authentication(auth))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"trackingNumber\":\"TRK123\",\"status\":\"CREATED\",\"origin\":\"NY\",\"destination\":\"CA\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.trackingNumber").value("TRK123"));
    }
}
