package fi.pink.resourceserver.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class ProductMocMvcTesterTests {

    @Autowired
    MockMvcTester tester;

    @Test
    void basicTestWithMMTester() {
        tester.get()
            .uri("/shop/products/99")
            .exchange()
            .assertThat()
            .hasStatus(HttpStatus.OK);
//            .bodyJson()
    }
}
