package com.springstudy.ch02exam.main;

import java.util.ArrayList;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.springstudy.ch02exam.domain.Product;
import com.springstudy.ch02exam.service.ProductService;

public class ProductBeanPropertyIndex {

	public static void main(String[] args) {
		
		ApplicationContext ctx = new GenericXmlApplicationContext("classpath:config/ProductBeanConstructorContext.xml");
		
		ProductService service = (ProductService) ctx.getBean("productService");
		
		ArrayList<Product> productList = service.getProductList();
		System.out.println("## 제품 리스트 - Setter 주입 ##");
		for(Product p : productList) {
			System.out.println(p);
		}
	}
}
