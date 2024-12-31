package fi.pink.resourceserver.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductIntegrationTests {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void shouldReturnProductWhenDataIsSaved() {
        ResponseEntity<String> response = testRestTemplate.getForEntity("/products/99", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldNotReturnAProductWithAnUnknownId() {
        ResponseEntity<String> response = testRestTemplate.getForEntity("/products/1000", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isBlank();
    }

}
