package com.springstudy.ch02exam.annotation;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springstudy.ch02exam.dao.ProductDAO;
import com.springstudy.ch02exam.domain.Product;
import com.springstudy.ch02exam.service.ProductService;

@Service("productService")
public class ProductServiceImplProperty implements ProductService{

	private ProductDAO productDAO;
	
	@Autowired
	public void setProductDAO(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}
	
	@Override
	public ArrayList<Product> getProductList(){
		return productDAO.getProductList();
	}
}
