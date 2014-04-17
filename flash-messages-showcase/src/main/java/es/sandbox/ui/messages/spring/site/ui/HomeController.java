package es.sandbox.ui.messages.spring.site.ui;

import static es.sandbox.ui.messages.argument.Arguments.link;
import static es.sandbox.ui.messages.argument.Arguments.text;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.sandbox.ui.messages.Messages;

@Controller
public class HomeController {

	private static final Logger LOGGER= LoggerFactory.getLogger(HomeController.class);


	@RequestMapping(value= "/a", method= RequestMethod.GET)
	public String a(Messages messages, Model model, HttpServletRequest request) {
		messages.success("sm.controller.method.A");

		LOGGER.debug("{} -> R({}): {}", "A", "B", messages);
		return "redirect:/demo/b";
	}

	@RequestMapping(value= "/b", method= RequestMethod.GET)
	public String b(Messages messages) {
		messages.success("sm.controller.method.B");

		LOGGER.debug("{} -> R({}): {}", "B", "D", messages);
		return "redirect:/demo/d";
	}

	@RequestMapping(value= "/c", method= RequestMethod.GET)
	public String c() {

		LOGGER.debug("{} -> R({}): {}", "C", "D", "unknown");
		return "redirect:/demo/d";
	}

	@RequestMapping(value= "/d", method= RequestMethod.GET)
	public String d(Messages flash, HttpServletRequest request) {
		flash.warning("sm.controller.method.D");
		flash.warning("sm.controller.method.D.withArgs",
				link("/status/demo/e").title("sm.controller.method.D.linktitle", 32).cssClass("alert-link"),
				text("sm.controller.method.A.texto", new Integer(4)));

		LOGGER.debug("{} -> F({}): {}", "D", "home", flash);

		return "home";
	}

	@RequestMapping(value= "/e", method= RequestMethod.GET)
	public String e(Messages messages) {
		messages.success("Paso por <b>E</b>" + 1);
		messages.success("Paso por <b>E</b>" + 2);
		messages.success("Paso por <b>E</b>" + 3);
		messages.success("Paso por <b>E</b>" + 4);

		LOGGER.debug("{} -> R({}): {}", "E", "D", messages);
		return "redirect:/demo/d";
	}

	@RequestMapping(value= "/f", method= RequestMethod.GET)
	public String f(Messages messages, HttpServletRequest request) {
		messages.success("Paso por <b>F</b>");

		LOGGER.debug("{} -> R({}): {}", "F", "C", messages);

		return "redirect:/demo/c";
	}

	@RequestMapping(value= "/exception", method= RequestMethod.GET)
	public String exception(Messages messages, HttpServletRequest request) {
		messages.warning("Paso por <b>exception</b>");

		LOGGER.debug("{} -> F({}): {}", "exception", "home", messages);

		throw new IllegalArgumentException("Mensaje de excepción!!!!");
	}

	@ExceptionHandler(Exception.class)
	public String handleException(Exception exception, Messages messages) {
		LOGGER.error("Error!", exception);
		messages.error("Atencion excepcion " + exception.getLocalizedMessage());
		return "home";
	}
}
