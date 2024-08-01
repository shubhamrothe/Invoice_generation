package com.example.serviceImpl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import com.example.model.Item;
import com.example.model.Order;
import com.example.service.InvoiceService;

@Service
public class InvoiceServiceImpl implements InvoiceService{

	@Override
	public byte[] generateInvoice(Order order) throws IOException {
ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		PDDocument document= new PDDocument();
		PDPage page= new PDPage();
		document.addPage(page);
		
		PDPageContentStream contentStream = new PDPageContentStream(document, page);
	//Add Header
		contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
		contentStream.beginText();
		contentStream.newLineAtOffset(50, 750);
		contentStream.showText("INVOICE");
		contentStream.endText();
		
		 contentStream.setFont(PDType1Font.HELVETICA, 12);
         contentStream.beginText();
         contentStream.newLineAtOffset(50, 720);
         contentStream.showText("Customer Name: " + order.getCustomerName());
         contentStream.endText();
		
         //Add Table Headers
         contentStream.beginText();
         contentStream.newLineAtOffset(50, 680);
         contentStream.showText("Item");
         contentStream.newLineAtOffset(200, 0);
         contentStream.showText("Quantity");
         contentStream.newLineAtOffset(100, 0);
         contentStream.showText("Unit Price");
         contentStream.endText();
		
		//Add Items
        contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 12);
         int yOffset=660;
         for(Item item : order.getItems()) {
         contentStream.beginText();
         contentStream.newLineAtOffset(50, yOffset);
         contentStream.showText(item.getProductName());
         contentStream.newLineAtOffset(200, 0);
         contentStream.showText(String.valueOf(item.getQuantity()));
         contentStream.newLineAtOffset(100, 0);
         contentStream.showText("RS"+ item.getUnitPrice());
         contentStream.endText();
         yOffset -=20;    
         }
         
         contentStream.beginText();
         contentStream.newLineAtOffset(50, yOffset-20);
         double totalAmount = calculateTotal(order.getItems());
         contentStream.showText("Total Amount: RS" +totalAmount);
         contentStream.endText();
         
         contentStream.close();
         document.save(byteArrayOutputStream);
         document.close();
		return byteArrayOutputStream.toByteArray();
	}

	private double calculateTotal(List<Item> items) {
		double totalAmount =0;
		for(Item item : items) {
			totalAmount += item.getQuantity() * item.getUnitPrice();
		}
		return totalAmount;
	}

}
