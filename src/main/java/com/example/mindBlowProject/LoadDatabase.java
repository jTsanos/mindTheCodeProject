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

        log.info("Preloading " + addressRepo.insert(generateRandomAddress()));
        log.info("Preloading " + commentRepo.insert(generateRandomComment()));
        log.info("Preloading " + userRepo.insert(generateRandomUser(addressRepo.findAll(),commentRepo.findAll())));

    }

    private static List<User> generateRandomUser(List<Address> addressList,List<Comment> commentList){


        //List<Address> addressList = new ArrayList<>();
     //   List<Comment> commentList = new ArrayList<>();

        User user = new User("john","tsan",addressList,commentList);
        User user1 = new User("dim","kok",addressList,commentList);
        List<User> allUsers = new ArrayList<>();
        allUsers.add(user1);
        allUsers.add(user);
        return allUsers;
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
