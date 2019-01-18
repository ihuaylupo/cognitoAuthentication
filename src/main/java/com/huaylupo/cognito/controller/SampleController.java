/**
 * 
 */
package com.huaylupo.cognito.controller;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ihuaylupo
 *
 */

@RestController
@RequestMapping("sample")
public class SampleController {

	@CrossOrigin
	@RequestMapping(method = POST)
	public ResponseEntity<String> sample(){
		return ResponseEntity.ok("OAUTH2 Token validation success");
	}
}
