package cn.edu.qdu.weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class Weather {
	private List<String> provinceMessage=new ArrayList<String>();
	private static String allMessage="";
	
	public static void main(String[] args) {
		Weather we=new Weather();
		System.out.println(we.post());
		we.provinceMessage=we.getProvince(allMessage);
		for (String string : we.provinceMessage) {
			System.out.println(string);
		}
	}
	
	//HTTP post���󲢻�ȡ������Ϣ
	public String post(){
		OutputStreamWriter out=null;
//		String allMessage="";
		try {
			//ͨ��URL�ϴ�����openConnection()�����������Ӷ���
			URL urlTemp=new URL("http://www.webxml.com.cn/WebServices/WeatherWS.asmx/getSupportCityString");
			//����URL��ȡURLConnection����
			URLConnection connection=urlTemp.openConnection();
			//����Э��ΪHTTPЭ��
			HttpURLConnection httpConnection =(HttpURLConnection)connection;
			httpConnection.setDoOutput(true);//�Ƿ�д������
			httpConnection.setDoInput(true);//�Ƿ��������
			httpConnection.connect();
			out=new OutputStreamWriter(httpConnection.getOutputStream(),"UTF-8");
			out.write("theRegionCode=����");
			out.flush();
			InputStream in=httpConnection.getInputStream();
			String str="";
			BufferedReader read=new BufferedReader(new InputStreamReader(in,"UTF-8"));
			while((str=read.readLine())!=null){
				allMessage+=str;
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return allMessage;
	}
	
	public List<String> getProvince(String allMessage) {
		int beingIndex=allMessage.indexOf("<string>")+8;
		int endIndex=allMessage.lastIndexOf("</string>");
		String str=allMessage.substring(beingIndex, endIndex);
		String[] arrStr=str.split("</string>  <string>");
		for (String string : arrStr) {			
			provinceMessage.add(string);
		}
		return provinceMessage;
	}
}
