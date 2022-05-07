package com.robo.robo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PhoneService {

    public String formatNumberToSave(String number) {
        String cleanNumber = removeSpecialChars(number);
        return getDDD(cleanNumber) + getNumber(cleanNumber);
    }

    public static String getNumber(String number) {
        return number.substring(number.length() - 8);
    }

    private static String getDDD(String number) {
        if (number.startsWith("55")) {
            return number.substring(2, 4);
        }
        return number.substring(0, 2);
    }

    private static String removeSpecialChars(String number) {
        return number.replaceAll("[^\\w]", "");
    }
}
