//-----------------------------------------------------------
//  Purpose:    Broadcast Packets to Players
//  Author:     Seth Howard
//-----------------------------------------------------------

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import javax.xml.crypto.Data;

/* 
 * Notes: 
 *  - Use udpBroadcast.sendPacket(string) in
 * 	other files to broadcast information
 *  - To test without players, change port to 7501
 * 
 */

public class udpBroadcast
{
	static String start = "202";
	static String end = "221";
	String broadcastData = "";
	static int port = 7501; //WAS 7500

	udpBroadcast() {}

	public static void sendPacket(String data) throws IOException
	{
		DatagramSocket broadcast = new DatagramSocket();
		InetAddress ip = InetAddress.getByName("127.0.0.1");
		byte buffer[] = null;
		
		if (data != "")
		{
			buffer = data.getBytes();
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, ip, port);
			broadcast.send(packet);
			// System.out.println("Broadcast: " + data(buffer).toString());
		}
		else
		{
			//System.out.println("No Data for Broadcast");
		}
	}


	public static StringBuilder data(byte[] a)
	{
		if (a == null)
			return null;
		StringBuilder ret = new StringBuilder();
		int i = 0;
		while (i < a.length && a[i] != 0)
		{
			ret.append((char) a[i]);
			// System.out.println("i = " + i + "string: " + ret);
			i++;
		}
		return ret;
	}
}
