package com.shinwootns.ipm.controller;

import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardController {
	
	@RequestMapping("/Dashboard/Summary")
	public String Summary() {
		
		Random random = new Random();
		
		JSONObject jObj = new JSONObject();
		
		// CPU
		JSONArray cpuArray = new JSONArray(); 
		
		for(int i=0; i < 4; i++) {
			
			JSONObject cpuObj = new JSONObject();
			cpuObj.put("seq", String.format("cpu%d", i));
			cpuObj.put("value", (30 + random.nextInt(20) % 50));
			cpuObj.put("unit", "%");
			
			cpuArray.add(cpuObj);
		}
		
		jObj.put("CPU", cpuArray);
		
		
		// MEMORY
		JSONObject memObject = new JSONObject();
		memObject.put("value", 4);
		memObject.put("total", 8);
		memObject.put("unit", "G");
		
		jObj.put("MEMORY", memObject);
		
		
		// DISK
		JSONArray diskArray = new JSONArray(); 
		
		JSONObject disk1 = new JSONObject();
		disk1.put("seq", "/");
		disk1.put("usage", 5638);
		disk1.put("total", 28111);
		disk1.put("unit", "M");
		diskArray.add(disk1);
		
		JSONObject disk2 = new JSONObject();
		disk2.put("seq", "/dev/boot");
		disk2.put("usage", 165);
		disk2.put("total", 497);
		disk2.put("unit", "M");
		diskArray.add(disk2);
		
		jObj.put("DISK", diskArray);
		
		// NETWORK
		JSONArray networkArray = new JSONArray(); 
		
		JSONObject byte1 = new JSONObject();
		byte1.put("seq", "IN");
		byte1.put("usage", random.nextInt(10)+5);
		byte1.put("total", 1000);
		byte1.put("unit", "M");
		networkArray.add(byte1);
		
		JSONObject bytes2 = new JSONObject();
		bytes2.put("seq", "OUT");
		bytes2.put("usage", random.nextInt(10)+5);
		bytes2.put("total", 1000);
		bytes2.put("unit", "M");
		networkArray.add(bytes2);
		
		jObj.put("NETWORK", networkArray);
		
		// HA
		JSONObject haObj = new JSONObject();
		haObj.put("STATE", "FALSE");
		
		jObj.put("HA", haObj);
		
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
		
		return jObj.toJSONString();
	}
}
