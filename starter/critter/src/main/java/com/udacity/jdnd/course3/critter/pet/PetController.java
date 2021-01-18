package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetService petService;

    @Autowired
    CustomerService customerService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = convertPetDTOToEntity(petDTO);
        return convertEntityToPetDTO(petService.savePet(pet));
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return convertEntityToPetDTO(petService.getPetByPetId(petId));
    }

    @GetMapping
    public List<PetDTO> getPets(){
        return convertEntitiesToDTOs(petService.getAllPets());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        return convertEntitiesToDTOs(petService.getPetsByOwnerId(ownerId));
    }

    // note the difference between fields of PetDTO and Pet: customerId and customer
    public PetDTO convertEntityToPetDTO (Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        if (pet.getCustomer() != null) {
            petDTO.setCustomerId(pet.getCustomer().getId());
        }
        return petDTO;
    }

    // note the difference between fields of PetDTO and Pet: customerId and customer
    public Pet convertPetDTOToEntity (PetDTO petDTO) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        if (petDTO.getCustomerId() > 0) {
            pet.setCustomer(customerService.getCustomerByCustomerId(petDTO.getCustomerId()));
        }
        return pet;
    }

    public List<PetDTO> convertEntitiesToDTOs (List<Pet> pets) {
        List<PetDTO> res = new ArrayList<>();
        for (Pet singlePet : pets) {
            res.add(convertEntityToPetDTO(singlePet));
        }
        return res;
    }
}
