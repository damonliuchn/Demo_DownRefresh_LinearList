package com.mentor.firstdemo.model;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.text.TextUtils;

import com.mentor.firstdemo.util.LogUtil;



public class ReplyHandler extends DefaultHandler{

		public List<Reply> list;
		public Reply reply;
		public String error;
		
		
		public String startElement;
		public String endElement;
		public  ReplyHandler() {
			list=new ArrayList<Reply>();
			startElement="";
		}

		public void startElement(String uri, String localName, String name,
				Attributes attributes) throws SAXException {
			startElement=startElement+">"+name;
			if(">result>replyment".equals(startElement)){
				reply=new Reply();
				//LogUtil.i("新建了一个post");
			}
			//LogUtil.i("startElement:"+startElement+"__name:"+name);

			
	   }
		// 这里是将xml中元素值加入currentValue
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			String a=new String(ch).substring(start, length);
			
			//LogUtil.i(a);
			if(">result>replyment>guid".equals(startElement)) {
				reply.setGuid(a);
				//LogUtil.i(a);
			}else if(">result>replyment>text".equals(startElement)){
				reply.setText(a);
				//LogUtil.i(a);
			
			}else if(">result>replyment>user>id".equals(startElement)){
				reply.setUserid(a);
				//LogUtil.i(a);
			}else if(">result>replyment>user>name".equals(startElement)){
				reply.setUsername(a);
			}else if(">result>replyment>user>avstar".equals(startElement)){
				reply.setUseravstar(a);
				//LogUtil.i(a);
			}else if(">result>error_code".equals(startElement)){
				error=a;
			}
			//LogUtil.i("characters:"+ch);
		}

		// 在遇到</xx>时，将之间的字符存放在props中间
		public void endElement(String uri, String localName, String name)
				throws SAXException {
			endElement=name;
			
			if("replyment".equals(name)){
				//LogUtil.i("添加了一个post");
				list.add(reply);
			}
			
			
			//修正开始标记
			String a[]=startElement.split(">");
			if(a.length>2){
				String b=a[a.length-1];
				if (name.equals(b)){
					StringBuffer sb=new StringBuffer();
					for (int i = 1; i < a.length-1; i++) {
						sb.append(">");
						sb.append(a[i]);
					}
					startElement=sb.toString();
				}
			}
			
		}
		public List<Reply> getResult(){
			return list;
		}

}
