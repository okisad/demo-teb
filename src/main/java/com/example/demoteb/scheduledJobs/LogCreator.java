package com.example.demoteb.scheduledJobs;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@Slf4j
public class LogCreator {

    public final static String ISTANBUL = "Istanbul";
    public final static String TOKYO = "Tokyo";
    public final static String MOSCOW = "Moscow";
    public final static String BEIJING = "Beijing";
    public final static String LONDON = "London";

    private final Random random = new Random();

    //private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Scheduled(fixedDelay = 10)
    public void scheduledLogJob(){
        generateLog();
    }

    private void generateLog(){
        List<String> cities = getCityList();
        String city = cities.get(random.nextInt(cities.size()));
        int randomValue = random.nextInt(5);
        if (randomValue == 0)
            infoLog(city);
        else if (randomValue == 1)
            warnLog(city);
        else if (randomValue == 2)
            errorLog(city);
        else if (randomValue == 3)
            debugLog(city);
        else if (randomValue == 4)
            traceLog(city);
    }

    private void infoLog(String city){
        log.info(MarkerFactory.getMarker(city),"Hello-from-"+city);
    }
    private void warnLog(String city){
        log.warn(MarkerFactory.getMarker(city),"Hello-from-"+city);
    }
    private void errorLog(String city){
        log.error(MarkerFactory.getMarker(city),"Hello-from-"+city);
    }
    private void debugLog(String city){
        log.debug(MarkerFactory.getMarker(city),"Hello-from-"+city);
    }
    private void traceLog(String city){
        log.trace(MarkerFactory.getMarker(city),"Hello-from-"+city);
    }

    private List<String> getCityList(){
        List<String> cityList = new ArrayList<>();
        cityList.add(ISTANBUL);
        cityList.add(TOKYO);
        cityList.add(MOSCOW);
        cityList.add(BEIJING);
        cityList.add(LONDON);
        return cityList;
    }
}
