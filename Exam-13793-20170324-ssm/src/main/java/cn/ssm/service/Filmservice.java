package cn.ssm.service;

import java.util.List;

import cn.ssm.dto.Film;
import cn.ssm.dto.address;
import cn.ssm.dto.customer;

public interface Filmservice {
	 public List<Film> select(int a);
	 public int selectall();
	 public List<customer> findcustomer(String name);
	 public int insertfilm(Film film);
	 public List<customer> findallcustomer(int a);
	 public int selectcustomercount();
	 public List<customer> findaddress();
	 public String findaddressid(String address);
	 public int insertcustomer(customer c);
	 public int deletecustomer(int id);
	 public int updatecustomer(customer c);

}
