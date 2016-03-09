package org.neric.tb.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import org.neric.tb.Init;

/**
 * @author      Dan Whitehouse <daniel.whitehouse@neric.org>
 * @version     0.8.3
 * @since       2015-09-03
 */

public class ConfigUtil
{
	private static ConfigUtil instance = null;
	private static Properties properties = new Properties();

	public ConfigUtil()
	{
		try
		{
			File jarPath = new File(getJarPath());
			File propertiesPath = jarPath.getParentFile();	
			FileInputStream in = new FileInputStream("/" + propertiesPath + "/config/config.properties");
			
			properties.load(in);
			in.close();
		}
		catch (Exception e)
		{
			System.out.println("No dice... couldn't find file.");
		}
	}
	
	public static String getJarPath()
	{
		String path = Init.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		return path;
	}

	public static ConfigUtil getInstance()
	{
		if(instance == null)
		{
			synchronized(ConfigUtil.class)
			{
				if(instance==null)
				{
					instance = new ConfigUtil();
				}
			}
		}
		return instance;
	}

	public String getInputFolder() throws IOException
	{
		return properties.getProperty("folder.input");		
	}
	public void setInputFolder(String value)
	{
		properties.setProperty("folder.input", value);
	}
	
	public String getInputFolderMove() throws IOException
	{
		return properties.getProperty("folder.input.move");		
	}
	public void setInputFolderMove(String value)
	{
		properties.setProperty("folder.input.move", value);
	}
	
	public String getOutputFolder() throws IOException
	{
		return properties.getProperty("folder.output");		
	}
	public void setOutputFolder(String value)
	{
		properties.setProperty("folder.output", value);
	}
	
	public String getFileNameLike() throws IOException
	{
		return properties.getProperty("file.name.like");		
	}
	public void setFileNameLike(String value)
	{
		properties.setProperty("file.name.like", value);
	}
	
	public String getFileExtension() throws IOException
	{
		return properties.getProperty("file.ext");		
	}
	public void setFileExtension(String value)
	{
		properties.setProperty("file.ext", value);
	}
	
	public String getPaymentTypes() throws IOException
	{
		return properties.getProperty("payment.types");		
	}
	public void setPaymentTypes(String value)
	{
		properties.setProperty("payment.types", value);
	}
}
