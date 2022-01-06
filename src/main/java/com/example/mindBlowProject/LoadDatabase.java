package com.example.mindBlowProject;

import com.example.mindBlowProject.Repositories.AddressRepository;
import com.example.mindBlowProject.Repositories.CommentRepository;
import com.example.mindBlowProject.Repositories.UserRepository;
import com.example.mindBlowProject.entities.Address;
import com.example.mindBlowProject.entities.AppUserRole;
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



        User user = new User("john","tsan",List.of(addressList.get(0),addressList.get(1)),commentList, AppUserRole.ADMIN);
        User user1 = new User("dim","kok",List.of(addressList.get(2),addressList.get(3)),commentList,AppUserRole.ADMIN);
        List<User> allUsers = new ArrayList<>();
        allUsers.add(user1);
        allUsers.add(user);
        return allUsers;
    }

    private static  List<Address> generateRandomAddress(){
        Address address = new Address("mitsotaki13","666");
        Address address2 = new Address("tsipra12","666 666");
        Address address3 = new Address("p","2");
        Address address4 = new Address("o","1");
        return List.of(address,address2,address3,address4);
    }



    private static List<Comment> generateRandomComment(){
        Date date = new Date();
        Comment comment = new Comment("kalo",date);
        Comment comment1 = new Comment("so so",date);
        Comment comment2 = new Comment("haliaaa",date);
        return List.of(comment,comment1,comment2);
    }


}
