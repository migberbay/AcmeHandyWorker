package repositories;

import java.util.Collection;
<<<<<<< HEAD
import java.util.List;
=======
>>>>>>> 24df8365044bf859bca0f2c5a25065ad2c644294

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;
import domain.Referee;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>{

	// no es necesario viene por defecto esta como referencia
	@Query("select a from Customer a where a.id = ?1") 
	Customer findOne(Integer Id);
	
	@Query("select com.customer, count(com) from Complaint com group by com.customer order by count(com) desc")
	Collection<Object> TopThreeInComplaints();
	
	@Query("select c from Customer c where c.userAccount.id = ?1") 
	Customer findByUserAccountId(Integer Id);
	
	@Query("select c1 from Customer c1 where c1.fixUpTasks.size >= (select avg(c2.fixUpTasks.size)*1.1 from Customer c2) order by c1.fixUpTasks.size desc")
	Collection<Customer> getCustomersWMoreTasksThanAvg();
}
