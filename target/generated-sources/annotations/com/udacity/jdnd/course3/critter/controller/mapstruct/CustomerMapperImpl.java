package com.udacity.jdnd.course3.critter.controller.mapstruct;

import com.udacity.jdnd.course3.critter.controller.dto.dto.CustomerDto;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-12-26T11:47:45+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 11.0.8 (AdoptOpenJDK)"
)
public class CustomerMapperImpl extends CustomerMapper {

    @Override
    Customer toCustomer(CustomerDto customerDto) {
        if ( customerDto == null ) {
            return null;
        }

        Customer customer = new Customer();

        customer.setId( customerDto.getId() );
        customer.setName( customerDto.getName() );
        customer.setPhoneNumber( customerDto.getPhoneNumber() );
        customer.setNotes( customerDto.getNotes() );

        return customer;
    }

    @Override
    public CustomerDto toCustomerDto(Customer customer) {
        if ( customer == null ) {
            return null;
        }

        CustomerDto customerDto = new CustomerDto();

        customerDto.setPetIds( mapPetIdToList( customer.getPets() ) );
        customerDto.setId( customer.getId() );
        customerDto.setName( customer.getName() );
        customerDto.setPhoneNumber( customer.getPhoneNumber() );
        customerDto.setNotes( customer.getNotes() );

        return customerDto;
    }

    @Override
    public List<Long> mapPetIdToList(List<Pet> pets) {
        if ( pets == null ) {
            return null;
        }

        List<Long> list = new ArrayList<Long>( pets.size() );
        for ( Pet pet : pets ) {
            list.add( mapPetToLong( pet ) );
        }

        return list;
    }
}
