package org.neric.tb;

public class Payment
{
	public String district;
	public String date;
	public String type;
	public String line;
	public String originalLine;
	
	public Payment()
	{
		super();
	}

	public String getDistrict()
	{
		return district;
	}

	public void setDistrict(String district)
	{
		this.district = district;
	}
	
	public String getDate()
	{
		return date;
	}
	public void setDate(String date)
	{
		this.date = date;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public String getLine()
	{
		return line;
	}
	public void setLine(String line)
	{
		this.line = line;
	}

	public String getOriginalLine()
	{
		return originalLine;
	}

	public void setOriginalLine(String originalLine)
	{
		this.originalLine = originalLine;
	}
	
	
}
