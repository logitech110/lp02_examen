package com.municipalidad.thymeleaf.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import com.municipalidad.db.*;
import com.municipalidad.model.*;
import com.municipalidad.repository.*;
import com.municipalidad.service.*;

@Controller
public class ProyectoController {
	
	@Autowired
	private IRoleRepository repos;
	
	@Autowired
	private IPersonRepository reposPerson;
	
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/greeting")
	public String greeting (@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
		model.addAttribute("name", name);
		return "greeting";
	}
	
	@GetMapping("/listar")
	public String listRole(Model model) {
		try {
			model.addAttribute("ltsRole", repos.findAll());
			model.addAttribute("ltsPerson", reposPerson.findAll());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "listado";
	}
	
	@GetMapping("/login")
	public String loginView(Model model) {
		System.out.println("Mostrando login");
		model.addAttribute("userLogin", new User());
		return "login";
	}
	
	@GetMapping("/registro")
	public String registrarView(Model model) {
	    model.addAttribute("user", new User()); 
	    return "registro";  
	}

	@PostMapping("/registro")
	public String registrarUsuario(@ModelAttribute User user, Model model) {
	    try {
	        userService.save(user); 
	        return "greeting";  
	    } catch (Exception e) {
	        e.printStackTrace();
	        model.addAttribute("errors", "Error al registrar el usuario");
	        return "registro";  
	    }
	}


	@PostMapping("/login")
	public String login(@ModelAttribute User user, Model model) {
		System.out.println("Validando login");		
		String redirect = "login";
		User u = userService.validateUserByEmailAndPassword(user.getEmail(), user.getPassword());
		if (u != null) {
			u.setLastlogin(new Date());
			System.out.println("Actualizando usuario");
			User updUser = userService.updateUserLogin(u);
			model.addAttribute("userLogin", updUser);
			redirect = "greeting";
		} else {
			model.addAttribute("errors", "Usuario o clave incorrectos");
			model.addAttribute("userLogin", new User());
		}
		
		return redirect;
	}
    


	
	@RequestMapping(value = "/PersonReport", method = RequestMethod.GET)
	public void personaReporte(HttpServletResponse response) throws JRException, IOException {
		System.out.println("Hola mundo");
		InputStream jasperStream = this.getClass().getResourceAsStream("/reporte/Wood.jasper");
		System.out.println(jasperStream);
		Map<String, Object> params = new HashMap<String, Object>();
		JasperReport jasperReport = (JasperReport)JRLoader.loadObject(jasperStream);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, MySQLDataSource.getMySQLConnection());
		response.setContentType("application/x-pdf");
		response.setHeader("Content-disposition", "inline; filename=person-report.pdf");
		final OutputStream outputStream = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
	}
	
}

