package org.example;

import org.example.database.DatabaseHandler;
import org.example.model.Country;
import org.example.parser.CountryDataParser;
import org.example.visualization.ChartBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        String csvFilePath = "src/main/resources/Country.csv";

        // 1. Парсинг CSV
        List<Country> countries = CountryDataParser.parseCSV(csvFilePath);

        // 2. Работа с БД
        DatabaseHandler.createDatabase();
        DatabaseHandler.insertData(countries);

        // 3. График процентного соотношения пользователей интернета
        Map<String, Double> subregionData = new HashMap<>();
        for (Country country : countries) {
            double percentage = country.getInternetUserPercentage();
            if (Double.isFinite(percentage) && percentage > 0) { // Исключаем некорректные значения
                subregionData.merge(
                        country.getSubregion(),
                        percentage,
                        Double::sum
                );
            }
        }

        if (subregionData.isEmpty()) {
            System.out.println("Нет корректных данных для построения графика.");
        } else {
            ChartBuilder.createChart(subregionData, "Internet Users by Subregion");
        }

        // 4. Страна с наименьшим количеством пользователей интернета в Восточной Европе
        Country minInternetUsersCountry = countries.stream()
                .filter(country -> "Eastern Europe".equals(country.getSubregion()))
                .min((c1, c2) -> Long.compare(c1.getInternetUsers(), c2.getInternetUsers()))
                .orElse(null);

        if (minInternetUsersCountry != null) {
            System.out.println("Страна с наименьшим количеством пользователей интернета в Восточной Европе: " +
                    minInternetUsersCountry.getName());
        } else {
            System.out.println("Нет данных для Восточной Европы.");
        }

        // 5. Страны с процентом пользователей интернета от 75% до 85%
        List<Country> countriesInRange = countries.stream()
                .filter(country -> {
                    double percentage = country.getInternetUserPercentage();
                    return percentage >= 75 && percentage <= 85;
                })
                .toList();

        if (!countriesInRange.isEmpty()) {
            System.out.println("Страны с процентом пользователей интернета от 75% до 85%:");
            for (Country country : countriesInRange) {
                System.out.println(country.getName() + " - " + String.format("%.2f%%", country.getInternetUserPercentage()));
            }
        } else {
            System.out.println("Нет стран с процентом пользователей интернета в диапазоне от 75% до 85%.");
        }
    }
}
