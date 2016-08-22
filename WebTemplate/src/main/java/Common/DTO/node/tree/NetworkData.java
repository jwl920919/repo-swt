package Common.DTO.node.tree;

import java.math.BigInteger;
import java.net.UnknownHostException;

import Common.ip.IPNetwork;

public class NetworkData {
	private IPNetwork ipNetwork;

	public NetworkData(IPNetwork ipNetwork) {
		this.ipNetwork = ipNetwork;
	}

	public IPNetwork getIpNetwork() {
		return ipNetwork;
	}

	public boolean isSameNetwork(IPNetwork anotherIPNetwork) throws UnknownHostException {
		BigInteger startIp = ipNetwork.getStartIP().getNumberToBigInteger();
		BigInteger endIp = ipNetwork.getEndIP().getNumberToBigInteger();
		BigInteger anotherStartIp = anotherIPNetwork.getStartIP().getNumberToBigInteger();
		BigInteger anotherEndIp = anotherIPNetwork.getEndIP().getNumberToBigInteger();
		if (endIp.compareTo(anotherEndIp) == 0) {
			if (startIp.compareTo(anotherStartIp) == 0) {
				return true;
			}
		}
		return false;
	}

	public boolean hasNetwork(IPNetwork anotherIPNetwork) throws UnknownHostException {
		BigInteger startIp = ipNetwork.getStartIP().getNumberToBigInteger();
		BigInteger endIp = ipNetwork.getEndIP().getNumberToBigInteger();
		BigInteger anotherStartIp = anotherIPNetwork.getStartIP().getNumberToBigInteger();
		BigInteger anotherEndIp = anotherIPNetwork.getEndIP().getNumberToBigInteger();

		switch (endIp.compareTo(anotherEndIp)) {
		case 0:
		case 1:
			switch (startIp.compareTo(anotherStartIp)) {
			case -1:
			case 0:
				return true;
			default:
				return false;
			}
		default:
			return false;
		}
	}

	@Override
	public String toString() {
		return ipNetwork.toString();
	}
}
