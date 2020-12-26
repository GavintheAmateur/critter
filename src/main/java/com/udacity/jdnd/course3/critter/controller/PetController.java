package com.udacity.jdnd.course3.critter.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.udacity.jdnd.course3.critter.controller.dto.PetDto;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.controller.mapstruct.PetMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    private PetService petService;


    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public PetDto savePet(@RequestBody PetDto petDto) {
        Pet pet =  PetMapper.MAPPER.petDtoToPet(petDto);
        Pet pet1 = petService.createPet(pet);
        return PetMapper.MAPPER.petToPetDto(pet1);
    }
    
    @JsonView
    @GetMapping("/{petId}")
    public PetDto getPet(@PathVariable long petId) {
        Pet pet = petService.readPetById(petId);
        return PetMapper.MAPPER.petToPetDto(pet);
    }

    @GetMapping
    public List<PetDto> getPets(){
        List<Pet> pets = petService.listAllPets();
        return pets.stream().map(pet -> PetMapper.MAPPER.petToPetDto(pet)).collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDto> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> petsByOwner = petService.getPetsByOwner(ownerId);
        return petsByOwner.stream().map(pet -> PetMapper.MAPPER.petToPetDto(pet)).collect(Collectors.toList());
    }
}
