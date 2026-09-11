package in.mindcraft.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import in.mindcraft.service.StudentService;

@Component
public class StudentScheduler {
	
     // no direct communication with db it  goes via service  then repo then db 
    private final StudentService studentService;

    //constructor bcz  spring automatically inject 
    public StudentScheduler(StudentService studentService) {
        this.studentService = studentService;
    }
 
    //this  is main part that  means it will execute after 1 min everytime 
    @Scheduled(fixedRate = 60000)
    //spring automatically calls this method we did not call manually
    public void checkActiveStudents() {
        int count = studentService.getActiveStudents().size(); // service cl repo and size means how many students in list 

        System.out.println("Active Students Count: " + count);
    }
}