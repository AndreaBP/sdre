package sdre.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import sdre.services.ValidationService;

@RestController
public class EntityManagerController {

	@Autowired(required=true)
	private ValidationService validationService;

	@GetMapping(value = "/entities/validations")
	private ResponseEntity<?> validations() {

		return ResponseEntity.ok(validationService.entities());

	}
}
