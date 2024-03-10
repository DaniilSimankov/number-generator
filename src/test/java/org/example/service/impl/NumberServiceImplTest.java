package org.example.service.impl;

import org.example.service.impl.NumberServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NumberServiceImplTest {

    @Mock
    NumberServiceImpl numberService;

    @Test
    void generateNextNumber() {
        when(numberService.generateNextNumber()).thenReturn("A000AA 116 RUS");
        String result = numberService.generateNextNumber();
        assertEquals("A000AA 116 RUS", result);
    }

    @Test
    void generateRandomNumber() {
        when(numberService.generateRandomNumber()).thenReturn("C999BA 116 RUS");
        String result = numberService.generateRandomNumber();
        assertEquals("C999BA 116 RUS", result);
    }
}