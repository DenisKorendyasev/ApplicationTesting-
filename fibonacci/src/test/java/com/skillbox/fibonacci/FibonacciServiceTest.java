package com.skillbox.fibonacci;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

public class FibonacciServiceTest {

    private FibonacciService service;

    private final FibonacciRepository repository =
            Mockito.mock(FibonacciRepository.class);

    private final FibonacciCalculator calculator =
            Mockito.mock(FibonacciCalculator.class);

    @BeforeEach
    public void setUp(){
        service = new FibonacciService(repository, calculator);
    }


    @Test
    @DisplayName("Test Fibonacci Number from the database")
    public void testFibonacciNumberFromTheDatabase() {
        int index = 8;
        int numberFibonacci = 21; // 1, 1, 2, 3, 5, 8, 13, 21, 34, 55
        when(calculator.getFibonacciNumber(8)).thenReturn(21);
        FibonacciNumber fibonacciNumber = new FibonacciNumber(index, numberFibonacci);

        assertEquals(numberFibonacci, service.fibonacciNumber(index).getValue());
        verify(repository, times(1)).findByIndex(8);
    }

    @Test
    @DisplayName("Fibonacci number is not in the database")
    public void testFibonacciNumberIsNotInTheDatabase() {
        int index = 8;
        int valueFibonacciNumber = 21;
        when(calculator.getFibonacciNumber(index)).thenReturn(valueFibonacciNumber);
        when(repository.findByIndex(index)).thenReturn(Optional.empty());
        int fibonacciNumber = service.fibonacciNumber(index).getValue();

        assertEquals(valueFibonacciNumber, fibonacciNumber);
        verify(calculator, times(1)).getFibonacciNumber(index);
        verify(repository, times(1)).save(any(FibonacciNumber.class));
    }

    @Test
    @DisplayName("Test get Fibonacci number less than one")
    public void testGetFibonacciNumberLessThanOne() {
        int index = -1;
        assertThrows(IllegalArgumentException.class, () -> service.fibonacciNumber(index));
        verify(calculator, never()).getFibonacciNumber(index);
        verify(repository, never()).findByIndex(index);
        verify(repository, never()).save(any(FibonacciNumber.class));
    }

}
