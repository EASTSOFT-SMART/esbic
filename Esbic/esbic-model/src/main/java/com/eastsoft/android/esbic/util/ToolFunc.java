package com.eastsoft.android.esbic.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

public class ToolFunc
{
	private ToolFunc()
	{
	}

	public static byte[] int2byte(int num)
	{
		byte[] str = new byte[4];

		str[3] = (byte) (num % 256);
		str[2] = (byte) ((num >> 8) % 256);
		str[1] = (byte) ((num >> 16) % 256);
		str[0] = (byte) ((num >> 24) % 256);

		return str;
	}

	public static byte[] int2byteLittleEndian(int num)
	{
		byte[] str = new byte[4];

		str[0] = (byte) (num % 256);
		str[1] = (byte) ((num >> 8) % 256);
		str[2] = (byte) ((num >> 16) % 256);
		str[3] = (byte) ((num >> 24) % 256);

		return str;
	}

	public static byte[] long2byteLittleEndian(long num)
	{
		byte[] str = new byte[8];

		str[0] = (byte) (num % 256);
		str[1] = (byte) ((num >> 8) % 256);
		str[2] = (byte) ((num >> 16) % 256);
		str[3] = (byte) ((num >> 24) % 256);
		str[4] = (byte) ((num >> 32) % 256);
		str[5] = (byte) ((num >> 40) % 256);
		str[6] = (byte) ((num >> 48) % 256);
		str[7] = (byte) ((num >> 56) % 256);
		return str;
	}

	public static long byte2longLittleEndian(byte[] str, int index, int len)
	{
		long num = 0;
		int j = 0;

		if (str == null || (str.length < len && (len - index <= 8)))
		{
			return -1;
		}
		if (len - index > 8)
		{
			return -1;
		}
		for (int i = index; i < len; i++)
		{
			num |= (long) (str[i] & 0xFF) << (j++ * 8);
		}

		return num;
	}

	public static byte[] short2byte(short num)
	{
		byte[] str = new byte[2];

		str[1] = (byte) (num % 256);
		str[0] = (byte) ((num >> 8) % 256);
		return str;
	}

	public static byte[] short2byteLittleEndian(short num)
	{
		byte[] str = new byte[2];

		str[0] = (byte) (num % 256);
		str[1] = (byte) ((num >> 8) % 256);
		return str;
	}

	public static int byte2int(byte[] str)
	{
		if (str == null || str.length > 4)
			return -1;

		int num = 0;

		for (int i = str.length - 1; i >= 0; i--)
		{
			int tmp = (str.length - 1 - i) * 8;
			num += (str[i] << tmp) & (0xFF << tmp);
		}

		return num;
	}

	public static short byte2shortLittleEndian(byte[] str)
	{
		if (str == null || str.length != 2)
			return -1;

		short num = 0;

		num += str[0] & 0xFF;
		num += (str[1] << 8) & 0xFF00;

		return num;
	}

	public static short byte2short(byte[] str)
	{
		if (str == null || str.length != 2)
			return -1;

		short num = 0;

		num += str[1] & 0xFF;
		num += (str[0] << 8) & 0xFF00;

		return num;
	}

	public static int byte2int(byte[] str, int index, int len)
	{
		int num = 0;
		int j = 0;

		if (str == null || (str.length < len && (len - index <= 4)))
		{
			return -1;
		}
		if (len - index > 4)
		{
			return -1;
		}
		for (int i = len - 1; i >= index; i--)
		{
			num += (str[i] & 0xFF) << (j++ * 8);
		}

		return num;
	}

	public static int byte2intLittleEndian(byte[] str, int index, int len)
	{
		int num = 0;
		int j = 0;

		if (str == null || (str.length < len && (len - index <= 4)))
		{
			return -1;
		}
		if (len - index > 4)
		{
			return -1;
		}
		for (int i = index; i < len; i++)
		{
			num += (str[i] & 0xFF) << (j++ * 8);
		}

		return num;
	}
	
	public static String getTmpPath()
	{
		String path = "D:\\work\\program\\smartHome\\tmp\\";
		String system = ToolFunc.getOperateSystemName();

		if (system.compareTo("windows") != 0)
		{
			path = "/tmp/";
		}
		return path;
	}

	public static String getDataBasePath()
	{
		String path = "D:\\work\\program\\smartHome\\database\\";
		String system = ToolFunc.getOperateSystemName();

		if (system.compareTo("windows") != 0)
		{
			path = "/mnt/data/osgi/database/";
		}
		return path;
	}

	public static String getLogPath()
	{
		String path = "D:\\work\\program\\smartHome\\logs\\";
		String system = ToolFunc.getOperateSystemName();

		if (system.compareTo("windows") != 0)
		{
			path = "/tmp/logs/";
		}
		return path;
	}

	public static String getOperateSystemName()
	{
		Properties props = System.getProperties();
		String osName = props.getProperty("os.name");
		String os;

		if (osName.indexOf(new String("Windows")) != -1)
		{
			os = "windows";
		} else
		{
			os = "linux";
		}
		return os;
	}

	public static Boolean isLinuxSystem()
	{
		return (getOperateSystemName().compareTo("linux") == 0);
	}

	public static String hex2String(byte[] data)
	{
		if (data == null)
		{
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < data.length; i++)
		{
			sb.append(String.format("%02X", data[i]));
		}

		return sb.toString();
	}

