package com.example.demo.paytm.pay;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RazorpayConfig {

	@Value("${rzp_key_id}")
    private String apiKey;

    @Value("${rzp_key_secret}")
    private String apiSecret;

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getApiSecret() {
		return apiSecret;
	}

	public void setApiSecret(String apiSecret) {
		this.apiSecret = apiSecret;
	}
    
    

}
