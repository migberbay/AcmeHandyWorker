package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import security.UserAccount;

import domain.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer>{

	// no es necesario viene por defecto esta como referencia
	@Query("select a from Actor a where a.id = ?1") 
	Actor findOne(Integer Id);
	
	// bussines methods -----
	@Query("select a from Actor a where a.userAccount =?1")
	Actor findByUserAccountId(UserAccount ua);
	
	
}
