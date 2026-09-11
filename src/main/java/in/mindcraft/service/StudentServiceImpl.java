package in.mindcraft.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.mindcraft.entity.Student;

import in.mindcraft.repository.StudentRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    //application logs 
    private static final Logger logger =
            LoggerFactory.getLogger(StudentServiceImpl.class);

    @Override
    public Student create(Student student) {

        logger.info("Creating student: {}", student.getStudentName());

        if (student.getIsActive() == null) {

            student.setIsActive("Y");

        }

        Student savedStudent = studentRepository.save(student);

        logger.info("Student created successfully with ID: {}",
                savedStudent.getStudentId());

        return savedStudent;

    }

    //Create multiple students
    @Override
    public List<Student> createAll(List<Student> students) {

        logger.info("Creating multiple students. Total students: {}",
                students.size());

        for (Student student : students) {

            if (student.getIsActive() == null) {
                student.setIsActive("Y");

            }
        }

        List<Student> savedStudents = studentRepository.saveAll(students);

        logger.info("Multiple students created successfully. Total students: {}",
                savedStudents.size());

        return savedStudents;

    }

    //get student by id
    @Override
    public Student findById(Integer id)
    {
        logger.info("Fetching student with ID: {}", id);

        Student student = studentRepository.findById(id)

                .orElseThrow(() ->

                    new RuntimeException("Student not found with ID: " + id ));

        logger.info("Student found successfully with ID: {}", id);

        return student;
    }

    //get all student
    @Override
    public List<Student> findAll() {

        logger.info("Fetching all students");

        List<Student> students = studentRepository.findAll();

        logger.info("Total students found: {}", students.size());

        return students;
    }

    //update student
    @Override
    public Student update(Integer id, Student student) {

        logger.info("Updating student with ID: {}", id);

        Student existingStudent = findById(id);

        existingStudent.setStudentName(student.getStudentName());

        existingStudent.setStudentRank( student.getStudentRank());

        existingStudent.setStudentGender(student.getStudentGender());

        existingStudent.setStudentEmail(student.getStudentEmail());

        if (student.getIsActive() != null) {

            existingStudent.setIsActive(student.getIsActive());

        }

        Student updatedStudent = studentRepository.save(existingStudent);

        logger.info("Student updated successfully with ID: {}", id);

        return updatedStudent;

    }

    // hard delete 
    @Override
    public void hardDelete(Integer id) {

        logger.info("Hard deleting student with ID: {}", id);

        if (!studentRepository.existsById(id)) {

            logger.error("Student not found for hard delete. ID: {}", id);

            throw new RuntimeException("Student not found with ID: " + id);

        }

        studentRepository.deleteById(id);

        logger.info("Student permanently deleted with ID: {}", id);

    }

    // soft delete 
    @Override
    public void softDelete(Integer id) {

        logger.info("Soft deleting student with ID: {}", id);

        int result = studentRepository.deactivateStudent(id);

        if (result == 0) {

            logger.error("Student not found for soft delete. ID: {}", id);

            throw new RuntimeException( "Student not found with ID: " + id );

        }

        logger.info("Student deactivated successfully with ID: {}", id);

    }

    //get active student 
    @Override
    public List<Student> getActiveStudents() {

        logger.info("Fetching active students");

        List<Student> students = studentRepository.findActiveStudents();

        logger.info("Total active students found: {}", students.size());

        return students;

    }

    //update gender ( here wecall natuve quyery)
    @Override
    public void updateGender(Integer id,String gender) {

        logger.info("Updating gender for student ID: {} to {}",
                id, gender);

        int result = studentRepository.updateGender(id,gender);

        if (result == 0) {

            logger.error("Student not found for gender update. ID: {}", id);

            throw new RuntimeException("Student not found with ID: " + id );

        }

        logger.info("Student gender updated successfully. ID: {}", id);

    }

    //find by gender 
    @Override
    public List<Student> findByGender(String gender) {

        logger.info("Finding students with gender: {}", gender);

        List<Student> students =
                studentRepository.findByStudentGender(gender);

        logger.info("Students found with gender {}: {}",
                gender, students.size());

        return students;

    }

    //rank greater than 
    @Override
    public List<Student> findByRankGreaterThanEqual(Integer rank) 
    {
        logger.info("Finding students with rank >= {}", rank);

        List<Student> students =
                studentRepository.findByRankGreaterThanEqual(rank);

        logger.info("Students found with rank >= {}: {}",
                rank, students.size());

        return students;

    }

    //rank less than 
    @Override
    public List<Student> findByRankLessThanEqual(Integer rank) 
    {
        logger.info("Finding students with rank <= {}", rank);

        List<Student> students =
                studentRepository.findByRankLessThanEqual(rank);

        logger.info("Students found with rank <= {}: {}",
                rank, students.size());

        return students;

    }

    //gender and rank 
    @Override
    public List<Student> findByGenderAndRank( String gender,String rank) 
    {
        logger.info("Finding students with gender: {} and rank: {}",
                gender, rank);

        List<Student> students =
                studentRepository.findByStudentGenderAndStudentRank(
                        gender, rank);

        logger.info("Students found with gender {} and rank {}: {}",
                gender, rank, students.size());

        return students;

    }

    //gender rank greater than 
    @Override
    public List<Student> findByGenderAndRankGreaterThanEqual(String gender, Integer rank) 
    {
        logger.info("Finding students with gender: {} and rank >= {}",
                gender, rank);

        List<Student> students =
                studentRepository.findByGenderAndRankGreaterThanEqual(
                        gender, rank);

        logger.info("Students found with gender {} and rank >= {}: {}",
                gender, rank, students.size());

        return students;

    }

    //name staring with initial
    @Override
    public List<Student> findByNameStartingWith(
            String name) {

        logger.info("Finding students whose name starts with: {}", name);

        List<Student> students =
                studentRepository
                .findByStudentNameStartingWith(name);

        logger.info("Students found with name starting with {}: {}",
                name, students.size());

        return students;

    }

    //name that  contain some letter
    @Override
    public List<Student> findByNameContaining(String name) 
    {
        logger.info("Finding students whose name contains: {}", name);

        List<Student> students =
                studentRepository.findByStudentNameContaining(name);

        logger.info("Students found with name containing {}: {}",
                name, students.size());

        return students;

    }

    //particular email
    @Override
    public Student findByEmail(String email)
    {
        logger.info("Finding student with email: {}", email);

        Student student =
                studentRepository.findByStudentEmail(email);

        if (student != null) {

            logger.info("Student found successfully with email: {}", email);

        } else {

            logger.warn("No student found with email: {}", email);

        }

        return student;

    }

    //find that gender is null
    @Override
    public List<Student> findByGenderIsNull() {

        logger.info("Finding students where gender is null");

        List<Student> students =
                studentRepository.findByStudentGenderIsNull();

        logger.info("Students with null gender found: {}",
                students.size());

        return students;

    }

}

