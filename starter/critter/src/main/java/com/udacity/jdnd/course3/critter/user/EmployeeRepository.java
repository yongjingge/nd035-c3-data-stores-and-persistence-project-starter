package com.udacity.jdnd.course3.critter.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Repository
@Transactional
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    // Find out which employees with the right skills are available on a given date.
    List<Employee> findAllBySkillsInAndDaysAvailable (Set<EmployeeSkill> skills, DayOfWeek dayOfWeek);
}
