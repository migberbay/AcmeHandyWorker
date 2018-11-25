package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EducationRecordRepository;
import security.LoginService;
import security.UserAccount;
import domain.Curricula;
import domain.EducationRecord;
import domain.HandyWorker;
import domain.ProfessionalRecord;


@Service
@Transactional
public class EducationRecordService {

	//Managed Repository -----
	@Autowired
	private EducationRecordRepository educationRecordRepository;
	
	//Supporting Services -----
	
	@Autowired
	private CurriculaService curriculaService; 
	
	@Autowired
	private ActorService actorService;
	
	//Constructors -----
	public EducationRecordService(){
		super();
	}
	
	//Simple CRUD methods -----
	public EducationRecord create(){
		EducationRecord res = new EducationRecord();
		
		Date start = new Date();
		start.setTime(start.getTime()-1000);
		res.setStartDate(start);
		
		return res;
	}
	
	public Collection<EducationRecord> findAll(){
		return educationRecordRepository.findAll();
	}
	
	public EducationRecord findOne(int Id){
		return educationRecordRepository.findOne(Id);
	}
	
	public EducationRecord save(EducationRecord a){
		Assert.isTrue(LoginService.hasRole("HANDYWORKER"));
		linkCurricula(a); //COMPROBAR SIEMPRE EL ROL ANTES DE EJECUTAR.
		
		return a;
	}
	
	public void delete(EducationRecord a){
		UserAccount owner = findowner(a);
		Assert.notNull(owner);
		UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(owner.equals(userAccount));
		
		educationRecordRepository.delete(a);
	}
	
	//Other business methods -----
	private UserAccount findowner(EducationRecord a){
		
		Collection<Curricula> todas = curriculaService.findAll();
		UserAccount owner = null;
		for (Curricula c : todas) {
			for (EducationRecord e : c.getEducationRecords()) {
				if(e.equals(a)){
					owner = c.getHandyWorker().getUserAccount();
				}
			}
		}
		return owner;
	}
	
	private void linkCurricula(EducationRecord a){
		UserAccount ua = LoginService.getPrincipal();
		HandyWorker hw = (HandyWorker) actorService.getByUserAccountId(ua);
		System.out.println(hw.getUserAccount().getAuthorities());
		for (Curricula c : curriculaService.findAll()) {
			if(c.getHandyWorker().equals(hw)){ // de manera intrinseca ya comprueba que es el due�o del PersonalRecord.
				Collection<EducationRecord> aux = c.getEducationRecords();
				aux.add(a);
				c.setEducationRecords(aux);
				this.save(a);
				curriculaService.save(c);
			}
		}
	}
	
}