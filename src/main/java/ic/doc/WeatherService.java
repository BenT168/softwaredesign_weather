package ic.doc;

import java.time.DayOfWeek;

public interface WeatherService {
    Integer getWeatherForecast(String region, DayOfWeek day);
}
