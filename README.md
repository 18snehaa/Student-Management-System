# Student Management System

1. Objective

Develop a simple Spring Boot application for managing student details using Spring Data JPA, REST APIs, Scheduler, and a MySQL Stored Procedure.
The application follows the architecture:
Controller → Service → Repository → Database

2. Entity Class
The `Student` entity is mapped to the `STUDENT_DTLS` table.
The following JPA annotations are used:
* `@Entity`
* `@Table`
* `@Id`
* `@GeneratedValue`
* `@Column`
* `@CreationTimestamp`
* `@UpdateTimestamp`
The `student_rank` field is mapped as a `String`.

3. Repository Layer
Spring Data JPA predefined methods are used for normal CRUD operations.
Derived query methods are also implemented for different student search requirements.

4. Service Layer
The project contains:
* `StudentService`
* `StudentServiceImpl`

The Service layer handles the application logic and communicates with the Repository.
Implemented operations:
* Create student
* Create multiple students
* Find student by ID
* Find all students
* Update student
* Hard delete
* Soft delete
* Get active students

 5. REST Controller
The Controller uses:

* `@RestController`
* `@GetMapping`
* `@PostMapping`
* `@PutMapping`
* `@PatchMapping`
* `@DeleteMapping`
* `@RequestBody`
* `@PathVariable`
* `@RequestParam`
* `ResponseEntity'

 6. Derived Queries
The following derived query operations are implemented:
* Find students by gender
* Find students by gender and rank
* Find students whose rank is greater than or equal to a given rank
* Find students whose rank is less than or equal to a given rank
* Find students whose name starts with given text
* Find students whose name contains given text
* Find student by email
* Find students whose gender is NULL
Since `student_rank` is stored as `VARCHAR`, numeric comparison is handled using SQL casting where required

7. Native SQL Queries
Native SQL queries are implemented using:
@Query(nativeQuery = true)

The following operations are implemented:
* Retrieve all active students
* Update student gender using student ID
* Hard delete student using student ID

For update and delete queries:
@Modifying @Transactional are used.


8. Stored Procedure

A MySQL stored procedure is created to retrieve student name and gender using `student_id`.
DELIMITER //

CREATE PROCEDURE get_student_details(
    IN p_student_id INT
)
BEGIN
    SELECT
        student_name,
        student_gender
    FROM STUDENT_DTLS
    WHERE student_id = p_student_id;
END //

DELIMITER ;

The procedure is called from Spring Boot using `SimpleJdbcCall`.
  REST API
GET /students/procedure/{id}
  Example:
GET /students/procedure/1
The result is returned as a JSON response.

9. Scheduler
A scheduler is implemented to check the number of active students.
@Scheduled(fixedRate = 60000)
The scheduler runs every 60 seconds.

