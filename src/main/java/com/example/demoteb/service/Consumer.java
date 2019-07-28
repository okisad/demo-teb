package com.example.demoteb.service;


import com.example.demoteb.data.entity.LogEntity;
import com.example.demoteb.data.reporsitory.LogRepository;
import com.example.demoteb.dto.LogDto;
import com.example.demoteb.scheduledJobs.LogCreator;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@Slf4j
public class Consumer {

    @Autowired
    SimpMessagingTemplate template;
    @Autowired
    LogParser logParser;
    @Autowired
    LogRepository logRepository;

    List<LogDto> logs = new ArrayList<>();

    List<String> cities = new ArrayList<String>() {{
                add(LogCreator.TOKYO);
                add(LogCreator.ISTANBUL);
                add(LogCreator.BEIJING);
                add(LogCreator.MOSCOW);
                add(LogCreator.LONDON);
    }};

    private static final Pattern checkLog = Pattern.compile(".*Hello-from.*");

    @KafkaListener(topics = "sql-insert", groupId = "sql")
    public void consume(String message) throws IOException {
        if (checkLog.matcher(message).find()) {
            LogEntity logEntity = logParser.parseLogString(message);
            logRepository.save(logEntity);
            truncateSecondOfDate(logEntity);
            if (logs.size() > 0) {
                LogDto lastLog = logs.get(0);
                if (refreshData(lastLog.getDate(), logEntity.getDate())) {
                    template.convertAndSend("/topic/live", logs);
                    logs = new ArrayList<>();
                    addLog(logEntity);
                } else {
                    addLog(logEntity);
                }
            } else {
                addLog(logEntity);
            }
            //logRepository.save(logEntity);
            //template.convertAndSend("/topic/live",logEntity);
        }
    }

    private void addLog(LogEntity logEntity) {
        if (logs.size() == 0){
            cities.forEach(c -> logs.add(LogDto.newInstance(logEntity.getDate(), 0, c)));
        }
        Optional<LogDto> optionalLogEntity = logs.stream().filter(l -> l.getName().equals(logEntity.getCity())).findAny();
        if (optionalLogEntity.isPresent()) {
            optionalLogEntity.get().setCount(optionalLogEntity.get().getCount() + 1);
        } else {
            logs.add(LogDto.newInstance(logEntity.getDate(), 1, logEntity.getCity()));
        }
    }

    private void truncateSecondOfDate(LogEntity logEntity) {
        DateTime logDate = new DateTime(logEntity.getDate());
        int offset = logDate.getSecondOfMinute() % 5;
        logDate = logDate.minusSeconds(offset);
        logEntity.setDate(logDate.toDate());
    }

    private boolean refreshData(Date lastDate, Date dataDate) {
        DateTime lastLogDate = new DateTime(lastDate);
        DateTime newLogDate = new DateTime(dataDate);
        return (newLogDate.getMillis() - lastLogDate.getMillis()) / 1000 >= 5;
    }
}
