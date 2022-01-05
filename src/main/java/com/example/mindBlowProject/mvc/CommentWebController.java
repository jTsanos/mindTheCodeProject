package com.example.mindBlowProject.mvc;

import com.example.mindBlowProject.Repositories.CommentRepository;
import com.example.mindBlowProject.Repositories.UserRepository;
import com.example.mindBlowProject.entities.Address;
import com.example.mindBlowProject.entities.Comment;
import com.example.mindBlowProject.entities.User;
import com.example.mindBlowProject.mvc.models.SearchByName;
import com.example.mindBlowProject.mvc.models.SearchCommentByText;
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
public class CommentWebController {
    private final CommentRepository repository;
    private final UserRepository userRepository;


    public CommentWebController(CommentRepository repository, UserRepository userRepository){
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @PostMapping("comments")
    public Object searchCommentByText(
            @ModelAttribute SearchCommentByText searchByText) {
        return "redirect:/comments?searchByText=" + searchByText.getCommentText();
    }


    @GetMapping("/comments")
    public Object showComments(Model model,
                               @RequestParam(defaultValue = "1") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "") String searchByText
    ) {
        if (page < 1) {
            return new RedirectView("/comments?page=1&size=" + size);
        }
        ;

        Page<Comment> comments = findPaginated(
                !searchByText.equals("") ?
                        repository.findCommentByText(searchByText) :
                        repository.findAll(),
                PageRequest.of(page - 1, size)
        );

        int totalPages = comments.getTotalPages();

        if (page > totalPages) {
            return new RedirectView("/comments?size=" + size + "&page=" + totalPages);
        }
        ;

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(Math.max(1, page - 2), Math.min(page + 2, totalPages))
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("page", page);
        model.addAttribute("comments", comments);
        model.addAttribute("searchByText", new SearchCommentByText(searchByText));
        return "comments";
    }

    @GetMapping("/comments/addComment")
    public String addComment(Model model) {
        model.addAttribute("comment", new Comment());
        return "add-comment";
    }

    @PostMapping("/comments/addComment")
    public String addComment(@Valid Comment comment, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-comment";
        }

        repository.save(comment);
        model.addAttribute("comment", comment);
        return "redirect:/comments";
    }

    @GetMapping("/comments/update/{id}")
    public String updateComment(@PathVariable("id") String id, Model model) {
        Comment comment = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid comment Id:" + id));

        model.addAttribute("comment", comment);
        return "update-comment";
    }

    @PostMapping("/comments/update/{id}")
    public String updateComment(@PathVariable("id") String id, @Valid Comment comment,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            comment.setId(id);
            return "update-comment";
        }

        repository.save(comment);
        return "redirect:/comments";
    }

    @GetMapping("/comments/delete/{id}")
    public String deleteComment(@PathVariable("id") String id, Model model) {
        Comment comment = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        repository.delete(comment);
        return "redirect:/comments";
    }

/* NEW mapping */

    @GetMapping("/comments/user/{id}")
    public Object getCommentFromUser(@PathVariable("id") String id,
                                       @RequestParam(defaultValue = "1") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       Model model){
        if (page < 1) {
            return new RedirectView("/comment/user/id="+id+"?size="+ size + "&page=1");
        };

        User user =  userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));


        List<Comment> commentListOfUser = user.getCommentList();

        Page<Comment> commentListOfUserPages = findPaginated(commentListOfUser,
                PageRequest.of(page - 1, size)
        );

        int totalPages = commentListOfUserPages.getTotalPages();

        if (page > totalPages) {
            return new RedirectView("/comments/user/"+id+"?size="+ size + "&page=" + totalPages);
        }

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(Math.max(1, page-2), Math.min(page + 2, totalPages))
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }


        model.addAttribute("commentListOfUserPages", commentListOfUserPages);
        model.addAttribute("user", user);
        model.addAttribute("page", page);

        return "commentListofUser";

    }



    private Page<Comment> findPaginated(List<Comment> comments, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<Comment> result;

        if (comments.size() < startItem) {
            result = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, comments.size());
            result = comments.subList(startItem, toIndex);
        }

        Page<Comment> commentPage = new PageImpl<Comment>(result, PageRequest.of(currentPage, pageSize), comments.size());

        return commentPage;
    }
}