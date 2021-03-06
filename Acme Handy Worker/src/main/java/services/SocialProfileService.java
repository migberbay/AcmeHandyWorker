package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SocialProfileRepository;
import security.LoginService;
import security.UserAccount;
import domain.SocialProfile;


@Service
@Transactional
public class SocialProfileService {

	//Managed Repository -----
	@Autowired
	private SocialProfileRepository socialProfileRepository;
	
	//Supporting Services -----
	
	//@Autowired
	//private SomeService serviceName 
	
	//Constructors -----
	public SocialProfileService(){
		super();
	}
	
	//Simple CRUD methods -----
	public SocialProfile create(){
		//Metodo general para todas los servicios, es probable 
		//que sea necesario a�adir atributos consistentes con la entity.
		SocialProfile res = new SocialProfile();
		return res;
	}
	
	public Collection<SocialProfile> findAll(){
		return socialProfileRepository.findAll();
	}
	
	public SocialProfile findOne(int Id){
		return socialProfileRepository.findOne(Id);
	}
	
	public SocialProfile save(SocialProfile a){
		//puede necesitarse control de versiones por concurrencia del objeto.
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el due�o
		Assert.isTrue(true);//modificar para condiciones especificas
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		socialProfileRepository.save(a);
		return a;
	}
	
	public void delete(SocialProfile a){
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el due�o
		Assert.isTrue(true);//modificar para condiciones especificas.(data constraint)
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		socialProfileRepository.delete(a);
	}
	
	//Other business methods -----
	
	
}