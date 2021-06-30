package com.speedplanner.service;

import com.speedplanner.exception.ResourceNotFoundException;
import com.speedplanner.model.StudyGroup;
import com.speedplanner.model.Time;
import com.speedplanner.repository.CourseRepository;
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

    @Autowired
    private CourseRepository courseRepository;


    @Override
    public ResponseEntity<?> deleteTime(Long courseId, Long timeId) {
        return courseRepository.findById(courseId).map(course -> {
            Time time= timeRepository.findById(timeId).orElseThrow(() ->
                    new ResourceNotFoundException("Group", "Id", timeId));
            timeRepository.delete(time);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Course not found with Id: "+courseId));
    }

    @Override
    public Time updateTime(Long courseId, Long timeId, Time timeRequest) {
        if(!courseRepository.existsById(courseId))
            throw new ResourceNotFoundException("Course", "Id", courseId);

        return timeRepository.findById(timeId).map(time -> {
            time.setDay(timeRequest.getDay());
            time.setStartTime(timeRequest.getStartTime());
            time.setFinishTime(timeRequest.getFinishTime());
            return timeRepository.save(time);
        }).orElseThrow(() -> new ResourceNotFoundException("Time", "Id", timeId));

    }

    @Override
    public Time createTime(Long courseId, Time time) {
        return courseRepository.findById(courseId).map(course -> {
            time.setCourse(course);
            return timeRepository.save(time);
        }).orElseThrow(() -> new ResourceNotFoundException("Course", "Id", courseId));
    }

    @Override
    public Time getTimeByIdAndCourseId(Long courseId, Long timeId) {
        if (!courseRepository.existsById(courseId))
            throw new ResourceNotFoundException("Course not found with Id: "+courseId);
        return timeRepository.findByIdAndCourseId(timeId, courseId);
    }

    @Override
    public Page<Time> getAllTimesByCourseId(Long courseId, Pageable pageable) {
        if (!courseRepository.existsById(courseId))
            throw new ResourceNotFoundException("Course", "Id", courseId);
        return timeRepository.findAllByCourseId(courseId, pageable);
    }


}
