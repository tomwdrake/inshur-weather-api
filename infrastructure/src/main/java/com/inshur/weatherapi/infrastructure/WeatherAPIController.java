package com.inshur.weatherapi.infrastructure;

import com.inshur.weatherapi.application.WeatherForecastService;

import com.inshur.weatherapi.domain.WeatherForecast;
import com.inshur.weatherapi.domain.WeatherForecast.WeatherForecastDay;
import jakarta.validation.constraints.Digits;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1")
public class WeatherAPIController {
    private final WeatherForecastService weatherForecastService;

    public WeatherAPIController(WeatherForecastService weatherForecastService) {
        this.weatherForecastService = weatherForecastService;
    }

    @GetMapping("/warmest-day")
    public WeatherForecastDay findWarmestDay(@RequestParam("lat") @Digits(integer = Integer.MAX_VALUE, fraction = 6) BigDecimal latitude,
                                             @RequestParam("lon") @Digits(integer = Integer.MAX_VALUE, fraction = 6) BigDecimal longitude) {
        return weatherForecastService.calculateWarmestDay(latitude, longitude);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(WeatherForecast.InvalidLocationException.class)
    public void invalidLocation() {
    }
}
