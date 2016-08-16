package Common.DTO.node;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shinwootns.common.utils.ip.IPNetwork;

import Common.DTO.node.tree.NetworkData;
import Common.DTO.node.tree.NetworkTree;

import java.io.*;
import java.util.*;

public class IpmNodeFromNetworkTest {
    public static void main(String... args) throws Exception {
        File networkJson = new File("network.json");
        InputStreamReader isr = new InputStreamReader(new FileInputStream(networkJson), "UTF-8");
        Gson gson = new Gson();
        List<Map<String, Object>> list = gson.fromJson(isr,
                new TypeToken<List<Map<String, Object>>>() {
                }.getType());
        List<NetworkData> ipv4List = new ArrayList<>();

        List<NetworkData> ipv6List = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            NetworkData nd = new NetworkData(new IPNetwork(list.get(i).get("network").toString()));
            if (list.get(i).get("ip_type").equals("IPV4")) {
                ipv4List.add(nd);
            }
            else ipv6List.add(nd);
        }
        Collections.sort(ipv4List, new NetworkCompare());
        Collections.sort(ipv6List, new NetworkCompare());
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
        StringBuffer ipv4StringBuffer = new StringBuffer();
        StringBuffer ipv6StringBuffer = new StringBuffer();
        ipv4NetworkTree.getRoot().getNodeJsonInfo(ipv4StringBuffer);
        System.out.println(ipv4StringBuffer.toString()) ;
        ipv6NetworkTree.getRoot().getNodeJsonInfo(ipv6StringBuffer);
        System.out.println(ipv6StringBuffer.toString());
    }

    static class NetworkCompare implements Comparator<NetworkData> {
        @Override
        public int compare(NetworkData nd1, NetworkData nd2) {
            int c = nd1.getIpNetwork().getStartIP().getNumberToBigInteger().compareTo(nd2.getIpNetwork().getStartIP().getNumberToBigInteger());
            if( c==0) {
                return nd2.getIpNetwork().getEndIP().getNumberToBigInteger().compareTo(nd1.getIpNetwork().getEndIP().getNumberToBigInteger());
            }
            return c;
        }
    }

}
