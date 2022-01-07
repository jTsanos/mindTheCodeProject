package com.example.mindBlowProject.mvc;

import com.example.mindBlowProject.Repositories.AddressRepository;
import com.example.mindBlowProject.Repositories.UserRepository;
import com.example.mindBlowProject.entities.Address;
import com.example.mindBlowProject.entities.User;
import com.example.mindBlowProject.mvc.models.SearchByAddressName;
import com.example.mindBlowProject.mvc.models.SearchByName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class AddressWebController {
    private final AddressRepository repository;
    private final UserRepository userRepository;


    public AddressWebController(AddressRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @PostMapping("/addresses")
    public Object searchAddressByAddressName(
            @ModelAttribute SearchByAddressName searchAddressName) {
        return "redirect:/addresses?searchByAddressName=" + searchAddressName.getAddressName();
    }

    @GetMapping("/addresses")
    public Object showAddresses(Model model,
                                @RequestParam(defaultValue = "1") int page,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam(defaultValue = "") String searchByAddressName
    ) {
        if (page < 1) {
            return new RedirectView("/addresses?page=1&size=" + size);
        }
        ;

        Page<Address> addresses = findPaginated(
                !searchByAddressName.equals("") ?
                        repository.findByStreet(searchByAddressName) :
                        repository.findAll(),
                PageRequest.of(page - 1, size)
        );

        int totalPages = addresses.getTotalPages();

        if (page > totalPages) {
            return new RedirectView("/addresses?size=" + size + "&page=" + totalPages);
        }
        ;

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(Math.max(1, page - 2), Math.min(page + 2, totalPages))
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("page", page);
        model.addAttribute("addresses", addresses);
        model.addAttribute("searchByAddressName", new SearchByAddressName(searchByAddressName));
        return "addresses";
    }

    @GetMapping("/addresses/addAddress")
    public String addAddress(Model model) {
        model.addAttribute("address", new Address());
        return "add-address";
    }

    @PostMapping("/addresses/addAddress")
    public String addAddress(@Valid Address address, BindingResult result, Model model,User user) {
        if (result.hasErrors()) {
            return "add-address";
        }

        User user1 = userRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" ));

        user1.getAddressList().add(address);
        repository.save(address);
        userRepository.save(user1);
        model.addAttribute("address", address);
        return "redirect:/addresses";
    }

    @GetMapping("/addresses/update/{id}")
    public String updateAddress(@PathVariable("id") String id, Model model) {
        Address address = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid address Id:" + id));

        model.addAttribute("address", address);
        return "update-address";
    }

    @PostMapping("/addresses/update/{id}")
    public String updateAddress(@PathVariable("id") String id, @Valid Address address,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            address.setId(id);
            return "update-address";
        }

        repository.save(address);
        return "redirect:/addresses";
    }

    @GetMapping("/addresses/delete/{id}")
    public String deleteAddress(@PathVariable("id") String id, Model model) {
        Address address = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid address Id:" + id));
        repository.delete(address);
        return "redirect:/addresses";
    }

    @GetMapping("/addresses/user/{id}")
    public Object getAddressesFromUser(@PathVariable("id") String id,
                                       @RequestParam(defaultValue = "1") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       Model model) {
        if (page < 1) {
            return new RedirectView("/addresses/user/id=" + id + "?size=" + size + "&page=1");
        }
        ;

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));


        List<Address> addressListOfUser = user.getAddressList();

        if(addressListOfUser.size() == 0 ){
            return new RedirectView("/nodata/" +id);
        }
        else if (addressListOfUser.size() == 1 && addressListOfUser == null) {
            return new RedirectView("/nodata/" +id);

        }

        Page<Address> addressListOfUserPages = findPaginated(addressListOfUser,
                PageRequest.of(page - 1, size)
        );

        int totalPages = addressListOfUserPages.getTotalPages();

        if (page > totalPages) {
            return new RedirectView("/addresses/user/" + id + "?size=" + size + "&page=" + totalPages);
        }

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(Math.max(1, page - 2), Math.min(page + 2, totalPages))
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }


        model.addAttribute("addressListOfUserPages", addressListOfUserPages);
        model.addAttribute("user", user);
        model.addAttribute("page", page);

        return "addressListOfUser";

    }

    private Page<Address> findPaginated(List<Address> addresses, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<Address> result;

        if (addresses.size() < startItem) {
            result = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, addresses.size());
            result = addresses.subList(startItem, toIndex);
        }

        Page<Address> addressPage = new PageImpl<Address>(result, PageRequest.of(currentPage, pageSize), addresses.size());

        return addressPage;
    }




    @GetMapping("/addresses/addAddress/user/{id}")
    public String addAddressToSpecificUser(@PathVariable("id") String id, Model model) {

        Address address = new Address();

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        model.addAttribute(address);
        model.addAttribute(user);
        return "add-address";
    }

}

