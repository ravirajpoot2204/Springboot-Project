package com.smartcontact.service;

import java.util.Random;
import org.springframework.stereotype.Service;

@Service
public class OTPGenerator {
    
    public int otpGenerator() {
	

	// generating otp of 6 number
	Random random = new Random();
	int otp = random.nextInt(100000, 999999);

	return otp;
    }

}
