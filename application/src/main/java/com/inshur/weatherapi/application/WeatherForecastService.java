package com.inshur.weatherapi.application;

import com.inshur.weatherapi.domain.WeatherForecast.WeatherForecastDay;

import java.math.BigDecimal;

public interface WeatherForecastService {
    WeatherForecastDay calculateWarmestDay(BigDecimal latitude, BigDecimal longitude);
}
