package Common.DTO.node;

import java.util.Comparator;

import Common.DTO.node.tree.NetworkData;

public class NetworkCompare implements Comparator<NetworkData> {
    @Override
    public int compare(NetworkData nd1, NetworkData nd2) {
        int c = nd1.getIpNetwork().getStartIP().getNumberToBigInteger().compareTo(nd2.getIpNetwork().getStartIP().getNumberToBigInteger());
        if( c==0) {
            return nd2.getIpNetwork().getEndIP().getNumberToBigInteger().compareTo(nd1.getIpNetwork().getEndIP().getNumberToBigInteger());
        }
        return c;
    }
}
