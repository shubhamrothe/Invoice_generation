package com.example.controller;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Item;
import com.example.model.Order;
import com.example.service.InvoiceService;

@RestController
public class InvoiceController {
	
	@Autowired
	private InvoiceService invoiceService;
	
	@GetMapping("/invoice/{orderId}")
	public ResponseEntity<byte []> getInvoice(@PathVariable Integer orderId) throws IOException {
		
	 Item i1 = new Item(1,"Product 1",5, 450.00);
	 Item i2 = new Item(2,"Product 2",10, 500.00);
	 Item i3 = new Item(3,"Product 3",15, 40.00);
	 Item i4 = new Item(4,"Product 4",20, 420.00);
	 Item i5 = new Item(5,"Product 5",25, 640.00);
	 
	Order order =	new Order(orderId,"Shubham Rothe", Arrays.asList(i1,i2,i3,i4,i5));
		byte[] bytes = invoiceService.generateInvoice(order);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");
		
		return ResponseEntity.ok().headers(headers).body(bytes);
		
	}
}
