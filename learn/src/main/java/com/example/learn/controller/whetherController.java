package com.example.learn.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;

import com.example.learn.employee.EmpBody;
import com.example.learn.metaData.MsgLst;
import com.example.learn.metaData.Response;
import com.example.learn.metaData.ResponseBody;
import com.fasterxml.jackson.core.type.TypeReference;

import io.jsonwebtoken.lang.Collections;

@RestController
@RequestMapping("/weather")
public class whetherController {

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/report")
	ResponseEntity<ResponseBody<EmpBody>> getTodaysWhether() {
		ResponseBody<EmpBody> wr = new ResponseBody();
		try {
			HttpHeaders header = new HttpHeaders();
			header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			header.setContentType((MediaType.APPLICATION_JSON));
			// http://dummy.restapiexample.com/
			final HttpEntity<?> requestEntity = new HttpEntity<>(header);

			final ResponseEntity<EmpBody> response = restTemplate.exchange(
					"http://dummy.restapiexample.com/api/v1/employees", HttpMethod.GET, requestEntity,
					new ParameterizedTypeReference<EmpBody>() {
					});
			MsgLst lst = new MsgLst();
			if (null != response.getBody()) {
				wr.setEntity(response.getBody());
				lst.setCode("200");
				wr.setMsgLst(lst);
			} else {
				lst.setCode("404");
				wr.setMsgLst(lst);
			}

		} catch (Exception e) {

			System.out.println(e);
		}

		return new ResponseEntity<>(wr, HttpStatus.CREATED);
	}

}
