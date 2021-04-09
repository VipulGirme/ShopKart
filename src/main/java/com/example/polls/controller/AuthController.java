package com.example.polls.controller;

import java.net.URI;
import java.util.Collections;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.polls.exception.AppException;
import com.example.polls.model.Role;
import com.example.polls.model.RoleName;
import com.example.polls.model.User;
import com.example.polls.payload.ApiResponse;
import com.example.polls.payload.ForgotPasswordRequest;
import com.example.polls.payload.JwtAuthenticationResponse;
import com.example.polls.payload.LoginRequest;
import com.example.polls.payload.SignUpRequest;
import com.example.polls.repository.ConfirmationTokenRepository;
import com.example.polls.repository.RoleRepository;
import com.example.polls.repository.UserRepository;
import com.example.polls.security.ConfirmationToken;
import com.example.polls.security.EmailSenderService;
import com.example.polls.security.JwtTokenProvider;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
	private ConfirmationTokenRepository confirmationTokenRepository;
    

	@Autowired
	private EmailSenderService emailSenderService;
    
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @RequestMapping(value="/forgotpassword" ,method=RequestMethod.POST)
    public ResponseEntity<ApiResponse> forgotUserPassword(@RequestBody ForgotPasswordRequest forgotRequest)
    {
    	Optional<User> existingUser = userRepository.findByEmail(forgotRequest.getEmail());
    	if(existingUser != null) {
			// create token
			ConfirmationToken confirmationToken = new ConfirmationToken(existingUser.get());
			
			// save it
			confirmationTokenRepository.save(confirmationToken);
			
			// create the email
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo(existingUser.get().getEmail());
			mailMessage.setSubject("Complete Password Reset!");
			mailMessage.setFrom("vipulgirme3223@gmail.com");
			//mailMessage.setText("To complete the password reset process, please click here: "
			//+"http://localhost:5000/confirm-reset?token="+confirmationToken.getConfirmationToken());
			mailMessage.setText("To complete the password reset process, please click here: "
					+"http://localhost:4200/resetpassword?token="+confirmationToken.getConfirmationToken());
			
			emailSenderService.sendEmail(mailMessage);
			
			return new ResponseEntity(new ApiResponse(true, "Mail sent"),
                    HttpStatus.ACCEPTED);
    	}
    	else
    		return  new ResponseEntity(new ApiResponse(false, "Fail to send mail"),
                    HttpStatus.BAD_REQUEST);
    }
    
    @RequestMapping(value = "/confirm-reset", method = { RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<?> validateResetToken(@RequestParam("token") String confirmationToken) {
		ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

		if (token != null) {
			Optional<User> user = userRepository.findByEmail(token.getUser().getEmail());
			if (user.get() != null) {
				return new ResponseEntity(new ApiResponse(true, token.getUser().getEmail()),
						HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity(new ApiResponse(false, "Link has a invalid token."),
						HttpStatus.ACCEPTED);
			}

		}
		return new ResponseEntity(new ApiResponse(false, "The link is invalid or broken!"),
				HttpStatus.ACCEPTED);

	}
    @RequestMapping(value = "/reset-password", method = RequestMethod.POST)
	public ResponseEntity<?> resetUserPassword(@RequestBody ForgotPasswordRequest forgotRequest) {
		if (forgotRequest.getEmail() != null && forgotRequest.getPassword() != null) {
			// use email to find user
			Optional<User> tokenUser = userRepository.findByEmail(forgotRequest.getEmail());
			tokenUser.get().setPassword(passwordEncoder.encode(forgotRequest.getPassword()));
			// System.out.println(tokenUser.getPassword());
			userRepository.save(tokenUser.get());
			
			return new ResponseEntity(
					new ApiResponse(true, "Password successfully reset. You can now log in with the new credentials."),
					HttpStatus.ACCEPTED);

		}
		else {

			return new ResponseEntity(new ApiResponse(false, "Failed to reset password.Please try again.!"),
					HttpStatus.ACCEPTED);
		}
		
	}
    
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // user.setPassword(user.getPassword());

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }
}