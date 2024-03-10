package org.example.service;


import org.example.exception.NumberLimitException;

public interface NumberService {
    String generateNextNumber() throws NumberLimitException;

    String generateRandomNumber() throws NumberLimitException;
}
