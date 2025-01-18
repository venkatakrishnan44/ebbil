package com.cts.EbBillManagement.controller;
import com.cts.EbBillManagement.model.Customer;
import com.cts.EbBillManagement.service.LoginService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class LoginController {

 @Autowired
 private LoginService loginService;
 
 @PostMapping("/register/{username}/{useremail}/{password}")
 public ResponseEntity<String> registerNewUser(@PathVariable String username,@PathVariable String useremail, @PathVariable String password, @RequestBody @Valid Customer customer)
 {
	return  loginService.createUser(username,useremail,password,customer);
 }


 @GetMapping("/login/{username}/{password}")
 public ResponseEntity<String> loginUser(@PathVariable String username,@PathVariable String password)
 {
	 return loginService.loginUser(username,password);
 }
 

}
