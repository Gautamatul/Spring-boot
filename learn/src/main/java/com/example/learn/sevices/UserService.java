package com.example.learn.sevices;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.example.learn.metaData.ResponseBody;
import com.example.learn.metaData.MsgLst;
import com.example.learn.model.ApplicationUser;
import com.example.learn.repository.UserRepository;

@Service
public class UserService {

	@Resource
	private UserRepository userRepository;

	public ResponseBody<ApplicationUser> createUser(ApplicationUser u) {

		ApplicationUser user = userRepository.save(u);
		ResponseBody<ApplicationUser> metaData = new ResponseBody<>();
		MsgLst l = new MsgLst();
		if (null != user) {
			l.setCode("200");
			l.setServerity("None");
			l.setText("Successfully Create User");
			l.setType("NA");
			metaData.setEntity(user);
			metaData.setMsgLst(l);
		}

		return metaData;
	}

	public ApplicationUser getUser(String username) {
		return userRepository.findOne(username);
	}

	public ResponseBody<String> deleteUser(String username) {
		int i = userRepository.deleteByusername(username);
		ResponseBody<String> b = new ResponseBody<>();
		MsgLst l = new MsgLst();

		if (i > 0) {
			l.setCode("200");
			l.setText("Successfully deleted");
		} else {
			l.setCode("404");
			l.setText("Not Found");
		}
		b.setMsgLst(l);
		b.setEntity(username);
		return b;
	}

	public ApplicationUser updateUser(ApplicationUser user) {

		ApplicationUser u = userRepository.findOne(user.getUsername());
		if (u != null) {
			user.setId(u.getId());
		}
		return userRepository.saveAndFlush(user);
	}
}
