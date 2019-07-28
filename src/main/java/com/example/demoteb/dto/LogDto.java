package com.example.demoteb.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class LogDto {

    private Date date;
    private int count;
    private String name;

    public static LogDto newInstance(Date date,int count,String name){
        LogDto logDto = new LogDto();
        logDto.setCount(count);
        logDto.setDate(date);
        logDto.setName(name);
        return logDto;
    }
}
