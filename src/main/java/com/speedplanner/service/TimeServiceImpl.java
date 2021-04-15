package com.speedplanner.service;

import com.speedplanner.exception.ResourceNotFoundException;
import com.speedplanner.model.Course;
import com.speedplanner.model.Time;
import com.speedplanner.repository.TimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TimeServiceImpl implements TimeService {

    @Autowired
    private TimeRepository timeRepository;

    @Override
    public ResponseEntity<?> deleteTime(Long timeId) {
        Time time = timeRepository.findById(timeId).orElseThrow(() -> new ResourceNotFoundException("Time", "Id", timeId));
        timeRepository.delete(time);
        return ResponseEntity.ok().build();
    }

    @Override
    public Time updateTime(Long timeId, Time timeRequest) {
        Time time = timeRepository.findById(timeId)
                .orElseThrow(() -> new ResourceNotFoundException("Time", "Id", timeId));
        time.setDay(timeRequest.getDay());
        return timeRepository.save(time);
    }

    @Override
    public Time createTime(Time time) {
        return timeRepository.save(time);
    }

    @Override
    public Time getTimeById(Long timeId) {
        return timeRepository.findById(timeId)
                .orElseThrow(() -> new ResourceNotFoundException("Time", "Id", timeId));
    }

    @Override
    public Page<Time> getAllTimes(Pageable pageable) {
        return timeRepository.findAll(pageable);
    }
}
