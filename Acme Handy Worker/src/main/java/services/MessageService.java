package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.MessageRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Administrator;
import domain.Application;
import domain.Box;
import domain.Message;

@Service
@Transactional
public class MessageService {

	// Managed Repository -----
	@Autowired
	private MessageRepository messageRepository;

	// Supporting Services -----

	@Autowired
	private AdministratorService adminService;

	@Autowired
	private BoxService boxService;

	// Simple CRUD methods -----
	public Message create(Actor sender, Actor recipient) {

		Message message = new Message();

		message.setSender(sender);
		message.setRecipient(recipient);
		message.setPriority("NEUTRAL");
		message.setFlagSpam(false);
		message.setTags(new ArrayList<String>());

		return message;
	}

	public Collection<Message> findAll() {
		return messageRepository.findAll();
	}

	public Message findOne(int Id) {
		return messageRepository.findOne(Id);
	}

	public Message save(Message message) {

		Message result;

		Date moment = new Date(System.currentTimeMillis() - 1000);

		message.setMoment(moment);

		result = messageRepository.save(message);
		return result;
	}

	public void delete(Message message) {

		// El mensaje se movera a la trashbox, si el mensaje ya estaba en la
		// trashbox se elimina del sistema.

		UserAccount userAccount = LoginService.getPrincipal();

		Actor recipient = message.getRecipient();
		Actor sender = message.getSender();

		Actor logged = null;

		if (recipient.getUserAccount().equals(userAccount)) {
			logged = recipient;
		}
		if (sender.getUserAccount().equals(userAccount)) {
			logged = sender;
		}

		Box trash = null;

		Collection<Box> loggedBoxes = boxService.findByActorId(logged.getId());
		Collection<Box> otherboxes = new ArrayList<Box>();

		for (Box box : loggedBoxes) {
			if (box.getName().equals("Trash Box"))
				trash = box;
			else {
				otherboxes.add(box);
			}
		}

		if (trash.getMessages().contains(message)) {
			Collection<Message> aux = trash.getMessages();
			aux.remove(message);
			messageRepository.delete(message);
			boxService.save(trash);
		} else {
			for (Box b : otherboxes) {
				if (b.getMessages().contains(message)) {
					Collection<Message> aux = b.getMessages();
					aux.remove(message);
					Collection<Message> t = trash.getMessages();
					t.add(message);

					boxService.save(trash);
					boxService.save(b);
				}
			}
		}

		// modificar para aplicarlo a la entidad correspondiente.
		// Assert.isTrue(message.getUserAccount().equals(userAccount));

	}

	// Other business methods -----

	public void sendSystemMessages(Application application) {

		Administrator sender = (Administrator) adminService.findAll().toArray()[0];

		Actor handyWorker = application.getHandyWorker();
		Actor customer = application.getFixUpTask().getCustomer();

		Message message1 = this.create(sender, handyWorker);
		Message message2 = this.create(sender, customer);

		message1.setBody("The status of the fix-up Task described as: \n"
				+ application.getFixUpTask().getDescription()
				+ "\n has changed you shoud revise it in the system. \n\n\n" +

				"El estado de la tarea de arreglo descrita como:\n"
				+ application.getFixUpTask().getDescription()
				+ "\n ha cambiado deberia revisar los cambios en el sistema.");

		message2.setBody("The status of the fix-up Task described as: \n"
				+ application.getFixUpTask().getDescription()
				+ "\n has changed you shoud revise it in the system. \n\n\n" +

				"El estado de la tarea de arreglo descrita como:\n"
				+ application.getFixUpTask().getDescription()
				+ "\n ha cambiado deberia revisar los cambios en el sistema.");

		this.save(message1);
		this.save(message2);

		Collection<Box> senderBoxes = boxService.findByActorId(sender.getId());
		Collection<Box> handyWorkerBoxes = boxService.findByActorId(handyWorker
				.getId());
		Collection<Box> customerBoxes = boxService.findByActorId(customer
				.getId());

		for (Box box : handyWorkerBoxes) {
			if (box.getName().equals("In Box")) {
				boxService.addMessageToBox(box, message1);
			}
		}

		for (Box box : customerBoxes) {
			if (box.getName().equals("In Box")) {
				boxService.addMessageToBox(box, message2);
			}
		}

		for (Box box : senderBoxes) {
			if (box.getName().equals("Out Box")) {
				boxService.addMessageToBox(box, message2);
				boxService.addMessageToBox(box, message1);
			}
		}
	}

	public void addMesageToBoxes(Message message) {
		// No se hara en el momento de la creacion si no a posteriori
		// cuando el mensaje este completamente escrito para evitar
		// guardar un mensaje diferente en la Box que lo que se queria mandar.

		Actor recipient = message.getRecipient();
		Actor sender = message.getSender();

		Collection<Box> recieverBoxes = boxService.findByActorId(recipient
				.getId());
		Collection<Box> senderBoxes = boxService.findByActorId(sender.getId());

		for (Box box : recieverBoxes) {
			if (box.getName().equals("In Box")) {
				boxService.addMessageToBox(box, message);
			}
		}

		for (Box box : senderBoxes) {
			if (box.getName().equals("Out Box")) {
				boxService.addMessageToBox(box, message);
			}
		}
	}

}