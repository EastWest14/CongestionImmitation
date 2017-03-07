package congestion;

import java.util.*;

public interface Receiver {
	public void receive(Packet p);
	public List<Packet> allReceivedPackets();
}