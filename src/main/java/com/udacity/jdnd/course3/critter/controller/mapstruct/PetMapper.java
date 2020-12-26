package com.udacity.jdnd.course3.critter.controller.mapstruct;

import com.udacity.jdnd.course3.critter.controller.dto.PetDto;
import com.udacity.jdnd.course3.critter.entity.Pet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PetMapper {
    PetMapper MAPPER = Mappers.getMapper(PetMapper.class);
    @Mapping(source="customer.id",target="ownerId")
    PetDto petToPetDto(Pet pet);
    @Mapping(source="ownerId",target="customer.id")
    Pet petDtoToPet(PetDto petDto);
}
