package com.inshur.weatherapi.infrastructure;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inshur.weatherapi.application.WeatherDataService;
import com.inshur.weatherapi.domain.WeatherForecast;
import com.inshur.weatherapi.domain.WeatherForecast.WeatherForecastDay;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;

@Component
public class OpenWeatherMapDataService implements WeatherDataService {
    private final WebClient client;

    private final String key;

    public OpenWeatherMapDataService(@Value("${open-weather-map.url}") String url,
                                     @Value("${open-weather-map.key}") String key) {
        this.client = WebClient.create(url);
        this.key = key;
    }

    @Override
    public List<WeatherForecastDay> retrieveWeatherData(BigDecimal latitude, BigDecimal longitude) {
        ForecastResponse response = client.get().uri(uriBuilder -> uriBuilder
                        .queryParam("lat", latitude)
                        .queryParam("lon", longitude)
                        .queryParam("appid", key)
                        .build())
                .retrieve()
                .bodyToMono(ForecastResponse.class)
                .share()
                .block();
        Map<LocalDate, List<Forecast>> forecastsPerDay =
                response.forecasts.stream()
                        .collect(groupingBy(f -> Instant.ofEpochSecond(f.timeStamp).atZone(ZoneId.systemDefault()).toLocalDate()));
        return forecastsPerDay.entrySet().stream().map(e ->
                        new WeatherForecastDay(
                                e.getKey(),
                                e.getValue().stream().max(comparing(f -> f.main.temperature)).orElseThrow().main.temperature,
                                e.getValue().stream().max(comparing(f -> f.main.humidity)).orElseThrow().main.humidity,
                                response.city.countryCode))
                .collect(Collectors.toList());
    }

    public record ForecastResponse(@JsonProperty("list") List<Forecast> forecasts, City city) {
    }

    public record Forecast(@JsonProperty("dt") Integer timeStamp, Main main) {
    }

    public record Main(@JsonProperty("temp") BigDecimal temperature, Integer humidity) {
    }

    public record City(@JsonProperty("country") String countryCode) {
    }
}
