package com.example.service;

import java.io.IOException;

import com.example.model.Order;

public interface InvoiceService {

	public byte[] generateInvoice(Order order) throws IOException;
}
