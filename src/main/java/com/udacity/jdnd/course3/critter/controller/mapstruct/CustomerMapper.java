package com.udacity.jdnd.course3.critter.controller.mapstruct;

import com.udacity.jdnd.course3.critter.controller.dto.CustomerDto;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public abstract class CustomerMapper {

    public static CustomerMapper MAPPER = Mappers.getMapper(CustomerMapper.class);

    abstract Customer toCustomer(CustomerDto customerDto);
    @Mappings({
            @Mapping(source="pets",target = "petIds"),
            @Mapping(source="id",target="id"),
            @Mapping(source="name",target="name"),
            @Mapping(source="phoneNumber",target="phoneNumber"),
            @Mapping(source="notes",target="notes"),
    })
    public abstract CustomerDto toCustomerDto(Customer customer);

    public abstract List<Long> mapPetIdToList(List<Pet> pets);

    public  Long mapPetToLong(Pet pet) {
        return pet.getId();
    }


}