	public static byte[] string2hex(byte[] str)
	{
		if (str == null)
		{
			return null;
		}
		if (str.length % 2 != 0)
		{
			return null;
		}
		byte[] data = new byte[str.length / 2];
		int index = 0;

		for (int i = 0; i < str.length; i += 2)
		{
			if (str[i] >= '0' && str[i] <= '9')
			{
				data[index] = (byte) (str[i] - '0');
			} else if (str[i] >= 'A' && str[i] <= 'F')
			{
				data[index] = (byte) (str[i] - 'A' + 10);
			} else if (str[i] >= 'a' && str[i] <= 'f')
			{
				data[index] = (byte) (str[i] - 'a' + 10);
			} else
			{
				System.err.println("str2hex error!!!");
				return null;
			}
			data[index] = (byte) (data[index] << 4);
			if (str[i + 1] >= '0' && str[i + 1] <= '9')
			{
				data[index] += (byte) (str[i + 1] - '0');
			} else if (str[i + 1] >= 'A' && str[i + 1] <= 'F')
			{
				data[index] += (byte) (str[i + 1] - 'A' + 10);
			} else if (str[i + 1] >= 'a' && str[i + 1] <= 'f')
			{
				data[index] += (byte) (str[i + 1] - 'a' + 10);
			} else
			{
				System.err.println("str2hex error!!!");
				return null;
			}
			index++;
		}

		return data;

	}

	public static int random_int()
	{
		SecureRandom random = new SecureRandom();
		return random.nextInt(0x12345678);
	}

	public static void random(byte[] value)
	{
		SecureRandom random = new SecureRandom();
		random.nextBytes(value);
	}


	public static InetAddress getLocalHost()
	{
		Enumeration<NetworkInterface> netInterfaces = null;
		InetAddress addr = null;

		try
		{
			if (ToolFunc.getOperateSystemName().equals("windows") == true)
			{
				// addr = InetAddress.getByName("192.168.1.49");
				addr = InetAddress.getByName("129.1.9.123");
				return addr;
			}

			InetAddress baiduAddr = InetAddress.getByName("61.135.169.105");
			netInterfaces = NetworkInterface.getNetworkInterfaces();
			Enumeration<InetAddress> ips = null;
			while (netInterfaces.hasMoreElements())
			{
				NetworkInterface ni = netInterfaces.nextElement();
				ips = ni.getInetAddresses();

				while (ips.hasMoreElements())
				{
					addr = ips.nextElement();
					int timeOut = 3000;

					if (baiduAddr.isReachable(ni, 0, timeOut) == true)
					{
						return addr;
					}
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<InetAddress> getLocalHosts()
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

	// for example "abcNULLNULL" -> "abc"
	public static byte[] delInvalidByte(byte[] src, byte[] c)
	{
		String dest;
		String tmp = new String(src);
		String[] subArray = tmp.split(new String(c));
		dest = subArray[0];
		return dest.getBytes();
	}

	public static byte calculateCS(byte[] data) throws Exception
	{
		byte cs = 0;
		if (data == null)
		{
			throw new Exception("data is null");
		}

		for (int i = 0; i < data.length; i++)
		{
			cs += data[i];
		}
		return cs;
	}

	public static boolean checkIP(String ip)
	{
		if (ip == null)
		{
			return false;
		}
		String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
				+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
				+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
				+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
		if (ip.matches(regex))
		{
			return true;
		}
		return false;
	}

	public static boolean checkDomainName(String domain)
	{
		if (domain == null)
		{
			return false;
		}
		String regex = "((\\w|-)+\\.)+\\w+";
		if (domain.matches(regex))
		{
			return true;
		}
		return false;
	}

	public static int byteSearch(byte[] data, int start, int end, byte value)
	{
		if (data == null)
		{
			return -1;
		}
		if (start > end)
		{
			return -1;
		}
		if (data.length < end)
		{
			return -1;
		}
		for (int i = start; i < end; i++)
		{
			if (data[i] == value)
			{
				return i;
			}
		}

		return -1;
	}

	public static boolean isFileExist(String filename)
	{
		if (filename == null)
		{
			return false;
		}
		File file = new File(filename);
		if (file.exists())
		{
			return true;
		}
		return false;
	}

	public static void deleteFile(String filename)
	{
		if (filename == null)
		{
			return;
		}
		File file = new File(filename);
		if (file.exists())
		{
			try
			{
				file.delete();
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public static boolean copyFile(String original, String target)
	{
		if (original == null || target == null)
		{
			return false;
		}
		try
		{
			File originalFile = new File(original);
			File targetFile = new File(target);
			if (originalFile.exists() == false)
			{
				return false;
			}
			if (targetFile.exists() == true)
			{
				targetFile.delete();
			}
			FileInputStream in = new FileInputStream(originalFile);
			FileOutputStream out = new FileOutputStream(targetFile);
			byte[] buff = new byte[4096];
			int n = 0;
			while ((n = in.read(buff)) != -1)
			{
				out.write(buff, 0, n);
			}
			out.flush();
			in.close();
			out.close();
			if (isLinuxSystem() == true)
			{
				Runtime.getRuntime().exec("sync");
			}
			return true;
		} catch (Exception e)
		{
			return false;
		}
	}
	
	public static void deleteFileBySuffixName(String path, String...args)
	{
		File file = new File(path);
		File[] filelist = file.listFiles();
		for (int i = 0; i < filelist.length; i++)
		{
			File temp = filelist[i];
			for(String arg : args)
			{
				if (temp.getName().endsWith("."+arg))
				{
					temp.delete();
				}
			}
		}
	}
	
	public static void deleteAllFile(String path)
	{
		File file = new File(path);
		File[] filelist = file.listFiles();
		for (int i = 0; i < filelist.length; i++)
		{
			File temp = filelist[i];
			temp.delete();
		}
	}

}
