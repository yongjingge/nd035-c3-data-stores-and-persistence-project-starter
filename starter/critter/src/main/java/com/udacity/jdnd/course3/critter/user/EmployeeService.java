package com.udacity.jdnd.course3.critter.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public Employee saveEmployee (Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeByEmployeeId (Long employeeId) {
        return employeeRepository.findById(employeeId).get();
    }

    // set availability of days on target employee
    public void setAvailabilityForEmployee (Set<DayOfWeek> daysAvailable, Long employeeId) {
        Employee targetEmployee = getEmployeeByEmployeeId(employeeId);
        targetEmployee.setDaysAvailable(daysAvailable);
        employeeRepository.save(targetEmployee);
    }

    public List<Employee> findEmployeesForService (Set<EmployeeSkill> skills, DayOfWeek day) {
        List<Employee> candidates = employeeRepository.findAllBySkillsInAndDaysAvailable(skills, day);
        List<Employee> res = new ArrayList<>();
        // deal with the 'findAllBySkillsIn' issue
        for (Employee candidate : candidates) {
            if (candidate.getSkills().containsAll(skills)) {
                res.add(candidate);
            }
        }
        return res;
    }
}
