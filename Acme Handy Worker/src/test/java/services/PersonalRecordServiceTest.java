package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.Authority;
import security.LoginService;
import utilities.AbstractTest;
import domain.Curricula;
import domain.Note;
import domain.PersonalRecord;
import domain.Report;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml",
										"classpath:spring/config/packages.xml"})
@Transactional
public class PersonalRecordServiceTest extends AbstractTest {
	
	// Service under test ---------------------------------------------------------

	@Autowired
	private CurriculaService curriculaService;
	
	@Autowired
	private PersonalRecordService personalRecordService;
	
	
	// Tests ----------------------------------------------------------------------
	
	// CREATE ---------------------------------------------------------------------
	
	@Test
	public void testHandyWorkerCreate(){
		PersonalRecord pR;
		super.authenticate("handyWorker1");
		pR = personalRecordService.create();

		Assert.isNull(pR.getEmail());
		Assert.isNull(pR.getFullName());
		Assert.isNull(pR.getLinkedInUrl());
		Assert.isNull(pR.getPhone());
		Assert.isNull(pR.getPhoto());
		
		super.authenticate(null);
	}
	
	
	
	// SAVE -----------------------------------------------------------------------
	
	@Test 
	public void testHandyWorkerSave(){
		Curricula curricula,saved;
		
		super.authenticate("handyWorker1");						
		curricula = curriculaService.create();	
		
		System.out.println(curricula.getHandyWorker().getUserAccount().getUsername()+"<-- username due�o");
		
		PersonalRecord p = personalRecordService.create();
		
		p.setEmail("email@dominio.com");
		p.setFullName("pepito grillo");
		p.setLinkedInUrl("http://linkedin.com/user");
		p.setPhone("672190514");
		p.setPhoto("http://photostock.com/photo");
		
		personalRecordService.save(p); 
		
		curricula.setPersonalRecord(p);
		
		saved = curriculaService.save(curricula);			

		Collection<Curricula> curriculas = curriculaService.findAll();						
		Assert.isTrue(curriculas.contains(saved));
		
		super.authenticate(null);
	}
	

	// UPDATE ---------------------------------------------------------------------
	
	@Test 
	public void testHandyWorkerUpdate(){
		Curricula curricula, saved;
		Collection<Curricula> curriculas;
		super.authenticate("handyworker1");					
		curricula = curriculaService.findOne(14730);	
		PersonalRecord p = personalRecordService.create();

		curricula.setPersonalRecord(p);
		
		saved = curriculaService.save(curricula);						
		
		curriculas = curriculaService.findAll();						
		Assert.isTrue(curriculas.contains(saved));

		super.authenticate(null);
	}
	
	// DELETE ---------------------------------------------------------------------
	
	@Test 
	public void testHandyWorkerDelete(){
		Curricula curricula;
		Collection<Curricula> curriculas;
		super.authenticate("handyworker1");							

		curricula = curriculaService.findOne(14730);							  
		
		curriculaService.delete(curricula);							
		curriculas = curriculaService.findAll();						
		Assert.isTrue(!curriculas.contains(curricula));
			
		super.authenticate(null);
	}
	

}
