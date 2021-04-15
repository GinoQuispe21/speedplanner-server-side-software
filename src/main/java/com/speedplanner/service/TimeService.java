package com.speedplanner.service;

import com.speedplanner.model.Time;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface TimeService {
    ResponseEntity<?> deleteTime(Long timeId);
    Time updateTime(Long timeId, Time timeRequest);
    Time createTime(Time time);
    Time getTimeById(Long timeId);
    Page<Time> getAllTimes(Pageable pageable);
}
