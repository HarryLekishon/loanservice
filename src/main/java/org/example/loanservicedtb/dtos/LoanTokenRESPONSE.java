package org.example.loanservicedtb.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoanTokenRESPONSE {

    public String access_token;
    public String token_type;
    public Long expires_in;
    private Long expirationTime;

    public LoanTokenRESPONSE(String access_token, String token_type, Long expires_in) {
        this.access_token = access_token;
        this.token_type = token_type;
        this.expires_in = expires_in;
        setExpires_in_long(this.expires_in);
        this.expirationTime = System.currentTimeMillis() + (this.expires_in * 1000);
    }

    public void setExpires_in_long(Long expiry) {
        if( expiry != null){
            expires_in = expiry;//equivalent to 3000 seconds
        }
    }

    public boolean isExpired() {
        return System.currentTimeMillis() >= expirationTime;
    }
}
