package in.mindcraft.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import in.mindcraft.entity.Student;
import in.mindcraft.service.StudentProcedureService;
import in.mindcraft.service.StudentService;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentProcedureService studentProcedureService;

    //one create 
    @PostMapping
    public ResponseEntity<Student> create(
            @RequestBody Student student) 
    {
        return ResponseEntity.status(HttpStatus.CREATED) .body(studentService.create(student));
    }

    
    //bulk students
    @PostMapping("/bulk")
    public ResponseEntity<List<Student>> createAll(
            @RequestBody List<Student> students)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.createAll(students));
    }

    //by id 
    @GetMapping("/{id}")
    public ResponseEntity<Student> findById(
            @PathVariable Integer id)
    {
        return ResponseEntity.ok(studentService.findById(id));
    }

    //find all students
    @GetMapping
    public ResponseEntity<List<Student>> findAll() 
    {
        return ResponseEntity.ok(studentService.findAll() );
    }
    
    //put add update 
    @PutMapping("/{id}") 
    public ResponseEntity<Student> update(
    		@PathVariable Integer id, 
    		@RequestBody Student student)
    { 
    	return ResponseEntity.ok( studentService.update(id, student)); 
    }
    
    //gender 
    @GetMapping("/gender/{gender}")
    public ResponseEntity<List<Student>> findByGender(
            @PathVariable String gender) 
    {
        return ResponseEntity.ok(studentService.findByGender(gender));
    }
    
    //rank greater
    @GetMapping("/rank/greater-equal")
    public ResponseEntity<List<Student>> rankGreaterEqual(
            @RequestParam Integer rank)
    {
        return ResponseEntity.ok(studentService.findByRankGreaterThanEqual(rank));
    }

    //rank less
    @GetMapping("/rank/less-equal")
    public ResponseEntity<List<Student>> rankLessEqual(
            @RequestParam Integer rank) 
    {
        return ResponseEntity.ok(studentService.findByRankLessThanEqual(rank));
    }

    //gender and rank 
    @GetMapping("/gender-rank")
    public ResponseEntity<List<Student>> genderAndRank(
            @RequestParam String gender,
            @RequestParam String rank)
    {
        return ResponseEntity.ok(
                studentService.findByGenderAndRank( gender, rank));
    }

    //gender and rank greater equal
    @GetMapping("/gender-rank/greater-equal")
    public ResponseEntity<List<Student>>
    genderAndRankGreaterEqual(
            @RequestParam String gender,
            @RequestParam Integer rank)
    {
        return ResponseEntity.ok(
                studentService.findByGenderAndRankGreaterThanEqual(gender, rank));
    }

    //name starting
    @GetMapping("/name/start")
    public ResponseEntity<List<Student>> nameStartsWith(
            @RequestParam String text) 
    {
        return ResponseEntity.ok(studentService.findByNameStartingWith(text));
    }

    //name contains
    @GetMapping("/name/contains")
    public ResponseEntity<List<Student>> nameContains(
            @RequestParam String text)
    {
        return ResponseEntity.ok( studentService.findByNameContaining(text) );
    }

    // email
    @GetMapping("/email")
    public ResponseEntity<Student> email(
            @RequestParam String email) 
    {
        return ResponseEntity.ok(studentService.findByEmail(email) );
    }

    //null
    @GetMapping("/gender-null")
    public ResponseEntity<List<Student>> genderNull() 
    {
        return ResponseEntity.ok(studentService.findByGenderIsNull());
    }

    //active students 
    @GetMapping("/active")
    public ResponseEntity<List<Student>> activeStudents()
    {
        return ResponseEntity.ok(studentService.getActiveStudents());
    }
    
    //update gender why patching bcz we are partially changing the student
    @PatchMapping("/{id}/gender")
    public ResponseEntity<String> updateGender(
            @PathVariable Integer id,
            @RequestParam String gender) 
    {
        studentService.updateGender(id, gender);
        return ResponseEntity.ok("Gender updated successfully");
    }
    
    //soft delete 
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<String> softDelete(
            @PathVariable Integer id) 
    {
        studentService.softDelete(id);
        return ResponseEntity.ok( "Student deactivated successfully");
    }
    
    //hard delete
    @DeleteMapping("/{id}")
    public ResponseEntity<String> hardDelete(
            @PathVariable Integer id) 
    {
        studentService.hardDelete(id);
        return ResponseEntity.ok( "Student deleted successfully" );
    }

    //procedure 
    @GetMapping("/procedure/{id}")
    public ResponseEntity<?> procedure(
            @PathVariable Integer id)
    {
        return ResponseEntity.ok(studentProcedureService.getStudentDetails(id) );
    }
}