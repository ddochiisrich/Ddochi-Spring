package com.springclass.ch04.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springclass.ch04.dao.FirstMvcDao;

@Service
public class FirstMvcServiceImpl implements FirstMvcService {
	@Autowired
	private FirstMvcDao firstMvcDao;
	
	public void setFirstMvcDao(FirstMvcDao firstMvcDao) {
		this.firstMvcDao = firstMvcDao;
	}

	@Override
	public String getMessage(int no, String id) {
		return firstMvcDao.getMessage(no, id);
	}

}
