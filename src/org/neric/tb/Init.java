package org.neric.tb;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.neric.tb.util.ConfigUtil;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

public class Init
{
	private static ArrayList<String> types = new ArrayList<String>();		
	
	public static void main(String[] args) throws IOException
	{
		setTypes();
		
		File folder = new File(ConfigUtil.getInstance().getInputFolder());
		
		for(File file : folder.listFiles())
		{
			if(file.getName().contains(ConfigUtil.getInstance().getFileNameLike()))
			{
				ArrayList<Payment> payments = readFile(file);
				createFile(payments);
				moveOriginalFile(file);			
			}		
		}	
	}
	
	private static void setTypes()
	{
		List<String> list;
		try
		{
			list = Lists.newArrayList(Splitter.on(',').trimResults().split(ConfigUtil.getInstance().getPaymentTypes()));
			for(String s : list)
			{
				types.add(s);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static ArrayList<Payment> readFile(File file)
	{
		ArrayList<Payment> payments = new ArrayList<Payment>();
		try
		{
		    BufferedReader reader = new BufferedReader(new FileReader(file));
		    String line;
		    
		    while ((line = reader.readLine()) != null)
		    {
		    	Payment payment = new Payment();
		    		
		    	payment.setDistrict(file.getName().substring(0,4)); //First 4 characters of the file name.
		    	payment.setOriginalLine(line); //Entire line.
		    	payment.setLine(line.substring(0, 87)); //First 88 characters, which will exclude the date and type.
		    	payment.setType(Character.toString(line.charAt(line.length()-1))); //Last character in file. (minus one for -1 error)
		    	payment.setDate(line.substring((line.length()-8)-1,line.length()-1)); //8 characters @ the end of the line, minus 1 character for the type.
		    	
		    	payments.add(payment);
		    }
		    reader.close();		   
		}
		catch (Exception e)
		{
		    e.printStackTrace();
		}
		return payments;
	}
	
	public static void createFile(ArrayList<Payment> payments) throws IOException
	{
		String district = null;
		ArrayList<String> dates = new ArrayList<String>();

		for(Payment p : payments)
		{
			district = p.getDistrict();
			dates.add(p.getDate());
		}

		String processedDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
		
		for(String date : dates)
		{
			for(String type : types)
			{
				StringBuilder builder = new StringBuilder();
				File file = new File(ConfigUtil.getInstance().getOutputFolder() + district + "_Pay_" + date + "_" + type + "_" + processedDate + ".txt");
				FileWriter writer = new FileWriter(file, false); // true to append		
				
				for(Payment payment : payments)
				{
					if(payment.date.equalsIgnoreCase(date) && payment.type.equalsIgnoreCase(type))
					{				
						builder.append(payment.getLine());
						builder.append(System.getProperty("line.separator"));
					}
				}
				writer.write(builder.toString());
				writer.close();
			}		
		}
	}	

	private static void moveOriginalFile(File file) throws IOException
	{
		Date processedDate = new Date();
		String newFileName = file.getName().replace(ConfigUtil.getInstance().getFileExtension(), "_") + new SimpleDateFormat("yyyyMMdd").format(processedDate) + ConfigUtil.getInstance().getFileExtension();		
		file.renameTo(new File(ConfigUtil.getInstance().getInputFolderMove() + newFileName));
		deleteFile(file);	
	}

	private static void deleteFile(File file)
	{
		try
		{
			file.delete();
		}
		catch(Exception e)
		{	
    		e.printStackTrace();    		
    	}
	}
}