package com.inshur.weatherapi.application;

import com.inshur.weatherapi.domain.WeatherForecast;
import com.inshur.weatherapi.domain.WeatherForecast.WeatherForecastDay;

import java.math.BigDecimal;
import java.util.List;

public class WeatherForecastApplicationService implements WeatherForecastService {
    private final WeatherDataService weatherDataService;

    public WeatherForecastApplicationService(WeatherDataService weatherDataService) {
        this.weatherDataService = weatherDataService;
    }

    @Override
    public WeatherForecastDay calculateWarmestDay(BigDecimal latitude, BigDecimal longitude) {
        List<WeatherForecastDay> weatherForPeriods = weatherDataService.retrieveWeatherData(latitude, longitude);
        WeatherForecast weatherForecast = new WeatherForecast(weatherForPeriods);
        return weatherForecast.calculateWarmestDay();
    }

}
