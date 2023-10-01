//-----------------------------------------------------------
//  Purpose:    Link Sockets to Rest of Program
//  Author:     Seth Howard
//-----------------------------------------------------------

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import javax.xml.crypto.Data;


/*
 * Tasks for Integration:
 *  - [] Get equipment ids from PES
 * 		 - not sure if this is needed, could just send packet from PES
 *  - [] Get start condition when countdown timer ends from PAS
 *  - [] Send tag information to PAS
 * 		 - taggerID and TagID
 *  - [] Get end condition when game time ends from PAS
 * 
 * Notes: 
 *  - Main udp file and implements udp receive
 *  - Might need to be run separate from rest of program because
 * 	it waits to receive new packet before it continues
 *  - udpBroadcast is offshoot that can be called whenever
 * 		- use udpBroadcast.sendPacket(string message);
 * 
 * 
 *  - Start code = 202
 *  - End code = 221
 *  - Red Base hit = 66
 *  - Green Base hit = 148
 *  - Player Entry Screen = PES
 *  - Play Action Screen = PAS
 * 
 */

public class udpClient
{
	static String start = "202";
	static String end = "221";
	static String redBaseHit = "66";
	static String greenBaseHit = "148";
	
	public static void main(String[] args) throws IOException
	{
		System.out.println("udpClient Running");
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
}
