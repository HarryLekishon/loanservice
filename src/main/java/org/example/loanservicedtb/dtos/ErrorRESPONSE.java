package org.example.loanservicedtb.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class ErrorRESPONSE {

    public Integer status;
    public String message;
    public ArrayList<String> errors;
}
