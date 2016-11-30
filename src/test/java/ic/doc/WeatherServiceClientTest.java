package ic.doc;

import org.junit.Test;

import static java.time.DayOfWeek.*;
import static org.junit.Assert.assertTrue;


public class WeatherServiceClientTest {

    @Test
    public void canClientGetTemperature() {
        WeatherServiceClient weatherServiceClient = new WeatherServiceClient();
        assertTrue(weatherServiceClient.getWeatherForecast("LONDON", FRIDAY)
                instanceof Integer);
    }
}
