package com.shinwootns.ipm.controller;

import java.util.Random;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@RestController
public class DashboardController {
	
	@RequestMapping("/Dashboard/Summary")
	public String Summary() {
		
		Random random = new Random();
		
		JsonObject jObj = new JsonObject();
		
		// CPU
		JsonArray cpuArray = new JsonArray(); 
		
		for(int i=0; i < 4; i++) {
			
			JsonObject cpuObj = new JsonObject();
			cpuObj.addProperty("seq", String.format("cpu%d", i));
			cpuObj.addProperty("value", (30 + random.nextInt(20) % 50));
			cpuObj.addProperty("unit", "%");
			
			cpuArray.add(cpuObj);
		}
		
		jObj.add("CPU", cpuArray);
		
		
		// MEMORY
		JsonObject memObject = new JsonObject();
		memObject.addProperty("value", 4);
		memObject.addProperty("total", 8);
		memObject.addProperty("unit", "G");
		
		jObj.add("MEMORY", memObject);
		
		
		// DISK
		JsonArray diskArray = new JsonArray(); 
		
		JsonObject disk1 = new JsonObject();
		disk1.addProperty("seq", "/");
		disk1.addProperty("usage", 5638);
		disk1.addProperty("total", 28111);
		disk1.addProperty("unit", "M");
		diskArray.add(disk1);
		
		JsonObject disk2 = new JsonObject();
		disk2.addProperty("seq", "/dev/boot");
		disk2.addProperty("usage", 165);
		disk2.addProperty("total", 497);
		disk2.addProperty("unit", "M");
		diskArray.add(disk2);
		
		jObj.add("DISK", diskArray);
		
		// NETWORK
		JsonArray networkArray = new JsonArray(); 
		
		JsonObject byte1 = new JsonObject();
		byte1.addProperty("seq", "IN");
		byte1.addProperty("usage", random.nextInt(10)+5);
		byte1.addProperty("total", 1000);
		byte1.addProperty("unit", "M");
		networkArray.add(byte1);
		
		JsonObject bytes2 = new JsonObject();
		bytes2.addProperty("seq", "OUT");
		bytes2.addProperty("usage", random.nextInt(10)+5);
		bytes2.addProperty("total", 1000);
		bytes2.addProperty("unit", "M");
		networkArray.add(bytes2);
		
		jObj.add("NETWORK", networkArray);
		
		// HA
		JsonObject haObj = new JsonObject();
		haObj.addProperty("STATE", "FALSE");
		
		jObj.add("HA", haObj);
		
		/*
		{
			  "CPU": [
			    {
			      "seq": "core1",       //CPU Name
			      "value": 90, //CPU 사용률
			      "unit": "%" //Unit
			    },
			    {
			      "seq": "core2",       //CPU Name
			      "value": 80, //CPU 사용률
			      "unit": "%" //Unit
			    }
			  ],
			  "MEMORY": {
			    "value": 4,               //Memory 사용량
			    "total": 8,                //Memory 총용량
			    "unit": "G"               용
			  },
			  "DISK": [
			    {
			      "seq": "C", //드라이브 구분
			      "usage": "16",        //드라이브 사용량
			      "total": 100,          //드라이브 총용량
			      "unit": "G" //Unit
			    },
			    {
			      "seq": "D", //드라이브 구분
			      "usage": "99",        //드라이브 사용량
			      "total": 100,          //드라이브 총용량
			      "unit": "G" //Unit
			    }
			  ],
			  "NETWORK": [
			    {
			      "seq": "IN", //회선 구분
			      "usage": "16",        //회선 사용량
			      "total": 100,          //회선 Bandwidth
			      "unit": "bps"         //Unit
			    },
			    {
			      "seq": "OUT",        //회선 구분
			      "usage": "30",        //회선 사용량
			      "total": 100,          //회선 Bandwidth
			      "unit": "bps"         //Unit
			    }
			  ],
			  "HA": {
			    "STATE": "TRUE"        //이중화 설정? 상태
			  }
			}
		 */
		
		return jObj.toString();
	}
}
