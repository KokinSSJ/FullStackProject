package com.ak.scheduler;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ak.entity.User;
import com.ak.service.EmailService;
import com.ak.service.UserService;

@Service
public class AdvService
{
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private UserService userService;
	
////    @Scheduled(cron="*/5 * * * * ?")
//	@Scheduled(fixedDelay=10000)
//    public void demoServiceMethod()
//    {	List<User> users = userService.findAll();
//    if(users==null){
//    	return;
//    }
//    	for(User u: users){
////    		emailService.sendEmail("test54321010@gmail.com", u.getEmail(), "reklama", "Super library");
//    	}
//        System.out.println("Method executed at every 5 seconds. Current time is :: "+ new Date());
//    }
}
