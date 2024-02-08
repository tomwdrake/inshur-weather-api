package com.inshur.weatherapi.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.Map.Entry.comparingByKey;
import static java.util.stream.Collectors.groupingBy;

public class WeatherForecast {
    private static final Set<String> ALLOWED_COUNTRY_CODES = Set.of("GB");
    private Long forecastId;
    private final List<WeatherForecastDay> fiveDayWeather;

    public WeatherForecast(List<WeatherForecastDay> fiveDayWeather) {
        this.fiveDayWeather = fiveDayWeather;
    }

    public record WeatherForecastDay(LocalDate date, BigDecimal temperature, Integer humidity, String locationCountryCode) {
    }

    public WeatherForecastDay calculateWarmestDay() {
        String locationCountryCode = fiveDayWeather.getFirst().locationCountryCode;
        if (!ALLOWED_COUNTRY_CODES.contains(locationCountryCode)) {
            throw new InvalidLocationException(locationCountryCode);
        }

        Map<BigDecimal, List<WeatherForecastDay>> daysGroupedByTemperature =
                fiveDayWeather
                        .stream()
                        .collect(groupingBy(f -> f.temperature));
        List<WeatherForecastDay> jointHottestDays =
                daysGroupedByTemperature.entrySet()
                        .stream()
                        .sorted(comparingByKey())
                        .collect(Collectors.toList())
                        .reversed()
                        .getFirst()
                        .getValue();
        Map<Integer, List<WeatherForecastDay>> hottestDaysGroupedByHumidity = jointHottestDays
                .stream()
                .collect(groupingBy(f -> f.humidity));
        List<WeatherForecastDay> jointMostHumidDays =
                hottestDaysGroupedByHumidity.entrySet()
                        .stream()
                        .sorted(comparingByKey())
                        .collect(Collectors.toList())
                        .reversed()
                        .getFirst()
                        .getValue();
        return jointMostHumidDays.stream().min(comparing(WeatherForecastDay::date)).orElseThrow();
    }

    public static class InvalidLocationException extends RuntimeException {
        public InvalidLocationException(String countryCode) {
            super(String.format("Country code %s is not allowed", countryCode));
        }
    }

}
