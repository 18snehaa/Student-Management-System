package in.mindcraft.service;

import java.util.List;

import in.mindcraft.entity.Student;

public interface StudentService {

    Student create(Student student);

    List<Student> createAll(List<Student> students);

    Student findById(Integer id);

    List<Student> findAll();

    Student update(Integer id, Student student);

    void hardDelete(Integer id);

    void softDelete(Integer id);

    List<Student> getActiveStudents();
    
    //derived/native queries
    void updateGender(Integer id, String gender);

    List<Student> findByGender(String gender);

    List<Student> findByRankGreaterThanEqual(Integer rank);

    List<Student> findByRankLessThanEqual(Integer rank);

    List<Student> findByGenderAndRank(String gender, String rank);

    List<Student> findByGenderAndRankGreaterThanEqual( String gender, Integer rank);

    List<Student> findByNameStartingWith(String name);

    List<Student> findByNameContaining(String name);

    Student findByEmail(String email);

    List<Student> findByGenderIsNull();
}