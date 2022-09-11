package net.talaatharb.invoicetracker.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import net.talaatharb.invoicetracker.dtos.RequestLeaveBody;
import net.talaatharb.invoicetracker.models.Request;
import net.talaatharb.invoicetracker.services.AbsenceService;
import net.talaatharb.invoicetracker.services.RequestLeaveServices;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user/absence")
@CrossOrigin(origins = "*")
public class AbsenceController {
    @Autowired
    private AbsenceService absenceService;
    @Autowired
	RequestLeaveServices leaveServices;
    // http://localhost:8080/api/user/absence
    
    // TODO replace entity in request body or response with an actual DTO
    @PostMapping(consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE },
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<Long> postRequest(@RequestBody Request request){

        return new ResponseEntity<>(absenceService.postRequest(request), HttpStatus.OK);
    }

    @GetMapping("/request")
    public ResponseEntity<List<Request>> getAbsenceHistoryByEmployeeId(@RequestParam Long empId) {
        return ResponseEntity.ok().body(absenceService.getAllAbsenceByEmployeeId(empId));
    }

    @PostMapping("/update-requests")
    public ResponseEntity<List<Request>> updateListOfRequests(@RequestBody List<Request> absences) {
        return ResponseEntity.ok().body(absenceService.updateAllEmployeeAbsences(absences));
    }

    @GetMapping("/LeaveRequests")
    public ResponseEntity<ArrayList<RequestLeaveBody>> getUsers() {
        return ResponseEntity.ok().body(leaveServices.get_EmployeesRequests());
    }

	@PutMapping("/UpdateLeaveRequest/{request_id}")
	public ResponseEntity<Request> UpdateEmployeeRequest(@RequestBody Request req, @RequestParam int request_id) {
		return new ResponseEntity<>(req, HttpStatus.OK);
	}
}
