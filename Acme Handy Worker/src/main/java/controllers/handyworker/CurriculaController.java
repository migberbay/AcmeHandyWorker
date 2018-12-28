package controllers.handyworker;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.CurriculaService;
import services.EducationRecordService;
import services.EndorserRecordService;
import services.PersonalRecordService;
import controllers.AbstractController;
import domain.Actor;
import domain.Curricula;
import domain.EducationRecord;
import domain.EndorserRecord;
import domain.PersonalRecord;

@Controller
@RequestMapping("curricula/handyworker")
public class CurriculaController extends AbstractController {
	
	//Services
	
	@Autowired
	CurriculaService curriculaService;
	
	@Autowired
	private PersonalRecordService personalRecordService;
	
	@Autowired
	private EndorserRecordService endorserRecordService;
	
	@Autowired
	private EducationRecordService educationRecordService;
	
	@Autowired
	private ActorService actorService;

	// Constructors ------------------------------------------------------------------

	public CurriculaController() {
		super();
	}
	
	
	//Curricula----
	//Show----------------------------------------------------------------------------


	@RequestMapping(value="/show" , method=RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		Curricula c = null;
		
		Actor logged = actorService.getByUserAccountId(LoginService.getPrincipal());
		
		for (Curricula cu : curriculaService.findAll()) {
			if(cu.getHandyWorker().equals(logged)){
				c = cu;
			}
		}

		res = new ModelAndView("curricula/show");
		res.addObject("curricula", c);

		return res;
	}
	
	//Delete
	@RequestMapping(value="/deleteCurricula", method=RequestMethod.GET)
	public ModelAndView delete(@RequestParam int curriculaId){
		ModelAndView res;
		Curricula c = curriculaService.findOne(curriculaId);

			try {
				curriculaService.delete(c);
				res = new ModelAndView("redirect:show.do");
			} catch (Throwable e) {
				res = new ModelAndView("curricula/show");
				res.addObject("curricula", c);
				res.addObject("message", "message.commit.error");
			}
		return res;
	}
	
	//PERSONAL RECORD ----------------------------------------------------------------
	
	//Create--------------------------------------------------------------------------
		@RequestMapping(value="/createPersonalRecord", method=RequestMethod.GET)
		public ModelAndView create(){
			// its the same as creating a personal record, saving it, and assigning it to the curricula later on,
			// then creating a curricula, asssigning the PR to it then saving the curricula
			
			ModelAndView res;
			PersonalRecord pr = personalRecordService.create();
			
			res = this.createEditPersonalRecordModelAndView(pr);
			return res;
		}
		
		
	//Edit-----------------------------------------------------------------------------
		@RequestMapping(value="/editPersonalRecord", method=RequestMethod.GET)
		public ModelAndView editPR(@RequestParam int personalRecordId){
			
			
			ModelAndView res;
			PersonalRecord pr = personalRecordService.findOne(personalRecordId);
			
			res = this.createEditPersonalRecordModelAndView(pr);
			return res;
		}
		
	//Save-----------------------------------------------------------------------------
		@RequestMapping(value="/editPersonalRecord", method=RequestMethod.POST, params="save")
		public ModelAndView savePersonalRecord(@Valid PersonalRecord pr, BindingResult binding){
			// we check if the logged in actor has a curricula, if not create one and bind the PR to it (save the record first)
			ModelAndView res;
			System.out.println(binding);
			System.out.println(pr.getFullName()+" "+pr.getEmail()+ pr.getLinkedInUrl());
			
			if(binding.hasErrors()){
				res = createEditPersonalRecordModelAndView(pr);
			}else{
				try {
					System.out.println("tries to save the personal record / create curricula");
					personalRecordService.save(pr);// this already checks for the existance of a curricula. and if it doesnt exist it does it all.
					res = new ModelAndView("redirect:show.do");
				} catch (Throwable e) {
					res = createEditPersonalRecordModelAndView(pr,"message.commit.error");
					System.out.println(e);
				}
			}
			return res;
		}

	//ENDORSER RECORD ----------------------------------------------------------------
		
	//Create--------------------------------------------------------------------------
		@RequestMapping(value="/createEndorserRecord", method=RequestMethod.GET)
		public ModelAndView createEndorser(){
			
			
			ModelAndView res;
			EndorserRecord er = endorserRecordService.create();
			
			res = this.createEditEndorserRecordModelAndView(er);
			return res;
		}
		
		
	//Edit-----------------------------------------------------------------------------
		@RequestMapping(value="/editEndorserRecord", method=RequestMethod.GET)
		public ModelAndView editEndorserRecord(@RequestParam int endorserRecordId){
			
			
			ModelAndView res;
			EndorserRecord er = endorserRecordService.findOne(endorserRecordId);
			
			res = this.createEditEndorserRecordModelAndView(er);
			return res;
		}
		
