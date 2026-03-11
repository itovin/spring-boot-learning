package springbootlearning.ecommerce.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import springbootlearning.ecommerce.entities.Address;
import springbootlearning.ecommerce.repositories.AddressRepository;

@AllArgsConstructor
@Service
public class AddressService {
    private final AddressRepository addressRepository;

    public void save(Address address){
        addressRepository.save(address);
    }
}
