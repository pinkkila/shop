package fi.pink.resourceserver.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class ProductJsonTest {

    @Autowired
    private JacksonTester<Product> json;

    @Test
    void productSerializationTest() throws IOException {
        Product product = new Product(1L, "tuote1");
        assertThat(json.write(product)).isStrictlyEqualToJson("product.json");
        assertThat(json.write(product)).hasJsonPathNumberValue("@.id");
        assertThat(json.write(product)).extractingJsonPathNumberValue("@.id")
            .isEqualTo(1);
        assertThat(json.write(product)).hasJsonPathStringValue("@.productName");
        assertThat(json.write(product)).extractingJsonPathStringValue("@.productName")
            .isEqualTo("tuote1");
    }

    @Test
    void productDeserializationTest() throws IOException {
        String expected = """
            {
                "id": 1,
                "productName": "tuote1"
            }
            """;
        assertThat(json.parse(expected))
            .isEqualTo(new Product(1L, "tuote1"));
        assertThat(json.parseObject(expected).getId()).isEqualTo(1);
        assertThat(json.parseObject(expected).getProductName()).isEqualTo("tuote1");
    }

}