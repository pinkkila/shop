package fi.pink.resourceserver.product;

import fi.pink.resourceserver.SecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.mockito.Mockito.when;

@WebMvcTest(ProductController.class)
@Import(SecurityConfig.class)
public class ProductControllerSlicedTests {

    @Autowired
    MockMvcTester mockMvc;

    @MockitoBean
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        Product product = new Product(99L, "tuote1", 2);
        when(productRepository.findById(99L)).thenReturn(Optional.of(product));
    }

    @Test
    void basicTestWithMMTester() {
        mockMvc.get()
            .uri("/shop/products/99")
            .exchange()
            .assertThat()
            .hasStatus(HttpStatus.OK)
            .bodyJson()
            .extractingPath("$.productName")
            .asString()
            .isEqualTo("tuote1");
//            .extractingPath("$.id")
//            .asNumber()
//            .isEqualTo(99);
    }

    @Test
    void basicWithDifferentAssertionSyntaxAndRedundatnRequest() {
        assertThat(mockMvc.get().uri("/shop/products/99")).hasStatusOk();
        assertThat(mockMvc.get().uri("/shop/products/99")).bodyJson().extractingPath("$.productName").isEqualTo("tuote1");
        assertThat(mockMvc.get().uri("/shop/products/99")).bodyJson().extractingPath("$.id").isEqualTo(99);
    }

    @Test
    void basicWithDifferentSyntaxButWithoutRedundantRequest() {
        MvcTestResult result = mockMvc.get().uri("/shop/products/99").exchange();

        assertThat(result).hasStatus(HttpStatus.OK);
        assertThat(result).bodyJson().extractingPath("$.productName").isEqualTo("tuote1");
    }

    @Test
    void shouldNotFound() {
        mockMvc.get()
            .uri("/shop/products/999")
            .exchange()
            .assertThat()
            .hasStatus(HttpStatus.NOT_FOUND);
    }

    @Test
    void basicSecurityGet() {
        mockMvc.get()
            .uri("/products/99")
            .with(jwt())
            .exchange()
            .assertThat()
            .hasStatus(HttpStatus.OK);
    }

    @Test
    void basicSecuritWithoutJwt() {
        mockMvc.get()
            .uri("/products/99")
            .exchange()
            .assertThat()
            .hasStatus(HttpStatus.UNAUTHORIZED);
    }

}
