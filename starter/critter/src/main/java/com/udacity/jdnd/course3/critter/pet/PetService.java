package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PetService {

    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerRepository customerRepository;

    // add new pet, make sure to update its customer if exists
    public Pet savePet (Pet pet) {

        Pet newPetArrived = petRepository.save(pet);
        Customer customer = customerRepository.findCustomerByPetsId(newPetArrived.getId());
        // Customer customer = newPetArrived.getCustomer();
        if (customer != null) {
            customer.getPets().add(newPetArrived);
            // List<Pet> pets = customer.getPets() == null ? new ArrayList<>() :customer.getPets();
            // pets.add(newPetArrived);
            // customer.setPets(pets);
        }
        return newPetArrived;
    }

    public Pet getPetByPetId (Long petId) {
        return petRepository.findById(petId).get();
    }

    public List<Pet> getAllPets () {
        Iterable<Pet> iterableResult = petRepository.findAll();
        return iterableToList(iterableResult);
    }

    public List<Pet> getPetsByOwnerId (Long ownerId) {
        return petRepository.findAllByOwnerNamedQuery(ownerId);
    }

    /**
     * helper method to transform Iterable into List
     * @param pets Iterable
     * @return List
     */
    public List<Pet> iterableToList (Iterable<Pet> pets) {
        List<Pet> res = new ArrayList<>();
        pets.forEach(pet -> {
            res.add(pet);
        });
        return res;
    }
}
