package cn.ssm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.ssm.dto.Film;
import cn.ssm.dto.address;
import cn.ssm.dto.customer;
import cn.ssm.mapper.Filmapper;
import cn.ssm.service.Filmservice;

@Service
@Transactional
public class Filmserviceimpl implements Filmservice {
	
	@Autowired
	private Filmapper filmapper;

	public List<Film> select(int a) {
		
		return filmapper.select(a);
	}

	public int selectall() {
		
		return filmapper.selectall();
	}

	public List<customer> findcustomer(String name) {
		
		return filmapper.findcustomer(name);
	}

	public int insertfilm(Film film) {
		
		return filmapper.insertfilm(film);
	}

	public List<customer> findallcustomer(int a) {
		
		return filmapper.findallcustomer(a);
	}

	public int selectcustomercount() {
		// TODO Auto-generated method stub
		return filmapper.selectcustomercount();
	}

	public List<customer> findaddress() {
		
		return filmapper.findaddress();
	}

	public String findaddressid(String address) {
		// TODO Auto-generated method stub
		return filmapper.findaddressid(address);
	}

	public int insertcustomer(customer c) {
		
		return filmapper.insertcustomer(c);
	}

	public int deletecustomer(int id) {
		
		return filmapper.deletecustomer(id);
	}

	public int updatecustomer(customer c) {
		
		return filmapper.updatecustomer(c);
	}

	

}
