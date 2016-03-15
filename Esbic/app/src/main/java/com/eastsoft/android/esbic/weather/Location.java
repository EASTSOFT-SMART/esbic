package com.eastsoft.android.esbic.weather;

public class Location
{
	public final String address;
	public final Content content;

	public Location(String address, Content content)
	{
		this.address = address;
		this.content = content;
	}

	public static class Point
	{
		public final double x;
		public final double y;

		public Point(double x, double y)
		{
			this.x = x;
			this.y = y;
		}
	};

	public static class AddressDetail
	{
		public final String city;
		public final int city_code;
		public final String district;
		public final String province;
		public final String street;
		public final String street_number;
		public final Point point;
		
		public AddressDetail(String city, int city_code, String district, 
				String province, String street, String street_number, Point point)
		{
			this.city = city;
			this.city_code = city_code;
			this.district = district;
			this.province = province;
			this.street = street;
			this.street_number = street_number;
			this.point = point;
		}
	};

	public static class Content
	{
		public final String address;
		public final AddressDetail address_detail;
		public final int status;
		public Content(String address, AddressDetail address_detail, int status)
		{
			this.address = address;
			this.address_detail = address_detail;
			this.status = status;
		}
	}
}
