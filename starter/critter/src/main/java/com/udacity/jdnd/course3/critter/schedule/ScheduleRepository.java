package com.udacity.jdnd.course3.critter.schedule;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ScheduleRepository extends CrudRepository<Schedule, Long> {

    List<Schedule> findAllByPetId (Long petId);

    List<Schedule> findAllByEmployeeId (Long employeeId);

    List<Schedule> findAllByCustomerId (Long customerId);
}
