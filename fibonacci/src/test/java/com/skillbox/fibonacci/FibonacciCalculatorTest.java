package com.skillbox.fibonacci;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Value;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class FibonacciCalculatorTest {

    private FibonacciCalculator fibonacciCalculator;

    @BeforeEach
    public void setUp() {
        fibonacciCalculator = new FibonacciCalculator();
    }
    @Test
    @DisplayName("Test get Fibonacci number")
    public void testGetFibonacciNumber() {
        int numberFibonacci = fibonacciCalculator.getFibonacciNumber(7);
        assertEquals(13, numberFibonacci);
    }

    @Test
    @DisplayName("Test get Fibonacci number less than one")
    public void testGetFibonacciNumberLessThanOne() {
        assertThrows(IllegalArgumentException.class, () -> fibonacciCalculator.getFibonacciNumber(0));
    }

    @ParameterizedTest(name = "{0} Sequence number")
    @ValueSource(ints = {1, 2})
    @DisplayName("Test for obtaining Fibonacci numbers with ordinal numbers One and Two")
    public void testGetFibonacciNumberOneAndTwo(Integer number) {
        int resultNumber = 1;
        int sequenceNumber = fibonacciCalculator.getFibonacciNumber(number);
        assertEquals(resultNumber, sequenceNumber);
    }

}
