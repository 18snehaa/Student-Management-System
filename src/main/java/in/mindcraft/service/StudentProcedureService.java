package in.mindcraft.service;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

@Service
public class StudentProcedureService {

    private final SimpleJdbcCall procedure;

    //constructor -- automatically inject the jdbctemplate 
    public StudentProcedureService(JdbcTemplate jdbcTemplate) 
    {
        procedure = new SimpleJdbcCall(jdbcTemplate).withProcedureName("get_student_details");
    }
    
    
    public List<Map<String, Object>> getStudentDetails(Integer id) 
    {
        Map<String, Object> result = procedure.execute(Map.of("p_student_id", id));

        return (List<Map<String, Object>>) result.get("#result-set-1");
    }
}