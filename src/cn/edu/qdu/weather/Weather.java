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
	private List<String> allProvinces=new ArrayList<String>();
	private static String allMessage="";
	
	public static void main(String[] args) {
		Weather we=new Weather();
//		System.out.println(we.getCitys());
		we.getCitys();
		we.allProvinces=we.getProvince(allMessage);
		for (String string : we.allProvinces) {
			System.out.println(string);
		}

	}
	
	//HTTP post���󲢻�ȡProvince��֧�ֵĳ���
	public String getCitys(){
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
	
	//�����õ�������
	public List<String> getProvince(String allMessage) {
		int beingIndex=allMessage.indexOf("<string>")+8;
		int endIndex=allMessage.lastIndexOf("</string>");
		String str=allMessage.substring(beingIndex, endIndex);
		String[] arrStr=str.split("</string>  <string>");
		for (int i = 0; i < arrStr.length; i++) {
			int indexTemp=arrStr[i].indexOf(",");
			String strTemp=arrStr[i].substring(0, indexTemp);
			allProvinces.add(strTemp);
		}
//		int i=2;
//		System.out.println(arrStr[i]);
		return allProvinces;
	}
	
	//dom4j ����XML�ĵ�--ת�����ַ���
//	public void getWeatherInformation(){
//		// ����saxreader����
//				SAXReader reader = new SAXReader();
//				// ��ȡһ���ļ���������ļ�ת����Document����
//				Document document=null;
//				try {
//					document = reader.read(new File("H://JAVA//weather//a.xml"));
//				} catch (DocumentException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				// ��ȡ��Ԫ��
//				Element root = document.getRootElement();
//				// ���ĵ�ת���ַ���
//				String docXmlText = document.asXML();
//				System.out.println(docXmlText);
//				System.out.println("---------------------------");
//	}
}
