package com.ak.scheduler;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.ak.entity.Rent;
import com.ak.entity.User;
import com.ak.service.EmailService;
import com.ak.service.RentService;
import com.ak.service.UserService;

//@Service -> no annotation if @Scheduled(cron="* * * * * ?") because then it send twice!
//can be with fixedDelay!!
public class AdvService
{
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RentService rentService;
	
//    @Scheduled(cron="*/5 * * * * ?") //every 5sec
    @Scheduled(cron="0 30 17 * * ?") // every day at 5:30 
//	@Scheduled(fixedDelay=10000)
    public void demoServiceMethod()
    {	List<User> users = userService.findAll();
    if(users==null){
    	return;
    }
    	System.out.println("Sending email  at 5:30. Current time is : "+ new Date());
    	for(User u: users){
    		List<Rent> rents = rentService.findByUserOrderByCreatedDateDesc(u);
    		StringBuilder stringBuilder = new StringBuilder();
    		int i = 1;
    		stringBuilder.append("\n \t" + "Lp \t Rent Date \t\t\t Title \t\t Author Name ");
    		for(Rent r : rents){
    			
    			stringBuilder.append("\n \t" + i +"\t"+ r.getCreatedDate() + " \t\t" + r.getBook().getTitle() + " \t\t" + r.getBook().getAuthor() + " " );
    			i++;
    		}
    		emailService.sendEmail("test54321010@gmail.com", u.getEmail(), "reklama", "Super library " + "\nYou have Books to give back: " + stringBuilder);
    		// if you want send email with HTML content - > use MimeMessage
    		System.out.println(u.getEmail());
    	}
        System.out.println("Method executed. Current time is :: "+ new Date());
    }
}
