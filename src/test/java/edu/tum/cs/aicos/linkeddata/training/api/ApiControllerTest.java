package edu.tum.cs.aicos.linkeddata.training.api;

import edu.tum.cs.aicos.linkeddata.training.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class ApiControllerTest {

    Logger logger = LoggerFactory.getLogger(ApiControllerTest.class);

    @Value("${local.server.port}")
    protected int port;

    @Test
    public void testCountries() throws Exception {
        logger.debug("testHello begin");

        RestTemplate browser1 = new TestRestTemplate();
        ResponseEntity<Countries> responseEntity = browser1.getForEntity(
                "http://127.0.0.1:" + port + "/api/countries?population=1000000", Countries.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<Country> data = responseEntity.getBody();
        assertEquals("Bunyoro", data.get(5).getName());
        assertEquals(100, data.size());

        logger.debug("testHello end");
    }

}
