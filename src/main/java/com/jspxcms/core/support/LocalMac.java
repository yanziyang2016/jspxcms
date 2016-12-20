package com.jspxcms.core.support;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
/*
 * 物理地址是48位，别和ipv6搞错了
 */
public class LocalMac {
	/**
	 * @param args
	 * @throws UnknownHostException 
	 * @throws SocketException 
	 */
	public static void main(String[] args) throws UnknownHostException, SocketException {
		// TODO Auto-generated method stub
		
		System.out.println(getLocalMac());
	}
	public static String getLocalMac() throws SocketException {
		InetAddress ia=null;
		try {
			ia = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		//获取网卡，获取地址
		byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
		StringBuffer sb = new StringBuffer("");
		for(int i=0; i<mac.length; i++) {
			if(i!=0) {
				sb.append("-");
			}
			//字节转换为整数
			int temp = mac[i]&0xff;
			String str = Integer.toHexString(temp);
			if(str.length()==1) {
				sb.append("0"+str);
			}else {
				sb.append(str);
			}
		}
		return sb.toString().toUpperCase();
//		System.out.println("本机MAC地址:"+sb.toString().toUpperCase());
	}
}
