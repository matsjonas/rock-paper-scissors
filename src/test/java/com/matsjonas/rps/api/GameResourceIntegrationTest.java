package com.matsjonas.rps.api;

import com.matsjonas.rps.api.dto.MakeBetDTO;
import com.matsjonas.rps.api.dto.ResponseDTO;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

public class GameResourceIntegrationTest {

    private static Client client;
    private static WebTarget webTarget;

    @BeforeClass
    public static void beforeClass() {
        client = ClientBuilder.newClient();
        webTarget = client.target("http://localhost:8081/rps/api/game");
    }

    @Test
    public void testSuccessfulRequestChain() throws Exception {

        MakeBetDTO dto1 = new MakeBetDTO();
        dto1.setPlayer("Adam");
        dto1.setBet("ROCK");

        Response response1 = webTarget
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(dto1, MediaType.APPLICATION_JSON_TYPE));

        assertEquals(200, response1.getStatus());
        ResponseDTO responseDTO1 = response1.readEntity(ResponseDTO.class);
        assertEquals("Game started", responseDTO1.getMessage());

        MakeBetDTO dto2 = new MakeBetDTO();
        dto2.setPlayer("Bertil");
        dto2.setBet("PAPER");

        Response response2 = webTarget
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(dto2, MediaType.APPLICATION_JSON_TYPE));

        assertEquals(200, response2.getStatus());
        ResponseDTO responseDTO2 = response2.readEntity(ResponseDTO.class);
        assertEquals("Winner is Bertil", responseDTO2.getMessage());

        MakeBetDTO dto3 = new MakeBetDTO();
        dto3.setPlayer("Cesar");
        dto3.setBet("SCISSORS");

        Response response3 = webTarget
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(dto3, MediaType.APPLICATION_JSON_TYPE));

        assertEquals(200, response3.getStatus());
        ResponseDTO responseDTO3 = response3.readEntity(ResponseDTO.class);
        assertEquals("Game started", responseDTO3.getMessage());
    }

    @Test
    public void testInvalidRequests() throws Exception {

        MakeBetDTO dto1 = new MakeBetDTO();
        dto1.setBet("ROCK");

        Response response1 = webTarget
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(dto1, MediaType.APPLICATION_JSON_TYPE));

        assertEquals(400, response1.getStatus());

        MakeBetDTO dto2 = new MakeBetDTO();
        dto2.setPlayer("Bertil");

        Response response2 = webTarget
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(dto2, MediaType.APPLICATION_JSON_TYPE));

        assertEquals(400, response2.getStatus());


        MakeBetDTO dto3 = new MakeBetDTO();
        dto3.setPlayer("Cesar");
        dto3.setBet("SPOCK");

        Response response3 = webTarget
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(dto3, MediaType.APPLICATION_JSON_TYPE));

        assertEquals(400, response3.getStatus());
    }

    @Test
    public void testSuccessfulRequestChainWithInvalidInMiddle() throws Exception {

        MakeBetDTO dto1 = new MakeBetDTO();
        dto1.setPlayer("Adam");
        dto1.setBet("ROCK");

        Response response1 = webTarget
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(dto1, MediaType.APPLICATION_JSON_TYPE));

        assertEquals(200, response1.getStatus());
        ResponseDTO responseDTO1 = response1.readEntity(ResponseDTO.class);
        assertEquals("Game started", responseDTO1.getMessage());

        MakeBetDTO dto2 = new MakeBetDTO();
        dto2.setPlayer("Bertil");
        dto2.setBet("LIZARD");

        Response response2 = webTarget
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(dto2, MediaType.APPLICATION_JSON_TYPE));

        assertEquals(400, response2.getStatus());

        MakeBetDTO dto3 = new MakeBetDTO();
        dto3.setPlayer("Cesar");
        dto3.setBet("SCISSORS");

        Response response3 = webTarget
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(dto3, MediaType.APPLICATION_JSON_TYPE));

        assertEquals(200, response3.getStatus());
        ResponseDTO responseDTO3 = response3.readEntity(ResponseDTO.class);
        assertEquals("Winner is Adam", responseDTO3.getMessage());
    }

}