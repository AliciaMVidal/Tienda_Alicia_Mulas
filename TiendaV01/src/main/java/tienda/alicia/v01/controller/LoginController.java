package tienda.alicia.v01.controller;

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tienda.alicia.v01.model.Usuario;
import tienda.alicia.v01.repository.UsuarioRepository;
import tienda.alicia.v01.service.UsuarioService;

@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
	UsuarioService usuarioService;

	private static Logger logger = LogManager.getLogger(LoginController.class);

	@GetMapping("")
	public String formulario(Model model) {
		model.addAttribute("usuario", new Usuario());
		logger.info(String.format(" >>>>>> Cargando el login"));
		//enviarEmail();
		
		return "login";

	}

	@PostMapping("/hacerLogin")
	public String hacerLogin(@ModelAttribute Usuario usuario, Model model, HttpSession sesion) {

		if (usuarioService.loginCorrecto(usuario)) {
			model.addAttribute("usuario", usuario);
			// sesion.setAttribute("sesion", usuario.getUsername());
			sesion.setAttribute("sesion", usuario.getEmail());
			// Usuario usuarioRol =
			// usuarioService.getUsuarioByUsername(usuario.getUsername());
			Usuario usuarioRol = usuarioService.getUsuarioByEmail(usuario.getEmail());
			System.out.println(usuarioRol.getId_rol());
			logger.info(String.format(" >>>>>> Intento de login: " + usuario.getEmail()));
			if (usuarioRol.getId_rol() == 1 || usuarioRol.getId_rol() == 2) {
				logger.info(String.format(" >>>>>> Login correcto: " + usuario.getEmail()));
				return "redirect:/administracion";
			} else if (usuarioRol.getId_rol() == 3) {
				logger.info(String.format(" >>>>>> Login correcto: " + usuario.getEmail()));
				return "redirect:/";
			}
		}
		return "redirect:/login";
	}

	@GetMapping("/registro")
	public String registro(Model model) {
		model.addAttribute("usuario", new Usuario());
		logger.info(String.format(" >>>>>> Cargando formulario de registro: "));
		return "registro";

	}

	@PostMapping("/hacerRegistro")
	public String hacerRegistro(@Valid @ModelAttribute Usuario usuario, BindingResult bindingResult, Model model, HttpSession sesion
			) {
		// Por defecto los usuarios son clientes(rol3)
		// Encriptar contraseña
		//System.out.println("aqui");

		if (bindingResult.hasErrors()) {
			System.out.println("aqui");
			model.addAttribute("usuario", usuario);

			return "registro";
		} else {
			Base64 base64 = new Base64();
			String passw = new String(base64.encode(usuario.getClave().getBytes()));
			usuario.setClave(passw);
			Usuario u = new Usuario(usuario.getId_rol(), usuario.getEmail(), passw);
			System.out.println("aqui");
			System.out.println(usuario.getEmail());
			usuarioService.registroCorrecto(u);
			logger.info(String.format(" >>>>>> Usuario nuevo registrado: " + u.getEmail()));
			//enviarEmail();

		}

		return "redirect:/login";

	}
//Lod e antes
//	@PostMapping("/hacerRegistro")
//	public String hacerRegistro(@ModelAttribute Usuario usuario, Model model, HttpSession sesion) {
//		//Por defecto los usuarios son clientes(rol3)
//		//Encriptar contraseña
//		Base64 base64 = new Base64();
//		String passw=new String(base64.encode(usuario.getClave().getBytes()));
//		usuario.setClave(passw);
//		Usuario u = new Usuario(usuario.getId_rol(), usuario.getEmail(), passw);
//		if (u.getEmail() != null && u.getClave() != null) {
//			usuarioService.registroCorrecto(usuario);
//			logger.info(String.format(" >>>>>> Usuario nuevo registrado: "+usuario.getEmail()));
//		
//		}
//
//		return "redirect:/login";
//
//	}

	@GetMapping("/registroCliente")
	public String registroCliente(Model model) {
		model.addAttribute("usuario", new Usuario());
		return "registrocliente";
	}

	public void enviarEmail() {
		String destinatario = "aliciamulasv@gmail.com";

		try {
			// Propiedades de la conexion
			Properties prop = new Properties();
			// Nombre del servidor de salida
			prop.setProperty("mail.smtp.host", "smtp.gmail.com");
			// Habilitamos TLS
			prop.setProperty("mail.smtp.starttls.enable", "true");
			// Indicamos el puerto
			prop.setProperty("mail.smtp.port", "587");
			//prop.setProperty("mail.ssl.port", "465");
			//prop.setProperty("mail.tls.port", "587");
			// Indicamos el usuario
			//prop.setProperty("mail.smtp.user", "tiendaonlineserbatic@gmail.com");
			// Indicamos que requiere autenticación
			prop.setProperty("mail.smtp.auth", "true");
			prop.setProperty("mail.smtp.starttls.enable", "true");

			// Creamos un objeto sesion
			//Session sesion = Session.getDefaultInstance(prop);
			Session session = Session.getInstance(prop,
					  new javax.mail.Authenticator() {
					    protected PasswordAuthentication getPasswordAuthentication() {
					        return new PasswordAuthentication("tiendaonlineserbatic@gmail.com", "serbatic");
					    }
					  });
			// TODO
			session.setDebug(true);
			// Creamos un objeto mensaje a traves de la sesion
			MimeMessage mensaje = new MimeMessage(session);

			// Indicamos la cuenta desde la que se va a enviar
			mensaje.setFrom(new InternetAddress("tiendaonlineserbatic@gmail.com"));

			// Añadimos el recipiente al mensaje al que va a ir dirigido el mensaje
			mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));

			// Creamos el asunto del mensaje
			mensaje.setSubject("Ejemplo con JavaMail");

			// Creamos el cuerpo del mensaje
			mensaje.setText("Esto es una prueba con JavaMail");

//			mensaje.setText(
//					"Esto es una prueba <br> con <b>JavaMail</b>",
//					"ISO-8859-1",
//					"html");

			// Utilizamos un objeto transport para hacer el envio indicando el protocolo
			
			Transport t = session.getTransport("smtp");
			 System.out.println("saqui1");

			// Hacemos la conexion
			t.connect("tiendaonlineserbatic@gmail.com", "serbatic");
			 System.out.println("saqui2");

			// Enviamos el mensaje
			t.sendMessage(mensaje, mensaje.getAllRecipients());

			// Cerramos la conexion
			t.close();

		} catch (AddressException ex) {
			logger.warn(String.format(" >>>>>>Enviando meail " + ex));
		} catch (MessagingException ex) {
			logger.warn(String.format(" >>>>>>Enviando meail " + ex));
		}
	}
	


}
