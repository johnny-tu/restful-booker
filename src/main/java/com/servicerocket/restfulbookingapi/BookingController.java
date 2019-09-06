package com.servicerocket.restfulbookingapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.servicerocket.restfulbookingapi.models.Booking;
import com.servicerocket.restfulbookingapi.models.User;
import com.servicerocket.restfulbookingapi.repositories.BookingRepository;

import javax.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/")
public class BookingController {

	private Map authTokens = new HashMap();
	
	@Autowired
	private BookingRepository repository;
	
	@RequestMapping(value = "/booking", method = RequestMethod.GET)
	public List<Booking> getAllBookings() {
		return repository.findAll();
	}
	
	@RequestMapping(value = "/booking/{id}", method = RequestMethod.GET)
	public ResponseEntity getBookingById(@PathVariable("id") ObjectId id) {
		Booking booking = repository.findBy_id(id);
		if (booking != null) {
			return new ResponseEntity(booking, HttpStatus.OK);
		} 
		else {
			return new ResponseEntity("Not Found", HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/booking/{id}", method = RequestMethod.PUT)
	public ResponseEntity modifyBookingById(@PathVariable("id") ObjectId id, @Valid @RequestBody Booking booking, @RequestHeader HttpHeaders headers) {
		/*
		List<String> cookie = headers.get("Cookie");
		String cookieString = cookie.get(0);
		String token = cookieString.substring(cookieString.indexOf("=")+1); 
		
		System.out.println("cookie : " + cookie);
		System.out.println("cookieString : " + cookieString);
		System.out.println("token : " + token);
		*/
		
		String checkAuthTokenResponse = checkAuthToken(headers);
		if (checkAuthTokenResponse != "") {
			return new ResponseEntity(checkAuthTokenResponse, HttpStatus.FORBIDDEN);
		}
		else {
			//Check that booking exists, otherwise return NOT FOUND
			if (repository.findBy_id(id) != null) {
				booking.set_Id(id);
				repository.save(booking);
				return new ResponseEntity(booking, HttpStatus.OK);
			}
			else {
				return new ResponseEntity("Not Found", HttpStatus.NOT_FOUND);
			}
		}
	}
	
	@RequestMapping(value = "/booking", method = RequestMethod.POST)
	public Booking createBooking(@Valid @RequestBody Booking booking) {
	    booking.set_Id(ObjectId.get());
	    repository.save(booking);
	    return booking;
	}
	  
	@RequestMapping(value = "/booking/{id}", method = RequestMethod.DELETE)
	public ResponseEntity deleteBooking(@PathVariable ObjectId id, @RequestHeader HttpHeaders headers) {
		System.out.println(authTokens);
		
		/*
		List<String> cookie = headers.get("Cookie");
		String cookieString = cookie.get(0);
		String token = cookieString.substring(cookieString.indexOf("=")+1); 
		
		if (cookie == null) {
			return new ResponseEntity("Forbidden", HttpStatus.FORBIDDEN);
		}
		else if (authTokens.get(token) == null) {
			return new ResponseEntity("Invalid API Token", HttpStatus.FORBIDDEN);
		}
		*/
		String checkAuthTokenResponse = checkAuthToken(headers);
		System.out.println(checkAuthTokenResponse);
		if (checkAuthTokenResponse != "") {
			return new ResponseEntity(checkAuthTokenResponse, HttpStatus.FORBIDDEN);
		}
		else {
			Booking booking = repository.findBy_id(id);
			if (booking != null) {
				repository.delete(repository.findBy_id(id));
				return new ResponseEntity("Booking Deleted", HttpStatus.OK);
			} else {
				return new ResponseEntity("Booking Not Found", HttpStatus.NOT_FOUND);
			}
		}
		
	}
	
	@RequestMapping(value = "/auth", method = RequestMethod.POST)
	public Map getAuthToken(@RequestBody User user) {
		Map map = new HashMap();
		/*
		 * The username and password accepted is hardcoded for now
		 */
		if (user.getUsername().equals("admin") && user.getPassword().equals("password123")) {
			String authToken = UUID.randomUUID().toString();
			map.put("token", authToken);
			authTokens.put(authToken, authToken);
		} else {
			String message = "Bad Credentials";
			map.put("reason", message);
		}

		return map;
	}
	
	@RequestMapping(value = "/ping", method = RequestMethod.GET)
	public String ping() {
		return "Application Running";
	}
	
	private String checkAuthToken(HttpHeaders headers) {
		List<String> cookie = headers.get("Cookie");
		String cookieString = cookie.get(0);
		String token = cookieString.substring(cookieString.indexOf("=")+1); 
		
		String response = ""; 
		
		if (cookie == null) {
			response = "Forbidden";
		}

		if (authTokens.get(token) == null) {
			response = "Invalid API Token";
		}
		
		return response;
	}
	
}
