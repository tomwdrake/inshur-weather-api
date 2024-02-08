package com.inshur.weatherapi.application;

import com.inshur.weatherapi.domain.WeatherForecast;

import java.math.BigDecimal;
import java.util.List;

// PORT
public interface WeatherDataService {
    List<WeatherForecast.WeatherForecastDay> retrieveWeatherData(BigDecimal latitude, BigDecimal longitude);
}
