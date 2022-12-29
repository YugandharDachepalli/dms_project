package com.te.dms.controller;

import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.te.dms.dto.LoginDto;
import com.te.dms.dto.UserRegisterDto;
import com.te.dms.exceptions.CannotRegisterUserException;
import com.te.dms.exceptions.PasswordMisMatchException;
import com.te.dms.response.GeneralResponse;
import com.te.dms.service.DmsUserService;
import com.te.dms.util.JwtUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/dms")
public class GeneralController {

	private final JwtUtils jwtUtils;
	private final DmsUserService dmsUserService;
 
	@PostMapping(path = "/register")
	public GeneralResponse<String> userRegister(@RequestBody UserRegisterDto userRegisterDto) {
		Optional<Boolean> isRegistered = dmsUserService.register(userRegisterDto);
		if (isRegistered.get()) {
			return new GeneralResponse<String>("Registration Successfull", null, null);
		}
		throw new CannotRegisterUserException("unable to register the user please try again");

	}

	@GetMapping(path = "/login")
	public GeneralResponse<String> login(@RequestBody LoginDto loginDto) {
		Boolean isVerified = dmsUserService.validate(loginDto);
		if (isVerified) {
			String token = jwtUtils.generateToken(loginDto.getUsername());
			return new GeneralResponse<String>("Login Successfull", null, token);
		}
		throw new PasswordMisMatchException("unable to validate user credentails");
	}

}
