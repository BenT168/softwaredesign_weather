package ic.doc;

import com.weather.Day;
import com.weather.Forecaster;
import com.weather.Region;

import java.time.DayOfWeek;


public class WeatherServiceClient implements WeatherService {

    @Override
    public Integer getWeatherForecast(String region, DayOfWeek day) {
        return new Forecaster().forecastFor(Region.valueOf(region.toUpperCase()),
                Day.valueOf(day.name().toUpperCase())).temperature();
    }
}
