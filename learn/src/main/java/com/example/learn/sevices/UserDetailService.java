package com.example.learn.sevices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.learn.exceptions.UserNotFoundException;
import com.example.learn.model.ApplicationUser;
//import com.example.learn.model.User;
import com.example.learn.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import static java.util.Collections.emptyList;

@Service
public class UserDetailService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		ApplicationUser applicationUser = userRepository.findOne(email);
		if (applicationUser == null) {
			throw new UserNotFoundException("User Does Not Exists");
		}

		return new User(applicationUser.getUsername(), applicationUser.getPassword(), emptyList());
	}

}
