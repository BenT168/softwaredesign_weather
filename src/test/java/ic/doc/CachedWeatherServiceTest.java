package ic.doc;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.time.DayOfWeek;
import static org.junit.Assert.assertTrue;


public class CachedWeatherServiceTest {

    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    public WeatherService upperStream = context.mock(WeatherService.class);

    @Test
    public void doesNotContainTheSameQuery() {
        CachedWeatherService cachedWeatherService =
                new CachedWeatherService(upperStream, 10);
        checkLondonForecast(cachedWeatherService);
    }

    @Test
    public void deleteTheMostRecentQuery() {
        CachedWeatherService cachedWeatherService =
                new CachedWeatherService(upperStream, 1);
        checkTwoLocationForecast(cachedWeatherService);
    }

    @Test
    public void isCacheExpiredAfterAnHour() {
        int time = 60;
        CachedWeatherService cachedWeatherService =
                new CachedWeatherService(upperStream, 4);
        checkTwoLocationForecast(cachedWeatherService);
        assertTrue(cachedWeatherService.size() == 2);

        cachedWeatherService.setTimer(time);
        cachedWeatherService.getWeatherForecast("GLASGLOW", DayOfWeek.FRIDAY);
        assertTrue(cachedWeatherService.size() == 1);
    }

    private void checkLondonForecast(CachedWeatherService cachedWeatherService) {
        context.checking(new Expectations() {{
            exactly(1).of(upperStream).getWeatherForecast("LONDON", DayOfWeek.FRIDAY);
        }});
        cachedWeatherService.getWeatherForecast("LONDON", DayOfWeek.FRIDAY);
        cachedWeatherService.getWeatherForecast("LONDON", DayOfWeek.FRIDAY);
        cachedWeatherService.getWeatherForecast("LONDON", DayOfWeek.FRIDAY);
    }

    private void checkTwoLocationForecast(CachedWeatherService cachedWeatherService){
        context.checking(new Expectations() {{
            exactly(2).of(upperStream).getWeatherForecast("GLASGLOW", DayOfWeek.FRIDAY);
            exactly(1).of(upperStream).getWeatherForecast("EDINBURGH", DayOfWeek.FRIDAY);
        }});
        cachedWeatherService.getWeatherForecast("GLASGLOW", DayOfWeek.FRIDAY);
        cachedWeatherService.getWeatherForecast("EDINBURGH", DayOfWeek.FRIDAY);
        cachedWeatherService.getWeatherForecast("GLASGLOW", DayOfWeek.FRIDAY);
    }



}
