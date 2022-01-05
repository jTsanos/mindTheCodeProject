package com.example.mindBlowProject.mvc;

import com.example.mindBlowProject.Repositories.AddressRepository;
import com.example.mindBlowProject.Repositories.UserRepository;
import com.example.mindBlowProject.entities.Address;
import com.example.mindBlowProject.entities.User;
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
public class UserWebController {

    private final UserRepository repository;



    public UserWebController(UserRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/users")
        public Object searchUserByFirstName(
                @ModelAttribute SearchByName searchFirstName) {
            return "redirect:/users?searchByFirstName=" + searchFirstName.getFirstName();
        }

        @GetMapping("/users")
        public Object showUsers(Model model,
                @RequestParam(defaultValue = "1") int page,
                @RequestParam(defaultValue = "10") int size,
                @RequestParam(defaultValue = "") String searchByFirstName
        ) {
            if (page < 1) {
                return new RedirectView( "/users?page=1&size="+ size);
            };

            Page<User> users = findPaginated(
                    !searchByFirstName.equals("") ?
                            repository.findAllByFirstName(searchByFirstName) :
                            repository.findAll(),
                    PageRequest.of(page - 1, size)
            );

            int totalPages = users.getTotalPages();

            if (page > totalPages) {
                return new RedirectView("/users?size="+ size + "&page=" + totalPages);
            };

            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(Math.max(1, page-2), Math.min(page + 2, totalPages))
                        .boxed()
                        .collect(Collectors.toList());
                model.addAttribute("pageNumbers", pageNumbers);
            }

            model.addAttribute("page", page);
            model.addAttribute("users", users);
            model.addAttribute("searchFirstName", new SearchByName(searchByFirstName));
            return "users";
        }

        @GetMapping("/users/adduser")
        public String addUser(Model model) {
            model.addAttribute("user", new User());
            return "add-user";
        }

        @PostMapping("/users/adduser")
        public String addUser(@Valid User user, BindingResult result, Model model) {
            if (result.hasErrors()) {
                return "add-user";
            }

            repository.save(user);
            model.addAttribute("user", user);
            return "redirect:/users";
        }

        @GetMapping("/users/update/{id}")
        public String updateUser(@PathVariable("id") String id, Model model) {
            User user = repository.findById(String.valueOf(id))
                    .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

            model.addAttribute("user", user);
            return "update-user";
        }

        @PostMapping("/users/update/{id}")
        public String updateUser(@PathVariable("id") String id, @Valid User user,
                                BindingResult result, Model model) {
            if (result.hasErrors()) {
                user.setId(String.valueOf(id));
                return "update-user";
            }

            repository.save(user);
            return "redirect:/users";
        }

        @GetMapping("/users/delete/{id}")
        public String deleteUser(@PathVariable("id") String id, Model model) {
            User user = repository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
            repository.delete(user);
            return "redirect:/users";
        }


        private Page<User> findPaginated(List<User> users, Pageable pageable) {
            int pageSize = pageable.getPageSize();
            int currentPage = pageable.getPageNumber();
            int startItem = currentPage * pageSize;

            List<User> result;

            if (users.size() < startItem) {
                result = Collections.emptyList();
            } else {
                int toIndex = Math.min(startItem + pageSize, users.size());
                result = users.subList(startItem, toIndex);
            }

            Page<User> userPage = new PageImpl<User>(result, PageRequest.of(currentPage, pageSize), users.size());

            return userPage;
        }

}

