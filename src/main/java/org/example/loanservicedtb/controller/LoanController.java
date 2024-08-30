package org.example.loanservicedtb.controller;

import org.example.loanservicedtb.dtos.ErrorRESPONSE;
import org.example.loanservicedtb.dtos.NewCustomerOriginationRESPONSE;
import org.example.loanservicedtb.dtos.NewCustomerREQUEST;
import org.example.loanservicedtb.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

@RestController
@RequestMapping("/api/v1")
public class LoanController {

    @Autowired
    private LoanService loanService;

//    @RequestMapping(
//            value = "/login",
//            method = RequestMethod.POST,
//            consumes = MediaType.APPLICATION_JSON_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE
//
//    )
//    ResponseEntity<Object> AuthenticateUser(){
//
//    }
    @PostMapping("/token")
    public ResponseEntity<String> getToken() {

        try{
            String token = loanService.getToken();
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error retrieving token: " + e.getMessage());
        }
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/create-new-customer",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Object> CreateNewCustomer(@RequestBody NewCustomerREQUEST newCustomerRequest) {

        Object response = null;
        try {
            response = loanService.createNewCustomer(newCustomerRequest);
            if(response instanceof NewCustomerOriginationRESPONSE){
                return ResponseEntity.ok(response);
            } else if (response instanceof ErrorRESPONSE) {
                return ResponseEntity.status(409).body(response);
            }

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
