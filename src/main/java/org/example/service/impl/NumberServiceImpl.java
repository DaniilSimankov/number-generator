package org.example.service.impl;

import org.example.exception.NumberLimitException;
import org.example.service.NumberService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NumberServiceImpl implements NumberService {

    private static final String REGION = " 116 RUS";
    private static final int MIN_NUMBER = 0, MAX_NUMBER = 999;


    // Отсортированный массив букв
    private static final String[] letters = {"А", "В", "Е", "К", "М", "Н", "О", "Р", "С", "Т", "У", "Х"};
    // HashMap для хранения ранее сгенерированных номеров. (Из альтернатив можно использовать TreeSet)
    private final HashMap<String, List<Integer>> randomNumbers = new HashMap<>();

    private int currentNumber = MIN_NUMBER, firstLetter = 0, secondLetter = 0, thirdLetter = 0;


    @Override
    public String generateNextNumber() {

        StringBuilder result = new StringBuilder();

        // Пока нам попадаются уже существующие номера, мы инкрементируем
        while (checkIfExist(letters[firstLetter] + letters[secondLetter] + letters[thirdLetter], currentNumber)) {
            incrementNumber();
        }

        result.append(letters[firstLetter]);
        result.append(appendZero(currentNumber));
        result.append(currentNumber);
        result.append(letters[secondLetter]);
        result.append(letters[thirdLetter]);

        incrementNumber();

        result.append(REGION);

        return result.toString();
    }

    @Override
    public String generateRandomNumber() {
        StringBuilder result = new StringBuilder();

        Random random = new Random();

        String firstRandomLetter, secondRandomLetter, thirdRandomLetter;
        Integer randomNumber;

        // Генерация случайных букв и цифр
        do {
            firstRandomLetter = letters[random.nextInt(firstLetter, letters.length)];
            secondRandomLetter = letters[random.nextInt(secondLetter, letters.length)];
            thirdRandomLetter = letters[random.nextInt(thirdLetter, letters.length)];

            // Если буквенная часть номера совпадает с текущей, то будем генерировать рандомный номер в возможных пределах
            if (letters[firstLetter].equals(firstRandomLetter) && letters[secondLetter].equals(secondRandomLetter)
                    && letters[thirdLetter].equals(thirdRandomLetter)) {
                randomNumber = random.nextInt(currentNumber, MAX_NUMBER + 1);

                // Если сгенерированный номер совпадает с будущим /next, инкрементируем
                if (currentNumber == randomNumber) {
                    incrementNumber();
                }

            } else {
                // Иначе генерируем численную часть в полных пределах
                randomNumber = random.nextInt(MIN_NUMBER, MAX_NUMBER);
            }

        } while (checkIfExist(firstRandomLetter + secondRandomLetter + thirdRandomLetter, randomNumber));

        result.append(firstRandomLetter);
        result.append(appendZero(randomNumber));
        result.append(randomNumber);
        result.append(secondRandomLetter);
        result.append(thirdRandomLetter);

        result.append(REGION);

        randomNumbers.computeIfAbsent(firstRandomLetter + secondRandomLetter + thirdRandomLetter, k -> new ArrayList<>());
        randomNumbers.get(firstRandomLetter + secondRandomLetter + thirdRandomLetter).add(randomNumber);

        return result.toString();
    }

    /**
     * Добавление нулей в начало номера, если это необходимо
     * @param number - текущий номер
     * @return "00" - если число меньше 10, "0" - если число меньше 100
     */
    private static String appendZero(Integer number) {
        if (number < 10)
            return "00";

        if (number < 100)
            return "0";

        return "";
    }

    /**
     * Функция для проверки позиции буквы в массиве.
     *
     * @param position текущий номер позиции буквы в массиве letters
     * @return true - если позиция буквы превысила допустимые значения,
     * false - иначе
     */
    private static boolean isGreaterThanLettersSize(int position) {
        return position > letters.length - 1;
    }

    /**
     * Функция для проверки числа.
     *
     * @param number текущий номер
     * @return true - если номер превысила допустимые значения, false - иначе
     */
    private static boolean isGreaterThanMaxNumber(int number) {
        return number > MAX_NUMBER;
    }

    /**
     * Функция проверки номера на наличие в списке ранее генерированных номеров.
     *
     * @param key    буквенная часть номера
     * @param number численная часть номера
     * @return true - если номер находится в списке, false - иначе
     */
    private boolean checkIfExist(String key, Integer number) {
        return randomNumbers.get(key) != null && randomNumbers.get(key).contains(number);
    }


    /**
     * Функция инкрементирования номера
     */
    private void incrementNumber() {
        currentNumber++;

        if (isGreaterThanMaxNumber(currentNumber)) {
            currentNumber = MIN_NUMBER;

            clearOldRandomNumbers();

            thirdLetter++;

            if (isGreaterThanLettersSize(thirdLetter)) {
                thirdLetter = 0;
                secondLetter++;
            }

            if (isGreaterThanLettersSize(secondLetter)) {
                secondLetter = 0;
                firstLetter++;
            }

            if (isGreaterThanLettersSize(firstLetter)) {
                // Сбрасываем счетчик
                firstLetter = 0;

                // Выкидываем ошибку. Может содержать другую бизнес-логику
                throw new NumberLimitException();
            }
        }
    }

    /**
     * Функция очистки старых сгенерированных записей в randomNumbers.
     */
    private void clearOldRandomNumbers() {
        randomNumbers.remove(letters[firstLetter] + letters[secondLetter] + letters[thirdLetter]);
    }
}
