
 Endpoints:
   Server.Port = 8081

Tasks and Status: -

1. The API entercandidate shall take a name as a parameter and save that into a table with a a count(vote count) initialized to 0.
 http://localhost:8081/entercandidate?name=ajay -- Completed

2. The API castvote shall take a name as a parameter and increment the vote count and return it. It should enter vote only for a valid candidate.
 http://localhost:8081/castvote?name=ajay -- Completed
 
3. The API countvote shall take a name as a parameter and should return the latest vote count. Validate candidate name.
 http://localhost:8081/countvote?name=ajay -- Not Completed(Looks similar to second endpoint - waiting for the clarification)

4. The API listvote should return all names and votecounts. The return value is in JSON.
 http://localhost:8081/listvote -- Completed

5. The API getwinner should return the name of the candidate who got the largest number of votes.
 http://localhost:8081/getwinner -- Completed

6. APIs shall be simultaneously executed by multi-users. -- Working on it

7. Implement unit testing -- Already informed and don't have enough knowledge but I'll learn to do it.

