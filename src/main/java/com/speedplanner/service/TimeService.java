package com.speedplanner.service;

import com.speedplanner.model.Time;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface TimeService {
    ResponseEntity<?> deleteTime(Long courseId, Long timeId);
    Time updateTime(Long courseId, Long timeId, Time timeRequest);
    Time createTime(Long courseId, Time time);
    Time getTimeByIdAndCourseId(Long courseId, Long timeId);
    Page<Time> getAllTimesByCourseId(Long courseId, Pageable pageable);
}
