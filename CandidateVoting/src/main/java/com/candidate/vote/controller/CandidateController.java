package com.candidate.vote.controller;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class CandidateController {

	// Storage variable for candidate
	private static Map<String, Integer> candidateData_Map = new HashMap<>();

	// To save a candidate name
	@PostMapping("/entercandidate")
	public ResponseEntity<?> saveCandidate(@RequestParam("name") String candidateName) {

		// Check whether candidate name is already in the Map variable
		if (!candidateData_Map.containsKey(candidateName)) {
			// Insert the data when it is new Candidate
			candidateData_Map.put(candidateName, 1);
		} else {
			return new ResponseEntity<>(" Candidate Already Exists ", HttpStatus.NOT_ACCEPTABLE);
		}

		return new ResponseEntity<>("Data Saved ", HttpStatus.ACCEPTED);
	}

	// To update a candidate vote by candidateName
	@PutMapping("/castvote")
	public ResponseEntity<?> updateCandidate(@RequestParam("name") String candidateName) {

		try {
			candidateName = candidateName.trim();
			if (candidateName != null && !candidateName.isBlank()) {
				// JSON response object declaration
				JSONObject jsonResponseObject = new JSONObject();

				// Check whether candidate name is already in the Map variable
				if (candidateData_Map.containsKey(candidateName)) {
				
					// Update the vote
					candidateData_Map.put(candidateName, candidateData_Map.get(candidateName) + 1);
					
					// Create the json data for valid candidate - Contains incremented value.
					jsonResponseObject.put("status", "Success");
					jsonResponseObject.put("vote", candidateData_Map.get(candidateName));

				} else {
					return new ResponseEntity<>("No Such Candidate Exists", HttpStatus.NOT_FOUND);
				}
				return new ResponseEntity<>(jsonResponseObject, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Name Can't be NULL", HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
		}
	}

	@GetMapping("/countvote")
	public ResponseEntity<?> getCandidateVote(@RequestParam("name") String candidateName) {

		// ---- JSON OBject to return the data

		// Check whether candidate name is already in the Map variable
		if (candidateData_Map.containsKey(candidateName)) {
			// Update the vote
			candidateData_Map.put(candidateName, candidateData_Map.get(candidateName) + 1);
		} else {
			return new ResponseEntity<>("No Such Candidate Exists ", HttpStatus.NOT_ACCEPTABLE);
		}

		return new ResponseEntity<>(candidateData_Map.get(candidateName), HttpStatus.ACCEPTED);

	}

	@GetMapping("/listvote")
	public ResponseEntity<?> getAllCandidate() {

		// ---- JSON OBject to return the data

		// Check whether candidate name is already in the Map variable
		if (candidateData_Map.isEmpty()) {
			// Update the vote

		} else {
			return new ResponseEntity<>("No Such Candidate Exists ", HttpStatus.NOT_ACCEPTABLE);
		}

		return new ResponseEntity<>("", HttpStatus.ACCEPTED);

	}

	@GetMapping("/getwinner")
	public ResponseEntity<?> getWinnerCandidate() {

		// ---- JSON OBject to return the data

		// Check whether candidate name is already in the Map variable
		if (candidateData_Map.isEmpty()) {
			// Update the vote

		} else {
			return new ResponseEntity<>("No Such Candidate Exists ", HttpStatus.NOT_ACCEPTABLE);
		}

		return new ResponseEntity<>("", HttpStatus.ACCEPTED);

	}

}
