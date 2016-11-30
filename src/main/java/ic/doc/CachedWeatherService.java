package ic.doc;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class CachedWeatherService implements WeatherService {

    private LinkedHashMap<Map<String, DayOfWeek>, Integer> weatherQueries;
    private WeatherService upperStream;
    private int size;
    private int timer = 0;

    public CachedWeatherService(WeatherService upperStream, final int size) {
        this.upperStream = upperStream;
        this.size = size;
        this.weatherQueries = new LinkedHashMap<Map<String, DayOfWeek>, Integer>() {
          @Override
            public boolean removeEldestEntry(Map.Entry eldest) {
              return size() > size;
          }
        };
    }

    @Override
    public Integer getWeatherForecast(String region, DayOfWeek day) {
        Map<String, DayOfWeek> map = new HashMap<>();
        map.put(region, day);

        checkTime();

        if(!weatherQueries.containsKey(map)) {
            weatherQueries.put(map, upperStream.getWeatherForecast(region, day));
        }

        return weatherQueries.get(map);
    }

    public int size() {
        return weatherQueries.size();
    }

    public void checkTime(){
        if(timer == 60){
            weatherQueries.clear();
        }
    }

    public void setTimer(int time) {
        this.timer = time;
    }

}
