package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    PetService petService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = convertDTOToEntity(scheduleDTO);
        return convertEntityToDTO(scheduleService.createSchedule(schedule));
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return convertEntitiesToDTOs(scheduleService.getAllSchedules());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        return convertEntitiesToDTOs(scheduleService.getScheduleForPet(petId));
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return convertEntitiesToDTOs(scheduleService.getScheduleForEmployee(employeeId));
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        return convertEntitiesToDTOs(scheduleService.getScheduleForCustomer(customerId));
    }

    // note the difference between fields of ScheduleDTO and Schedule: List<Employee/Pet> and List<Long> of their IDs
    public ScheduleDTO convertEntityToDTO (Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);
        if (schedule.getEmployees() != null) {
            List<Long> employeesIds = new ArrayList<>();
            for (Employee singleEmployee : schedule.getEmployees()) {
                employeesIds.add(singleEmployee.getId());
            }
            scheduleDTO.setEmployeeIds(employeesIds);
        }
        if (schedule.getPets() != null) {
            List<Long> petsIds = new ArrayList<>();
            for (Pet singlePet : schedule.getPets()) {
                petsIds.add(singlePet.getId());
            }
            scheduleDTO.setPetIds(petsIds);
        }
        return scheduleDTO;
    }

    // note the difference between fields of ScheduleDTO and Schedule: List<Employee/Pet> and List<Long> of their IDs
    public Schedule convertDTOToEntity (ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);
        if (scheduleDTO.getEmployeeIds() != null) {
            List<Employee> employees = new ArrayList<>();
            for (Long singleEmployeeId : scheduleDTO.getEmployeeIds()) {
                employees.add(employeeService.getEmployeeByEmployeeId(singleEmployeeId));
            }
            schedule.setEmployees(employees);
        }
        if (scheduleDTO.getPetIds() != null) {
            List<Pet> pets = new ArrayList<>();
            for (Long singlePetId : scheduleDTO.getPetIds()) {
                pets.add(petService.getPetByPetId(singlePetId));
            }
            schedule.setPets(pets);
        }
        return schedule;
    }

    public List<ScheduleDTO> convertEntitiesToDTOs (List<Schedule> schedules) {
        List<ScheduleDTO> res = new ArrayList<>();
        for (Schedule singleSchedule : schedules) {
            res.add(convertEntityToDTO(singleSchedule));
        }
        return res;
    }
}
