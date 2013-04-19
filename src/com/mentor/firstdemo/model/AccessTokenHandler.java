package com.mentor.firstdemo.model;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.text.TextUtils;

import com.mentor.firstdemo.util.LogUtil;



public class AccessTokenHandler extends DefaultHandler{

		public AccessToken at;
		public String error;
		
		
		public String startElement;
		public String endElement;
		public  AccessTokenHandler() {
			at=new AccessToken();
		}


		// 定义开始解析元素的方法，这里将<xx>中的名称xx提出来，
		public void startElement(String uri, String localName, String name,
				Attributes attributes) throws SAXException {
			startElement=name;
			//LogUtil.i("localName:"+localName+"__name:"+name);
//			for(int i=0;i<attributes.getLength();i++){
//				String attri=attributes.getLocalName(i);
//				if(attri.trim().equals("android:id")){
//					
//				}
//			}
			
	   }
		// 这里是将xml中元素值加入currentValue
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			String a=new String(ch).substring(start, length);
			if("access_token".equals(startElement)){
				at.setAccess_token(a);
			}else if("expires_in".equals(startElement)) {
				at.setExpires_in(a);
			}else if("refresh".equals(startElement)){
				at.setRefresh_token(a);
			}else if("error_code".equals(startElement)){
				error=a;
			}
			//LogUtil.i("characters:"+ch);
		}

		// 在遇到</xx>时，将之间的字符存放在props中间
		public void endElement(String uri, String localName, String name)
				throws SAXException {
			endElement=name;
		}
		
		public AccessToken getAccessToken(){
			if(at.getAccess_token()==null){
				return null;
			}
			return at;
		}
		public String getErrorCode(){
			return error;
		}

}