	//Save-----------------------------------------------------------------------------
		@RequestMapping(value="/editEndorserRecord", method=RequestMethod.POST, params="save")
		public ModelAndView saveEndorserRecord(@Valid EndorserRecord er, BindingResult binding){
			ModelAndView res;
			System.out.println(binding);
			System.out.println(er.getEndorserName()+er.getEmail());
			
			if(binding.hasErrors()){
				res = createEditEndorserRecordModelAndView(er);
			}else{
				try {
					endorserRecordService.save(er);
					res = new ModelAndView("redirect:show.do");
				} catch (Throwable e) {
					res = createEditEndorserRecordModelAndView(er,"message.commit.error");
					System.out.println(e);
				}
			}
			return res;
		}	
		
	//Delete-----------------------------------------------------------------------------
		@RequestMapping(value="/deleteEndorserRecord", method=RequestMethod.GET)
		public ModelAndView deleteEndorserRecord(@RequestParam int endorserRecordId){
			ModelAndView res;
			endorserRecordService.delete(endorserRecordService.findOne(endorserRecordId));
			
			res = new ModelAndView("redirect:show.do");
			return res;
		}
		
	//EDUCATION RECORD ----------------------------------------------------------------
		
	// Create--------------------------------------------------------------------------
	@RequestMapping(value = "/createEducationRecord", method = RequestMethod.GET)
	public ModelAndView createEducation() {

		ModelAndView res;
		EducationRecord er = educationRecordService.create();

		res = this.createEditEducationRecordModelAndView(er);
		return res;
	}

	// Edit-----------------------------------------------------------------------------
	@RequestMapping(value = "/editEndorserRecord", method = RequestMethod.GET)
	public ModelAndView editEndorserRecord(@RequestParam int endorserRecordId) {

		ModelAndView res;
		EndorserRecord er = endorserRecordService.findOne(endorserRecordId);

		res = this.createEditEndorserRecordModelAndView(er);
		return res;
	}

	// Save-----------------------------------------------------------------------------
	@RequestMapping(value = "/editEndorserRecord", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEndorserRecord(@Valid EndorserRecord er,
			BindingResult binding) {
		ModelAndView res;
		System.out.println(binding);
		System.out.println(er.getEndorserName() + er.getEmail());

		if (binding.hasErrors()) {
			res = createEditEndorserRecordModelAndView(er);
		} else {
			try {
				endorserRecordService.save(er);
				res = new ModelAndView("redirect:show.do");
			} catch (Throwable e) {
				res = createEditEndorserRecordModelAndView(er,
						"message.commit.error");
				System.out.println(e);
			}
		}
		return res;
	}

	// Delete-----------------------------------------------------------------------------
	@RequestMapping(value = "/deleteEndorserRecord", method = RequestMethod.GET)
	public ModelAndView deleteEndorserRecord(@RequestParam int endorserRecordId) {
		ModelAndView res;
		endorserRecordService.delete(endorserRecordService
				.findOne(endorserRecordId));

		res = new ModelAndView("redirect:show.do");
		return res;
	}

	//Helper methods---------------------------------------------------
	//Personal Record---------------------------------------------------
	protected ModelAndView createEditPersonalRecordModelAndView(PersonalRecord pr){
		ModelAndView res;
		res = createEditPersonalRecordModelAndView(pr, null);
		return res;
	}
	protected ModelAndView createEditPersonalRecordModelAndView(PersonalRecord pr, String messageCode){
		ModelAndView res;
		
		res = new ModelAndView("curricula/editPersonalRecord");
		res.addObject("personalRecord", pr);
		res.addObject("message", messageCode);
		
		return res;
	}
	
	//Endorser Record---------------------------------------------------
	protected ModelAndView createEditEndorserRecordModelAndView(EndorserRecord er){
		ModelAndView res;
		res = createEditEndorserRecordModelAndView(er, null);
		return res;
	}
	protected ModelAndView createEditEndorserRecordModelAndView(EndorserRecord er, String messageCode){
		ModelAndView res;
		
		res = new ModelAndView("curricula/editEndorserRecord");
		res.addObject("endorserRecord", er);
		res.addObject("message", messageCode);
		
		return res;
	}
	
	//Education Record---------------------------------------------------
	protected ModelAndView createEditEducationRecordModelAndView(EducationRecord er){
		ModelAndView res;
		res = createEditEducationRecordModelAndView(er, null);
		return res;
	}
	protected ModelAndView createEditEducationRecordModelAndView(EducationRecord er, String messageCode){
		ModelAndView res;
		
		res = new ModelAndView("curricula/editEducationRecord");
		res.addObject("educationRecord", er);
		res.addObject("message", messageCode);
		
		return res;
	}
	
}