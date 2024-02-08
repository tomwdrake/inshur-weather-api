package com.inshur.weatherapi.infrastructure;

import com.inshur.weatherapi.application.WeatherDataService;
import com.inshur.weatherapi.application.WeatherForecastApplicationService;
import com.inshur.weatherapi.application.WeatherForecastService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WeatherApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherApiApplication.class, args);
    }

    @Bean
    WeatherForecastService weatherForecastService(WeatherDataService weatherDataService) {
        return new WeatherForecastApplicationService(weatherDataService);
    }

}
