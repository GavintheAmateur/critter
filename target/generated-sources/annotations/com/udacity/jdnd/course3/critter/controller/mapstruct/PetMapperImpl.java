package com.udacity.jdnd.course3.critter.controller.mapstruct;

import com.udacity.jdnd.course3.critter.controller.dto.PetDto;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-12-26T11:47:45+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 11.0.8 (AdoptOpenJDK)"
)
public class PetMapperImpl implements PetMapper {

    @Override
    public PetDto petToPetDto(Pet pet) {
        if ( pet == null ) {
            return null;
        }

        PetDto petDto = new PetDto();

        petDto.setOwnerId( petCustomerId( pet ) );
        petDto.setType( pet.getType() );
        petDto.setName( pet.getName() );
        petDto.setBirthDate( pet.getBirthDate() );
        petDto.setNotes( pet.getNotes() );
        petDto.setId( pet.getId() );

        return petDto;
    }

    @Override
    public Pet petDtoToPet(PetDto petDto) {
        if ( petDto == null ) {
            return null;
        }

        Pet pet = new Pet();

        pet.setCustomer( petDtoToCustomer( petDto ) );
        pet.setId( petDto.getId() );
        pet.setType( petDto.getType() );
        pet.setName( petDto.getName() );
        pet.setBirthDate( petDto.getBirthDate() );
        pet.setNotes( petDto.getNotes() );

        return pet;
    }

    private long petCustomerId(Pet pet) {
        if ( pet == null ) {
            return 0L;
        }
        Customer customer = pet.getCustomer();
        if ( customer == null ) {
            return 0L;
        }
        long id = customer.getId();
        return id;
    }

    protected Customer petDtoToCustomer(PetDto petDto) {
        if ( petDto == null ) {
            return null;
        }

        Customer customer = new Customer();

        customer.setId( petDto.getOwnerId() );

        return customer;
    }
}
