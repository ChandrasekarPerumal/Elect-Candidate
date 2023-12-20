package com.candidate.vote.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
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

		// JSON response object declaration
		JSONObject jsonResponseObject = new JSONObject();

		try {
			if (isValidCandidateName(candidateName) && candidateName != null) {
				// Check whether candidate name is already in the Map variable
				if (!candidateData_Map.containsKey(candidateName)) {
					// Insert the data when it is new Candidate
					candidateData_Map.put(candidateName, 0);

					jsonResponseObject.put("status", true);
					jsonResponseObject.put("message", "Data Saved");
					return new ResponseEntity<>(jsonResponseObject, HttpStatus.OK);
				} else {

					jsonResponseObject.put("status", false);
					jsonResponseObject.put("message", "Candidate Already Exists");
					return new ResponseEntity<>(jsonResponseObject, HttpStatus.NOT_ACCEPTABLE);
				}

			} else {

				jsonResponseObject.put("status", false);
				jsonResponseObject.put("message", "Special characters are not accepted");
				return new ResponseEntity<>(jsonResponseObject, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {

			jsonResponseObject.put("status", false);
			jsonResponseObject.put("message", e.getMessage());
			return new ResponseEntity<>(jsonResponseObject, HttpStatus.EXPECTATION_FAILED);
		}
	}

	private boolean isValidCandidateName(String candidateName) throws Exception {

		Pattern patternOnlyCharacters = Pattern.compile("[a-zA-Z]+", Pattern.CASE_INSENSITIVE);

		Matcher matcherSpecialCharacters = patternOnlyCharacters.matcher(candidateName.trim());

		return matcherSpecialCharacters.find();
	}

	// To update a candidate vote by candidateName
	@PutMapping("/castvote")
	public ResponseEntity<?> updateCandidate(@RequestParam("name") String candidateName) {

		// JSON response object declaration
		JSONObject jsonResponseObject = new JSONObject();
		try {

			if (candidateName.trim() != null && !candidateName.trim().isBlank()) {

				// Check whether candidate name is already in the Map variable
				if (candidateData_Map.containsKey(candidateName)) {

					// Update the vote
					candidateData_Map.put(candidateName, candidateData_Map.get(candidateName) + 1);

					// Create the json data for valid candidate - Contains incremented value.
					jsonResponseObject.put("status", "Success");
					jsonResponseObject.put("vote", candidateData_Map.get(candidateName));

				} else {
					jsonResponseObject.put("status", false);
					jsonResponseObject.put("message", "No Such Candidate Exists");
					return new ResponseEntity<>(jsonResponseObject, HttpStatus.NOT_FOUND);
				}
				return new ResponseEntity<>(jsonResponseObject, HttpStatus.OK);
			} else {
				jsonResponseObject.put("status", false);
				jsonResponseObject.put("message", "Name Can't be NULL or Blank");
				return new ResponseEntity<>(jsonResponseObject, HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (Exception e) {
			jsonResponseObject.put("status", false);
			jsonResponseObject.put("message", e.getMessage());
			return new ResponseEntity<>(jsonResponseObject, HttpStatus.EXPECTATION_FAILED);
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

		// JSON response object declaration
		JSONObject jsonResponseObject = new JSONObject();

		try {
			// Check whether any data is available or not
			if (!candidateData_Map.isEmpty()) {
				// Valid data is available
				jsonResponseObject.put("status", true);
				jsonResponseObject.put("message", candidateData_Map);
				return new ResponseEntity<>(jsonResponseObject, HttpStatus.OK);
			} else {
				// No candidate is found
				jsonResponseObject.put("status", false);
				jsonResponseObject.put("message", "No data is available");
				return new ResponseEntity<>(jsonResponseObject, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			// Exception
			jsonResponseObject.put("status", false);
			jsonResponseObject.put("message", e.getMessage());
			return new ResponseEntity<>(jsonResponseObject, HttpStatus.EXPECTATION_FAILED);
		}

	}

	@GetMapping("/getwinner")
	public ResponseEntity<?> getWinnerCandidate() {

		// JSON response object declaration
		JSONObject jsonResponseObject = new JSONObject();

		try {
			// Check whether candidate name is already in the Map variable
			if (!candidateData_Map.isEmpty()) {

				// Get the candidate name who has highest vote
				String key_candidateName = Collections.max(candidateData_Map.entrySet(), Map.Entry.comparingByValue())
						.getKey();

				long moreThanOneCandidate_maxCount = candidateData_Map.entrySet().stream()
						.filter(entry -> entry.getValue().equals(candidateData_Map.get(key_candidateName))).count();

				if (moreThanOneCandidate_maxCount > 1) {
					jsonResponseObject.put("status", false);
					jsonResponseObject.put("message", "More candidate has same vote :" + moreThanOneCandidate_maxCount);
					return new ResponseEntity<>(jsonResponseObject, HttpStatus.OK);

				}

				jsonResponseObject.put("status", true);
				jsonResponseObject.put("message", key_candidateName);
				return new ResponseEntity<>(jsonResponseObject, HttpStatus.OK);

			} else {
				jsonResponseObject.put("status", false);
				jsonResponseObject.put("message", "No data available");
				return new ResponseEntity<>(jsonResponseObject, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			// Exception
			jsonResponseObject.put("status", false);
			jsonResponseObject.put("message", e.getMessage());
			return new ResponseEntity<>(jsonResponseObject, HttpStatus.EXPECTATION_FAILED);
		}
	}

}
