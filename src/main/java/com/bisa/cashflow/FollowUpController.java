package com.bisa.cashflow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class FollowUpController {
	@Autowired
	FollowUpService followUpService;

	@PostMapping("/addAccount")
	public ResponseEntity<String> addAccount(@RequestBody AccountDto accountDto) throws Exception {
		String response = followUpService.addAccount(accountDto);
		return new ResponseEntity<String>(response, HttpStatus.CREATED);
	}

	@GetMapping("/depositMoney/{accountCode}/{amount}/{currency}")
	public ResponseEntity<String> depositMoney(@PathVariable String accountCode, @PathVariable Double amount,
			@PathVariable int currency) throws Exception {
		String json = followUpService.moveMoney(accountCode, amount, 0D, currency);
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	@GetMapping("/withdrawMoney/{accountCode}/{amount}/{currency}")
	public ResponseEntity<String> withdrawMoney(@PathVariable String accountCode, @PathVariable Double amount,
			@PathVariable int currency) throws Exception {
		String json = followUpService.moveMoney(accountCode, 0D, amount, currency);
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	@GetMapping("/checkBalance/{accountCode}")
	public ResponseEntity<List<String>> checkBalance(@PathVariable String accountCode) throws Exception {
		List<String> json = followUpService.checkBalance(accountCode);
		return new ResponseEntity<List<String>>(json, HttpStatus.OK);
	}
}
