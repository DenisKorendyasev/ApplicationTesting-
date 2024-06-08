package com.skillbox.fibonacci;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.Assert.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FibonacciRepositoryTest extends PostgresTestContainerInitializer {

    @Autowired
    FibonacciRepository repository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Check the saving of the new Fibonacci number to the database")
    public void testSaveNewNumberFibonacci() {
        FibonacciNumber number = new FibonacciNumber(8, 21);
        repository.save(number);
        entityManager.flush();
        List<FibonacciNumber> actual = jdbcTemplate.query(
                "SELECT * FROM fibonacci_number",
                (rs, rowNum) -> new FibonacciNumber(rs.getInt("index"), rs.getInt("value"))
        );
        int numberFibonacci = actual.get(0).getValue();
        assertEquals(21, numberFibonacci);
        entityManager.detach(number);
    }

    @Test
    @DisplayName("Test for getting the Fibonacci number from the database")
    public void testGetNumberFibonacci() {
        FibonacciNumber number = new FibonacciNumber(8, 21);
        repository.save(number);
        entityManager.flush();


        int numberFibonacci = repository.findByIndex(8).get().getValue();
        assertEquals(21, numberFibonacci);
        entityManager.detach(number);
    }

    @Test
    @DisplayName("The same addition of the Fibonacci number")
    public void testTheSameAdditionOfTheFibonacciNumber() {
        FibonacciNumber fibonacciNumber = new FibonacciNumber(8, 21);
        repository.save(fibonacciNumber);
        repository.save(fibonacciNumber);

        List<FibonacciNumber> actual = jdbcTemplate.query(
                "SELECT * FROM fibonacci_number",
                (rs, rowNum) -> new FibonacciNumber(rs.getInt("index"), rs.getInt("value"))
        );
        assertEquals(1, actual.size());
        entityManager.detach(fibonacciNumber);
    }


}
