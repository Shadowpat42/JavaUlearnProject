package org.example.parser;

import com.opencsv.CSVReader;
import org.example.model.Country;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CountryDataParser {
    public static List<Country> parseCSV(String filePath) {
        List<Country> countries = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] line;
            reader.readNext(); // Пропускаем заголовок
            while ((line = reader.readNext()) != null) {
                String country = line[0];
                String subregion = line[1];
                String region = line[2];

                // Проверяем и обрабатываем пустые значения
                long internetUsers = parseLongOrDefault(line[3], 0);
                long population = parseLongOrDefault(line[4], 0);

                countries.add(new Country(country, subregion, region, internetUsers, population));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return countries;
    }

    private static long parseLongOrDefault(String value, long defaultValue) {
        if (value == null || value.trim().isEmpty()) {
            return defaultValue;
        }
        return Long.parseLong(value.replace(",", ""));
    }
}
