package com.udacity.jdnd.course3.critter.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    public Schedule getScheduleByScheduleId (Long scheduleId) {
        return scheduleRepository.findById(scheduleId).get();
    }

    public Schedule createSchedule (Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules () {
        Iterable<Schedule> iterableResult = scheduleRepository.findAll();
        return iterableToList(iterableResult);
    }

    public List<Schedule> getScheduleForPet (Long petId) {
        return scheduleRepository.findAllByPetId(petId);
    }

    public List<Schedule> getScheduleForEmployee (Long employeeId) {
        return scheduleRepository.findAllByEmployeeId(employeeId);
    }

    public List<Schedule> getScheduleForCustomer (Long customerId) {
        return scheduleRepository.findAllByCustomerId(customerId);
    }

    public List<Schedule> iterableToList (Iterable<Schedule> schedules) {
        List<Schedule> res = new ArrayList<>();
        schedules.forEach(schedule -> {
            res.add(schedule);
        });
        return res;
    }
}
