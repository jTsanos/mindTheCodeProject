package com.example.mindBlowProject;

import com.example.mindBlowProject.Repositories.AddressRepository;
import com.example.mindBlowProject.Repositories.CommentRepository;
import com.example.mindBlowProject.Repositories.UserRepository;
import com.example.mindBlowProject.entities.Address;
import com.example.mindBlowProject.entities.Comment;
import com.example.mindBlowProject.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Configuration
public class LoadDatabase {

        private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);


    @Autowired
    AddressRepository addressRepo;
    @Autowired
    CommentRepository commentRepo;
    @Autowired
    UserRepository userRepo;

    @EventListener(ApplicationReadyEvent.class)
    void initDatabase() {

        log.info("Preloading " + addressRepo.save(generateRandomAddress()));
        log.info("Preloading " + commentRepo.save(generateRandomComment()));
        log.info("Preloading " + userRepo.save(generateRandomUser(addressRepo.findAll(),commentRepo.findAll())));

    }

    private static User generateRandomUser(List<Address> addressList,List<Comment> commentList){


        //List<Address> addressList = new ArrayList<>();
     //   List<Comment> commentList = new ArrayList<>();

        User user = new User("john","tsan",addressList,commentList);
        return user;
    }

    private static  Address generateRandomAddress(){
        Address address = new Address("mitsotaki12","666");
        return address;
    }



    private static Comment generateRandomComment(){
        Date date = new Date();
        Comment comment = new Comment("kalo",date);
        return comment;
    }


}
