package com.smartcontact.controller;

import java.security.Principal;
import java.util.Map;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.razorpay.*;
import com.smartcontact.dao.OrderRepo;
import com.smartcontact.dao.UserRepo;
import com.smartcontact.entities.MyOrders;


@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private UserRepo userRepo;

    @PostMapping("/create-Order")
    @ResponseBody
    public String createOrder(@RequestParam(value="error",required = false) String error,@RequestBody Map<String, Object> data, Principal principal) throws Exception {

	if (error != null) {
	    throw new IllegalArgumentException("An Error occurred");
	}
	/* System.out.println("Hey i have got call from client to create order.."); */
	/* System.out.println(data); */
	int amt = Integer.parseInt(data.get("amount").toString());

	RazorpayClient razorpayClient = new RazorpayClient("rzp_test_xdazwrEC2gX5qS", "nX12Q87PWnMjvdjomqCCbbyC");

	JSONObject object = new JSONObject();
	object.put("amount", amt * 100);
	object.put("currency", "INR");
	object.put("receipt", "TXN_20562351");

	// creating new order

	Order order = razorpayClient.Orders.create(object);
	System.out.println(order);

	// saving the order in db

	MyOrders myOrders = new MyOrders();

	Integer amount = order.get("amount");

	myOrders.setAmount(amount / 100);

	myOrders.setOrderId(order.get("id"));

	myOrders.setPaymentID(null);

	myOrders.setStatus("created");

	myOrders.setUser(this.userRepo.getUserByUserName(principal.getName()));

	myOrders.setReceipt(order.get("receipt"));

	this.orderRepo.save(myOrders);

	return order.toString();
    }

    @PostMapping("/update-Order")
    public ResponseEntity<?> updateOrder(@RequestParam(value="error",required = false) String error,@RequestBody Map<String, Object> data) {

	if (error != null) {
	    throw new IllegalArgumentException("An Error occurred");
	}
	MyOrders myOrders = this.orderRepo.findByOrderId(data.get("id").toString());

	myOrders.setPaymentID(data.get(data.get("payment_id")).toString());

	myOrders.setStatus(data.get("status").toString());

	this.orderRepo.save(myOrders);

	System.out.println(data);

	return ResponseEntity.ok(Map.of("msg", "updated"));
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error_page";
    }
}
