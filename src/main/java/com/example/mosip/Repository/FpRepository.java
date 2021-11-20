package com.example.mosip.Repository;

import com.example.mosip.Beans.AbisDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;


@Repository
public interface FpRepository extends JpaRepository<AbisDB,Integer> {

  //  @Query(value = "SELECT * FROM customer u WHERE u.email_id =:emailId", nativeQuery = true)
   // public Customer findByEmail(@Param("emailId") String emailId);
  //public Collection<AbisDB> findByOffset(@Param("i") int i);

      @Query(value = "SELECT * FROM abisdb a order by a.refid offset ?2 limit ?1", nativeQuery = true)
      public ArrayList<AbisDB> findByOffset(int limit,int offset);
      //@Query(value="SELECT e FROM Employee e WHERE e.name LIKE ?1 ORDER BY e.id offset ?2 limit ?3", nativeQuery = true)
      //public List<Employee> findByNameAndMore(String name, int offset, int limit);

}
