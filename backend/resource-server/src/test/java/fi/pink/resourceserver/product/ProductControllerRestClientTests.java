package fi.pink.resourceserver.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.client.MockMvcClientHttpRequestFactory;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class ProductControllerRestClientTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    void restClientString() {
        var client = RestClient.builder().requestFactory(new MockMvcClientHttpRequestFactory(mockMvc)).build();

        var response = client.get().uri("/shop/products/99").retrieve().body(String.class);

        assertThat(response).contains("tuote");
    }

    @Test
    void restClientObject() {
        var client = RestClient.builder().requestFactory(new MockMvcClientHttpRequestFactory(mockMvc)).build();

        var response = client.get().uri("/shop/products/99").retrieve().body(Product.class);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(99);
        assertThat(response.getProductName()).isEqualTo("tuote1");
    }

    @Test
    void restClientStatus() {
        var client = RestClient.builder().requestFactory(new MockMvcClientHttpRequestFactory(mockMvc)).build();

        var response = client.get().uri("/shop/products/99").retrieve().toEntity(Product.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void restClientStatusWithStringClass() {
        var client = RestClient.builder().requestFactory(new MockMvcClientHttpRequestFactory(mockMvc)).build();

        var response = client.get().uri("/shop/products/99").retrieve().toEntity(String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


}
