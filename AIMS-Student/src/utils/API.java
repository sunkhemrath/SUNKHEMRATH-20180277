package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Logger;

import entity.payment.CreditCard;
import entity.payment.PaymentTransaction;

/**
 * Represent on api call
 * Date: 11/12/2021
 * @author SUN KHEMRATH 
 * @version 1.0
 */

public class API {
	/**
	 * date formatter formart all date to yy/mm/dd HH:mm:ss sunkhemrath
	 */
	public static DateFormat DATE_FORMATER = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	/**
	 * Thuoc tinh giup log ra thong tin ra console
	 */
	private static Logger LOGGER = Utils.getLogger(Utils.class.getName());
	
	/**
	 * Setup Connection
	 * Thiet lap connection toi server
	 * @param url : duong dan toi server can request
	 * @param method : giao thuc api
	 * @param token : doan ma ban can cung cap de xac thuc nguoi dung
	 * @return connection
	 * @throws Exception
	 */
	private static HttpURLConnection setupConnection(String url, String method, String token) throws IOException{
		//phan 1 : setup
		LOGGER.info("Request URL: " + url + "\n");
		URL line_api_url = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) line_api_url.openConnection();
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestMethod(method);
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Authorization", "Bearer " + token);
		return conn;
	}
	
	/**sunkhemrath
	 * Read Respone
	 * Phuong thuc doc du lieu tra ve tu server
	 * @param conn : connection to server
	 * @return respone : phan hoi tra ve tu server 
	 * @throws IOException
	 */
	private static String readRespone(HttpURLConnection conn) throws IOException{
		//phan 2 : doc du lieu tra ve tu server
		BufferedReader in;
		String inputLine;
		if(conn.getResponseCode() / 100 == 2) {
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		}else {
			in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder respone = new StringBuilder(); // su dung String cho viec toi uu ve mat bo cho
		while ((inputLine = in.readLine()) != null)
			System.out.println(inputLine);
		respone.append(inputLine + "\n");
		in.close();
		LOGGER.info("Respone Info: " + respone.substring(0, respone.length() - 1).toString());
		return respone.substring(0, respone.length() - 1).toString();
	}
	
	/**
	 * Get method
	 * Phuong thuc giup goi cac api dang GET (lay so du tai khoan,...)
	 * @param url : duong dan toi server ca request
	 * @param token : doan ma ban can cung cap de xac thuc nguoi dung
	 * @return respone : phan hoi tu server (dang string)
	 * @throws Exception
	 */
	public static String get(String url, String token) throws Exception {
		
		//phan 1 : setup		
		HttpURLConnection conn = setupConnection(url, "GET", token);
				
		//phan 2 : doc du lieu tra ve tu server
		String respone = readRespone(conn);
		return respone;
	}

	
	int var;
	/**
	 * Post method
	 * Phuong thuc giup goi cac api dang POST (thanh toan....)
	 * @param url : duong dan toi server can request
	 * @param token : doan ma bam can cung cap de xac thuc nguoi dung
	 * @param data : du lieu dua len server de xu ly (dang JSON)
	 * @return respone : phan hoi tu server (dang string)
	 * @throws IOException
	 */
	public static String post(String url, String data) throws IOException {
		//cho phep PATCH protocol
		allowMethods("PATCH");
		
		//phan 1 : setup
		HttpURLConnection conn = setupConnection(url, "GET", data);
		
		//phan 2 : gui du lieu
		Writer writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
		writer.write(data);
		writer.close();
		
		//phan 3 : doc du lieu gui ve tu server
		String respone = readRespone(conn);
		return respone;
		
	}
	/**
	 * allowMethods
	 * Phuong thuc cho phep goi cac loai giao thuc API khac nhau nhu PATCH, PUT,...(chi hoat dong voi Java 11)
	 * @deprecated chi hoat dong voi Java <= 11
	 * @param methods : giao thuc can cho phep (PATCH, PUT,....)
	 */

	private static void allowMethods(String... methods) {
		try {
			Field methodsField = HttpURLConnection.class.getDeclaredField("methods");
			methodsField.setAccessible(true);

			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(methodsField, methodsField.getModifiers() & ~Modifier.FINAL);

			String[] oldMethods = (String[]) methodsField.get(null);
			Set<String> methodsSet = new LinkedHashSet<>(Arrays.asList(oldMethods));
			methodsSet.addAll(Arrays.asList(methods));
			String[] newMethods = methodsSet.toArray(new String[0]);

			methodsField.set(null/* static field */, newMethods);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
	}

}