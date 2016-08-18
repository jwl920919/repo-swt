package Common.DTO.node;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shinwootns.common.utils.ip.IPNetwork;

import Common.DTO.node.tree.NetworkData;
import Common.DTO.node.tree.NetworkTree;

import java.io.*;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class IpmNodeFromNetworkTest {
    public static void main(String... args) throws Exception {
        File networkJson = new File("network.json");
        InputStreamReader isr = new InputStreamReader(new FileInputStream(networkJson), "UTF-8");
        Gson gson = new Gson();
        List<Map<String, Object>> list = gson.fromJson(isr,
                new TypeToken<List<Map<String, Object>>>() {
                }.getType());
        List<NetworkData> ipv4List = new ArrayList<NetworkData>();

        List<NetworkData> ipv6List = new ArrayList<NetworkData>();
        for (int i = 0; i < list.size(); i++) {
            NetworkData nd = new NetworkData(new IPNetwork(list.get(i).get("network").toString()));
            if (list.get(i).get("ip_type").equals("IPV4")) {
                ipv4List.add(nd);
            }
            else ipv6List.add(nd);
        }
        Common.DTO.node.tree.NetworkCompare netCompare = new Common.DTO.node.tree.NetworkCompare();
        Collections.sort(ipv4List, netCompare);
        Collections.sort(ipv6List, netCompare);
        NetworkTree ipv4NetworkTree = new NetworkTree(new NetworkData(new IPNetwork("0.0.0.0/0")));
        NetworkTree ipv6NetworkTree = new NetworkTree(new NetworkData(new IPNetwork("::/0")));
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
        StringBuffer ipStrBuf = new StringBuffer();
        StringBuffer ipv4StrBuf = new StringBuffer();
        StringBuffer ipv6StrBuf = new StringBuffer();
        ipStrBuf.append("[");
        ipv4NetworkTree.getRoot().getNodeJsonInfo(ipv4StrBuf);
        ipStrBuf.append(ipv4StrBuf.toString().replaceAll("0.0.0.0/0", "IPV4")) ;
        ipStrBuf.append(",");
        ipv6NetworkTree.getRoot().getNodeJsonInfo(ipv6StrBuf);
        ipStrBuf.append(ipv6StrBuf.toString().replaceAll("0:0:0:0:0:0:0:0/0", "IPV6"));
        ipStrBuf.append("]");
        System.out.println(ipStrBuf);
    }

}
