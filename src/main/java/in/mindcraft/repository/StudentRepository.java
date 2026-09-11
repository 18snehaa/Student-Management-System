package in.mindcraft.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import in.mindcraft.entity.Student;

public interface StudentRepository
        extends JpaRepository<Student, Integer> {

    // derived query

    // 1. Gender equals input
    List<Student> findByStudentGender(String gender);


    // 2.Student Rank >= input
    @Query(
        value = "SELECT * FROM STUDENT_DTLS " + "WHERE CAST(student_rank AS UNSIGNED) >= :rank",
        nativeQuery = true
    )
    List<Student> findByRankGreaterThanEqual(
            @Param("rank") Integer rank
    );


    // 3. Rank <= input
    @Query(
        value = "SELECT * FROM STUDENT_DTLS " + "WHERE CAST(student_rank AS UNSIGNED) <= :rank",
        nativeQuery = true
    )
    List<Student> findByRankLessThanEqual(
            @Param("rank") Integer rank
    );


    // 4. Gender AND Rank condition
    List<Student> findByStudentGenderAndStudentRank( String gender,String rank );


    // 5. Name starts with input
    List<Student> findByStudentNameStartingWith(
            String name
    );


    // 6. Name contains input
    List<Student> findByStudentNameContaining(String name );


    // 7. Email equals input
    Student findByStudentEmail( String email );

    // 8. Gender is NULL
    List<Student> findByStudentGenderIsNull();

    // Native queries
    
    // 1. Get active students
    @Query(
        value = "SELECT * FROM STUDENT_DTLS " +"WHERE active_sw = 'Y'",
        nativeQuery = true
    )
    List<Student> findActiveStudents();


    // 2. Update gender by student ID
    @Modifying
    @Transactional
    @Query(
        value = "UPDATE STUDENT_DTLS " + "SET student_gender = :gender " + "WHERE student_id = :id",
        nativeQuery = true
    )
    int updateGender(
            @Param("id") Integer id,
            @Param("gender") String gender
    );


    // 3. Hard delete by student ID
    @Modifying
    @Transactional
    @Query(
        value = "DELETE FROM STUDENT_DTLS " + "WHERE student_id = :id",
        nativeQuery = true
    )
    int deleteStudent(
            @Param("id") Integer id
    );


    // 4. Soft delete / deactivate student
    @Modifying
    @Transactional
    @Query(
        value = "UPDATE STUDENT_DTLS " +"SET active_sw = 'N' " + "WHERE student_id = :id",
        nativeQuery = true
    )
    int deactivateStudent(
            @Param("id") Integer id
    );


    // 5. Gender AND Rank >= condition
    @Query(
        value = "SELECT * FROM STUDENT_DTLS " + "WHERE student_gender = :gender " + "AND CAST(student_rank AS UNSIGNED) >= :rank",
        nativeQuery = true
    )
    List<Student> findByGenderAndRankGreaterThanEqual(
            @Param("gender") String gender,
            @Param("rank") Integer rank
    );
}