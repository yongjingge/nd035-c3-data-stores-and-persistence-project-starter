package com.udacity.jdnd.course3.critter.schedule;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ScheduleRepository extends CrudRepository<Schedule, Long> {

    List<Schedule> findAllByPetsId (Long petId);

    List<Schedule> findAllByEmployeesId (Long employeeId);

    List<Schedule> findAllByPetsCustomerId (Long customerId);
}
