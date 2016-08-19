package Common.DTO.node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import Common.DTO.node.tree.NetworkData;
import Common.DTO.node.tree.NetworkTree;
import Common.ServiceInterface.MANAGEMENT_Service_interface;
import Common.ServiceInterface.NETWORK_Service_interface;
import Common.ip.IPNetwork;

public class IpNetworkTree {
	private NetworkTree ipv4NetworkTree, ipv6NetworkTree;
	private StringBuffer ipStrBuf, ipv4StrBuf, ipv6StrBuf;

	public IpNetworkTree(MANAGEMENT_Service_interface managementService, NETWORK_Service_interface networkService,
			HashMap<String, Object> parameter) throws Exception {
		List<Map<String, Object>> list = networkService.select_SEARCHED_NETWORK_INFO(parameter);
		List<Map<String, Object>> existList = managementService.select_EXIST_CUSTOM_IP_GROUP_INFO(parameter);
		List<NetworkData> ipv4List = new ArrayList<>();
		List<NetworkData> ipv6List = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			NetworkData nd = new NetworkData(new IPNetwork(list.get(i).get("network").toString()));
			if (list.get(i).get("ip_type").equals("IPV4")) {
				ipv4List.add(nd);
			} else {
				ipv6List.add(nd);
			}
		}
		for (int i = 0; i < existList.size(); i++) {
			NetworkData nd = new NetworkData(new IPNetwork(existList.get(i).get("key").toString()));
			boolean chk = true;
			if (existList.get(i).get("ip_type").equals("IPV4")) {
				for (NetworkData n : ipv4List) {
					if (n.toString().trim().toUpperCase().equals(nd.toString().trim().toUpperCase())) {
						chk = false;
						break;
					}
				}
				if (chk)
					ipv4List.add(nd);
			} else {
				for (NetworkData n : ipv6List) {
					if (n.toString().trim().toUpperCase().equals(nd.toString().trim().toUpperCase())) {
						chk = false;
						break;
					}
				}
				if (chk)
					ipv6List.add(nd);
			}
		}
		Common.DTO.node.tree.NetworkCompare netCompare = new Common.DTO.node.tree.NetworkCompare();
		Collections.sort(ipv4List, netCompare);
		Collections.sort(ipv6List, netCompare);
		ipv4NetworkTree = new NetworkTree(new NetworkData(new IPNetwork("0.0.0.0/0")));
		ipv6NetworkTree = new NetworkTree(new NetworkData(new IPNetwork("::/0")));
		NetworkTree.Node node;
		for (NetworkData nd : ipv4List) {

			if ((node = ipv4NetworkTree.getRoot().getParentNode(nd.toString())) == null) {
				ipv4NetworkTree.getRoot().addChildren(new NetworkTree.Node(nd));
			} else {
				node.addChildren(new NetworkTree.Node(nd));
			}
		}
		for (NetworkData nd : ipv6List) {

			if ((node = ipv6NetworkTree.getRoot().getParentNode(nd.toString())) == null) {
				ipv6NetworkTree.getRoot().addChildren(new NetworkTree.Node(nd));
			} else {
				node.addChildren(new NetworkTree.Node(nd));
			}
		}
		
		ipStrBuf = new StringBuffer();
		ipv4StrBuf = new StringBuffer();
		ipv6StrBuf = new StringBuffer();
		Map<String, Object> tmpMap = new HashMap<String, Object>();
		tmpMap.put("ip_type", "IPV6");
		tmpMap.put("key", "::/0");
		tmpMap.put("group_name", "IPV6");
		existList.add(tmpMap);
		tmpMap = new HashMap<String, Object>();
		tmpMap.put("ip_type", "IPV4");
		tmpMap.put("key", "0.0.0.0/0");
		tmpMap.put("group_name", "IPV4");
		existList.add(tmpMap);
		ipStrBuf.append("[");
		ipv4NetworkTree.getRoot().getNodeJsonInfo(ipv4StrBuf, existList);
		ipStrBuf.append(ipv4StrBuf);
		ipStrBuf.append(",");
		ipv6NetworkTree.getRoot().getNodeJsonInfo(ipv6StrBuf, existList);
		ipStrBuf.append(ipv6StrBuf);
		ipStrBuf.append("]");
	}
	
	public NetworkTree getIPv4NetworkTree() {
		return ipv4NetworkTree;
	}

	public NetworkTree getIPv6NetworkTree() {
		return ipv6NetworkTree;
	}
	/** IPv4, IPv6 Network Tree를 기준으로 jstree 형식에 맞춰 json데이터를 만듬 */
	public String getIPNodeJsonStr4jstree() {
		return ipStrBuf.toString();
	}
	/** IPv4 Network Tree를 기준으로 jstree 형식에 맞춰 json데이터를 만듬 */
	public String getIPv4NodeJsonStr4jstree() {
		return ipv4StrBuf.toString();
	}
	/** IPv6 Network Tree를 기준으로 jstree 형식에 맞춰 json데이터를 만듬 */
	public String getIPv6NodeJsonStr4jstree() {
		return ipv6StrBuf.toString();
	}
	
	
}
