package Common.DTO.node.tree;

import java.math.BigInteger;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Common.ip.IPAddr;
import Common.ip.IPNetwork;
import Common.ip.ipv6.IPv6Address;

public class NetworkTree extends Tree {

	private Node root;

	public NetworkTree(NetworkData rootData) {
		super(rootData);
		root = new Node(rootData);
		root.depth = 0;
		root.children = new ArrayList<>();
	}

	public static class Node {
		private NetworkData data;
		private Node parent;
		private List<Node> children;
		private int depth;

		public Node(NetworkData data) {
			this.data = data;
			children = new ArrayList<>();
		}

		public void addChildren(Node child) {
			child.parent = this;
			child.depth = this.depth + 1;
			children.add(child);
		}

		public boolean hasData(String network) throws UnknownHostException {
			boolean hasData = false;
			if (this.children != null) {
				for (Node child : this.children) {
					if (child.hasData(network)) {
						hasData = true;
						break;
					}
				}
			}
			return this.data.hasNetwork(new IPNetwork(network)) | hasData;
		}
		public boolean isSameData(String network) throws UnknownHostException {
			boolean isSameData = false;
			if (this.children != null) {
				for (Node child : this.children) {
					if (child.isSameData(network)) {
						isSameData = true;
						break;
					}
				}
			}
			return this.data.isSameNetwork(new IPNetwork(network)) | isSameData;
		}
		/** 해당 네트워크 노드 검색 */
		public Node getNode(String network) throws UnknownHostException {
			if (this.children != null) {
				for (Node n1 : this.children) {
					if (n1.hasData(network)) {
						if (n1.isSameData(network)) {
							return n1;
						} else {
							return n1.getNode(network);
						}
					}
				}
			}
			return null;
		}
		/** 해당 네트워크의 부모노드 검색 */
		public Node getParentNode(String network) throws UnknownHostException {
			if (this.children != null) {
				for (Node n1 : this.children) {
					if (n1.hasData(network)) {
						if (n1.isChildNodeHasData(network)) {
							return n1.getParentNode(network);
						} else {
							return n1;
						}
					}
				}
			}
			return this;
		}
		/** 해당 네트워크가 자식 노드에 존재하는지 여부*/
		public boolean isChildNodeHasData(String network) throws UnknownHostException {
			if (this.children != null) {
				for (Node child : this.children) {
					if (child.hasData(network)) {
						return true;
					}
				}
			}
			return false;
		}
		/** 노드 정보를 jstree 형식의 json 데이터로 변경해주는 메소드*/
		public void getNodeJsonInfo(StringBuffer sb,List<Map<String,Object>> existList) throws UnknownHostException {
			sb.append("{\"text\":");
			sb.append('"');
			String ipStr = null;
			if(this.data.getIpNetwork().getStartIP().isIPv6()){
				BigInteger nwip = this.data.getIpNetwork().getStartIP().getNumberToBigInteger().add(new BigInteger("0"));
				IPv6Address nwIp = IPv6Address.fromBigInteger(nwip);
				ipStr = (nwIp.toString().toUpperCase()+"/"+this.data.getIpNetwork().getPrefixLength());
			} else {
				ipStr = (this.data.getIpNetwork().getStartIP().toString()+"/"+this.data.getIpNetwork().getPrefixLength());
			}
			boolean isExist = false;
			for(Map<String,Object> existRecord :existList) {
				if(ipStr.toUpperCase().equals(existRecord.get("key").toString().toUpperCase())){
					if(!existRecord.get("group_name").toString().trim().equals(""))
						sb.append(existRecord.get("group_name"));
					else sb.append(ipStr);
					isExist = true;
					break;
				}
			}
			if(!isExist){
				sb.append(ipStr);
			}
			sb.append("\",");
			sb.append("\"a_attr\":{\"value\":\"");
			sb.append(ipStr);
			sb.append("\"}");
			
			if (this.children.size() > 0)
				sb.append(",\"children\":[");
			for (int i = 0; i < this.children.size(); i++) {
				Node child = this.children.get(i);
				child.getNodeJsonInfo(sb, existList);
			}
			if (this.children.size() > 0)
				sb.append("]");
			sb.append("}");
			if (this.parent != null) {
				if (!(this.parent.children.indexOf(this) == this.parent.children.size() - 1))
					sb.append(",");
			}
		}
		
		/**자신을 포함한 하위노드 모두 출력*/
		public static void printNode(Node node) {
			if(node.depth==0){
				System.out.println(node.data);
				if(node.children!= null) {
					for(Node c:node.children) {
						printNode(c);
					}
				}
			} else {
				makeTab(node.depth);
				System.out.println(node.data);
				if(node.children!= null) {
					for(Node c:node.children) {
						printNode(c);
					}
				}
			}
		}
		private static void makeTab(int cnt) {
			for (int i = 0; i < cnt; i++) {
				System.out.print('\t');
			}
		}
	}
	
	

	public Node getRoot() {
		return root;
	}
}
