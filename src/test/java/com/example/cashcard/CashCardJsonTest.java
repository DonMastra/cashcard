package com.example.cashcard;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class CashCardJsonTest {

    @Autowired
    private JacksonTester<CashCard> jsonTester;

    @Autowired
    private JacksonTester<CashCard[]> jsonList;

    private CashCard[] cashCards;

    @BeforeEach
    void setUp() {
        cashCards = Arrays.array(
                new CashCard(99L, 123.45),
                new CashCard(100L, 1.00),
                new CashCard(101L, 150.00));
    }

    @Test
    void cashCardListSerializationTest() throws IOException {
        assertThat(jsonList.write(cashCards)).isStrictlyEqualToJson("list.json");
    }

    @Test
    void cashCardListDeserializationTest() throws IOException {
        String expected="""
                [
                    { "id": 99, "amount": 123.45 },
                    { "id": 100, "amount": 1.00 },
                    { "id": 101, "amount": 150.00 }
                ]
                """;
        assertThat(jsonList.parse(expected)).isEqualTo(cashCards);
    }

    @Test
    void cashCardSerializationTest() throws IOException {
        CashCard cashCard = new CashCard(99L, 123.45);

        assertThat(jsonTester.write(cashCard)).isStrictlyEqualToJson("single.json");
        assertThat(jsonTester.write(cashCard)).hasJsonPathNumberValue("@.id");
        assertThat(jsonTester.write(cashCard)).extractingJsonPathNumberValue("@.id")
                .isEqualTo(99);
        assertThat(jsonTester.write(cashCard)).hasJsonPathNumberValue("@.amount");
        assertThat(jsonTester.write(cashCard)).extractingJsonPathNumberValue("@.amount")
                .isEqualTo(123.45);
    }

    @Test
    void cashCardDeserializationTest() throws IOException {
        String expected = """
           {
               "id":99,
               "amount":123.45
           }
           """;
        assertThat(jsonTester.parse(expected))
                .isEqualTo(new CashCard(99L, 123.45));
        assertThat(jsonTester.parseObject(expected).id()).isEqualTo(99L);
        assertThat(jsonTester.parseObject(expected).amount()).isEqualTo(123.45);
    }

    /*
    @Test
    void checkResource() {
        var url = Thread.currentThread().getContextClassLoader()
                .getResource("com/example/cashcard/single.json");
        System.out.println("resource url = " + url);
        assertThat(url).isNotNull();
    }
    */


    @Test
    void initialTest() {
        assertThat(42).isEqualTo(42);
    }
}
