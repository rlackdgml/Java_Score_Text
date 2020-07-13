package com.biz.address.exec;

import java.util.ArrayList;
import java.util.List;

import com.biz.addr.AddrVO;

public class AddrEx_01 {

	public static void main(String[] args) {
		
		
		List<AddrVO> addrList = new ArrayList<AddrVO>();
		
		AddrVO addr1 = new AddrVO();
		addr1.setName("ㅇ");
		addr1.setTel("0100");
		addr1.setAge(10);
		addr1.setAddr("주소");
		
		System.out.println(addr1.getAddr());
	
	
		
		
		
	}

}
