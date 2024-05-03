package com.springstudy.ch02exam.main;

import java.util.ArrayList;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.springstudy.ch02exam.domain.Product;
import com.springstudy.ch02exam.service.ProductService;

public class ProductAnnotationPropertyIndex {

	public static void main(String[] args) {
		ApplicationContext ctx = new GenericXmlApplicationContext("classpath:config/annotation/AnnotationPropertyContext.xml");
		
		ProductService service = ctx.getBean("productService", ProductService.class);
		
		ArrayList<Product> productList = service.getProductList();
		System.out.println("## 회원 리스트 - Annotation Setter 주입 ##");
		for(Product p : productList) {
			System.out.println(p);
		}
	}

}
