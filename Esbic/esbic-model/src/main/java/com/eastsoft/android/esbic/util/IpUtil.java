package com.eastsoft.android.esbic.util;

import com.eastsoft.android.esbic.util.http.HttpRequestMethodEnum;
import com.eastsoft.android.esbic.util.http.HttpResult;
import com.eastsoft.android.esbic.util.http.HttpServiceImpl;
import com.eastsoft.android.esbic.util.http.IHttpService;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class IpUtil
{
	public static String getMyPublicIp()
	{
		try{
			String url = "http://www.ip138.com/ip2city.asp";
			
			IHttpService service = new HttpServiceImpl.Builder(url)
									.requestPropertyies("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.2.15) Gecko/20110303 Firefox/3.6.15")
									.requestPropertyies("Content-Type", "text/html")
									.requestPropertyies("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
									.requestMethod(HttpRequestMethodEnum.GET)
									.build();
			
			HttpResult result = service.getHttpResult(null);
			if(result.isSuccess() == false)
			{
				return null;
			}
			String content = new String(result.content);
			int start = content.indexOf('[')+1;
			int end = content.indexOf(']');

			if(start > end || start < 0 || end < 0)
			{
				return null;
			}
			
			String ip = content.substring(start, end);
			return ip;
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static String getMyPrivateIp()
	{
		try{
			return InetAddress.getLocalHost().getHostAddress();
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<InetAddress> getAllLocalHost()
	{
		List<InetAddress> addrs = new ArrayList<InetAddress>();
		Enumeration<NetworkInterface> netInterfaces = null;
		try
		{
			netInterfaces = NetworkInterface.getNetworkInterfaces();
			Enumeration<InetAddress> ips = null;
			while (netInterfaces.hasMoreElements())
			{
				NetworkInterface ni = netInterfaces.nextElement();
				ips = ni.getInetAddresses();
				while (ips.hasMoreElements())
				{
					InetAddress addr = ips.nextElement();
					if (addr instanceof Inet4Address)  
					{  
						addrs.add(addr);
					}  
				}
			}
			return addrs;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		getMyPublicIp();
		System.out.println(getMyPrivateIp());
		System.out.println(getAllLocalHost().toString());
	}
	
}
