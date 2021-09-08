package com.example.mosip.Repository;

import com.example.mosip.Beans.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

  //  @Query(value = "SELECT * FROM customer u WHERE u.email_id =:emailId", nativeQuery = true)
   // public Customer findByEmail(@Param("emailId") String emailId);

}
