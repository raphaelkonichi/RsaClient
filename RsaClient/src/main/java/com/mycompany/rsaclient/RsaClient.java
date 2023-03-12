/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.rsaclient;
import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.util.Scanner;
/**
 *
 * @author rapha
 */
public class RsaClient {

    public static void main(String args[]) throws IOException
    {
        DatagramSocket ds = new DatagramSocket();
  
        InetAddress ip = InetAddress.getLocalHost();
                
        String msgcifrada;
        BigInteger n, e;
        int bitlen = 4096;

        SecureRandom r = new SecureRandom();
        BigInteger p = new BigInteger(bitlen / 2, 100, r); 
        BigInteger q = new BigInteger(bitlen / 2, 100, r); 

        n = p.multiply(q);

        BigInteger m = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        e = new BigInteger("3");
        while(m.gcd(e).intValue() > 1) e = e.add(new BigInteger("2"));
        
        byte buf[];
  
        while (true)
        {
            Scanner sc = new Scanner(System.in);
            
            System.out.print("Digite a mensagem que será criptografada e enviada: ");
            String inp = sc.nextLine();
            
            msgcifrada = new BigInteger(inp.getBytes()).modPow(e, n).toString();
            System.out.println("Mensagem cifrada: "+ msgcifrada);
  
            buf = msgcifrada.getBytes();
  
            DatagramPacket DpSend =
                  new DatagramPacket(buf, buf.length, ip, 4445);
  
            ds.send(DpSend);
  
            if (inp.equals("fechar"))
                break;
        }
    }
}
