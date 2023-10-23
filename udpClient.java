//-----------------------------------------------------------
//  Purpose:    Receive Packets and Interact with Rest of Program
//  Author:     Seth Howard
//-----------------------------------------------------------

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import javax.xml.crypto.Data;
import java.util.concurrent.Semaphore;


/*
 * Tasks for Integration:
 *  - [] Get start condition when countdown timer ends from PAS
 * 		 - 
 *  - [] Send tag information to PAS
 * 		 - taggerID and TagID
 *  - [] Get end condition when game time ends from PAS
 * 
 * Notes: 
 *  - Main udp file and implements udp receive
 *  - Implemented as thread for simultaneous running with main program
 * 
 * 
 *  - Start code = 202
 *  - End code = 221
 *  - Red Base hit = 66
 *  - Green Base hit = 43
 *  - Player Entry Screen = PES
 *  - Play Action Screen = PAS
 * 
 */

public class udpClient implements Runnable
{
	static String start = "202";
	static String end = "221";
	static String redBaseHit = "66";
	static String greenBaseHit = "43";

	udpClient() {	}
	
	public void monitorPort() throws IOException
	{
		// System.out.println("Monitoring Port");
		String taggerID = "", tagID = "", temp = "";

		DatagramSocket receive = new DatagramSocket(7501);
		byte[] buffer = new byte[65535];
		DatagramPacket packet = null;

		while(true)
		{
			packet = new DatagramPacket(buffer, buffer.length);
			receive.receive(packet);
			temp = data(buffer).toString();
			buffer = new byte[65535];

			System.out.println("Received: [" + temp + "]");

			tagID = getTaggedID(temp);
			taggerID = getTagger(temp);

			if(temp.contains(":"))
			System.out.println(taggerID + " tagged " + tagID);

			// send tag to player if not base hit
			if (tagID != "" && tagID != redBaseHit && tagID != greenBaseHit)
				udpBroadcast.sendPacket(tagID);
		}
	}

	public static String getTaggedID(String temp)
	{
		String tagged = "";
		for (int i = 0; i < temp.length(); i++) // gets tagged player id
		{
			if(temp.charAt(i) == ':')
			{
				tagged = temp.substring(i + 1, temp.length());
				break;
			}
		}
		return tagged;
	}

	public static String getTagger(String temp)
	{
		String tagger = "";
		for (int i = 0; i < temp.length(); i++) // gets player id of tagger
		{
			if(temp.charAt(i) == ':')
			{
				tagger = temp.substring(0, i);
				break;
			}
		}
		return tagger;
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

	@Override
	public void run()
	{
		// System.out.println("Running Client");
		try {
			monitorPort();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
