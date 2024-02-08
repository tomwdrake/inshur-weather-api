package com.inshur.weatherapi.domain;

import com.inshur.weatherapi.domain.WeatherForecast.WeatherForecastDay;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("The WeatherForecast Entity")
class WeatherForecastTest {

    @Nested
    @DisplayName("should calculate the warmest day")
    class ShouldReturnTheWarmestDate {

        @Test
        @DisplayName("when there is a single day with the highest temperature value")
        public void singleHighestTemperature() {
            // given
            List<WeatherForecastDay> fiveDayWeather = List.of(
                    new WeatherForecastDay(LocalDate.of(2024, 1, 1), BigDecimal.valueOf(12.0), 10, "GB"),
                    new WeatherForecastDay(LocalDate.of(2024, 1, 2), BigDecimal.valueOf(10.0), 15, "GB"),
                    new WeatherForecastDay(LocalDate.of(2024, 1, 3), BigDecimal.valueOf(14.0), 19, "GB"),
                    new WeatherForecastDay(LocalDate.of(2024, 1, 4), BigDecimal.valueOf(15.0), 18, "GB"),
                    new WeatherForecastDay(LocalDate.of(2024, 1, 5), BigDecimal.valueOf(12.0), 10, "GB")
            );
            WeatherForecast weatherForecast = new WeatherForecast(fiveDayWeather);
            // when
            WeatherForecastDay localDate = weatherForecast.calculateWarmestDay();
            // then
            assertThat(localDate).isEqualTo(new WeatherForecastDay(LocalDate.of(2024, 1, 4), BigDecimal.valueOf(15.0), 18, "GB"));
        }

        @Test
        @DisplayName("when there are two days with the highest temperature value")
        public void twoDaysWithHighestTemperature() {
            // given
            List<WeatherForecastDay> fiveDayWeather = List.of(
                    new WeatherForecastDay(LocalDate.of(2024, 1, 1), BigDecimal.valueOf(12.0), 10, "GB"),
                    new WeatherForecastDay(LocalDate.of(2024, 1, 2), BigDecimal.valueOf(10.0), 15, "GB"),
                    new WeatherForecastDay(LocalDate.of(2024, 1, 3), BigDecimal.valueOf(15.0), 19, "GB"),
                    new WeatherForecastDay(LocalDate.of(2024, 1, 4), BigDecimal.valueOf(15.0), 18, "GB"),
                    new WeatherForecastDay(LocalDate.of(2024, 1, 5), BigDecimal.valueOf(12.0), 10, "GB")
            );
            WeatherForecast weatherForecast = new WeatherForecast(fiveDayWeather);
            // when
            WeatherForecastDay localDate = weatherForecast.calculateWarmestDay();
            // then
            assertThat(localDate).isEqualTo(new WeatherForecastDay(LocalDate.of(2024, 1, 3), BigDecimal.valueOf(15.0), 19, "GB"));
        }

        @Test
        @DisplayName("when there are two days with the highest temperature value and the same humidity value")
        public void twoDaysWithHighestTemperatureAndHumidity() {
            // given
            List<WeatherForecastDay> fiveDayWeather = List.of(
                    new WeatherForecastDay(LocalDate.of(2024, 1, 1), BigDecimal.valueOf(12.0), 10, "GB"),
                    new WeatherForecastDay(LocalDate.of(2024, 1, 2), BigDecimal.valueOf(10.0), 15, "GB"),
                    new WeatherForecastDay(LocalDate.of(2024, 1, 3), BigDecimal.valueOf(15.0), 19, "GB"),
                    new WeatherForecastDay(LocalDate.of(2024, 1, 4), BigDecimal.valueOf(15.0), 19, "GB"),
                    new WeatherForecastDay(LocalDate.of(2024, 1, 5), BigDecimal.valueOf(12.0), 10, "GB")
            );
            WeatherForecast weatherForecast = new WeatherForecast(fiveDayWeather);
            // when
            WeatherForecastDay localDate = weatherForecast.calculateWarmestDay();
            // then
            assertThat(localDate).isEqualTo(new WeatherForecastDay(LocalDate.of(2024, 1, 3), BigDecimal.valueOf(15.0), 19, "GB"));
        }

    }

    @Test
    void ShouldThrowAnExceptionIfTheLocationIsInvalid() {
        // given
        List<WeatherForecastDay> fiveDayWeather = List.of(
                new WeatherForecastDay(LocalDate.of(2024, 1, 1), BigDecimal.valueOf(12.0), 10, "NR"),
                new WeatherForecastDay(LocalDate.of(2024, 1, 2), BigDecimal.valueOf(10.0), 15, "NR"),
                new WeatherForecastDay(LocalDate.of(2024, 1, 3), BigDecimal.valueOf(15.0), 19, "NR"),
                new WeatherForecastDay(LocalDate.of(2024, 1, 4), BigDecimal.valueOf(15.0), 19, "NR"),
                new WeatherForecastDay(LocalDate.of(2024, 1, 5), BigDecimal.valueOf(12.0), 10, "NR")
        );
        WeatherForecast weatherForecast = new WeatherForecast(fiveDayWeather);
        // when
        // then
        assertThrows(WeatherForecast.InvalidLocationException.class, weatherForecast::calculateWarmestDay);
    }

}
