package com.example.demoteb.service;

import com.example.demoteb.data.entity.LogEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class LogParser {

    private static final Pattern logPattern = Pattern.compile("^([0-9]+-[0-9]+-[0-9]+\\s[0-9]+:[0-9]+:[0-9]+)\\s+(.+?)\\s+(.+?)\\s+(Hello.*)$");
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public LogEntity parseLogString(String logString){
        Matcher matcher = logPattern.matcher(logString);
        if (!matcher.find()) return null;
        String strDate = matcher.group(1);
        String level = matcher.group(2);
        String city = matcher.group(3);
        String message = matcher.group(4);
        Date date = null;
        try {
            date = simpleDateFormat.parse(strDate);
        } catch (ParseException e) {
            log.error(logString,"There is an error occurred when parsing date");
        }
        return LogEntity.newInstance(level,city,message,date);
    }
}
