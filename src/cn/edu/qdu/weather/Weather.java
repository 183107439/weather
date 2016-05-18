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
	
	//HTTP post请求并获取Province可支持的城市
	public String getCitys(){
		OutputStreamWriter out=null;
//		String allMessage="";
		try {
			//通过URL上代调用openConnection()方法创建连接对象
			URL urlTemp=new URL("http://www.webxml.com.cn/WebServices/WeatherWS.asmx/getSupportCityString");
			//根据URL获取URLConnection对象
			URLConnection connection=urlTemp.openConnection();
			//请求协议为HTTP协议
			HttpURLConnection httpConnection =(HttpURLConnection)connection;
			httpConnection.setDoOutput(true);//是否写入链接
			httpConnection.setDoInput(true);//是否读入链接
			httpConnection.connect();
			out=new OutputStreamWriter(httpConnection.getOutputStream(),"UTF-8");
			out.write("theRegionCode=海南");
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
	
	//解析得到的数据
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
	
	//dom4j 解析XML文档--转换成字符串
//	public void getWeatherInformation(){
//		// 创建saxreader对象
//				SAXReader reader = new SAXReader();
//				// 读取一个文件，把这个文件转换成Document对象
//				Document document=null;
//				try {
//					document = reader.read(new File("H://JAVA//weather//a.xml"));
//				} catch (DocumentException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				// 获取根元素
//				Element root = document.getRootElement();
//				// 把文档转换字符串
//				String docXmlText = document.asXML();
//				System.out.println(docXmlText);
//				System.out.println("---------------------------");
//	}
}
