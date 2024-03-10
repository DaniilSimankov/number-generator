package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.exception.NumberLimitException;
import org.example.service.NumberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/number")
@RequiredArgsConstructor
public class NumberController {

    private final NumberService numberService;

    @GetMapping("/next")
    public ResponseEntity<String> getNextNumber() {

        try {
            log.info("Start generating the next number...");
            String number = numberService.generateNextNumber();
            log.info("Generated the next number: {}", number);
            return ResponseEntity.ok().body(number);
        } catch (NumberLimitException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body("Oops...");
        }

    }

    @GetMapping("/random")
    public ResponseEntity<String> getRandomNumber() {

        try {
            log.info("Start generating the random number...");
            String number = numberService.generateRandomNumber();
            log.info("Generated the random number: {}", number);

            return ResponseEntity.ok().body(number);
        } catch (NumberLimitException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body("Oops...");
        }
    }


}
