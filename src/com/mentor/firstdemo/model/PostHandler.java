package com.mentor.firstdemo.model;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.text.TextUtils;

import com.mentor.firstdemo.util.LogUtil;

//private String id;
//private String guid;
//private String text;
//private String reply_count;
//private String userid;
//private String username;
//private String useravstar;

public class PostHandler extends DefaultHandler{

		public List<Post> list;
		public Post post;
		public String error;
		
		
		public String startElement;
		public String endElement;
		public  PostHandler() {
			list=new ArrayList<Post>();
			startElement="";
		}

		public void startElement(String uri, String localName, String name,
				Attributes attributes) throws SAXException {
			startElement=startElement+">"+name;
			if(">result>post".equals(startElement)){
				post=new Post();
				//LogUtil.i("新建了一个post");
			}
			//LogUtil.i("startElement:"+startElement+"__name:"+name);

			
	   }
		// 这里是将xml中元素值加入currentValue
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			String a=new String(ch).substring(start, length);
			
			//LogUtil.i(a);
			if(">result>post>id".equals(startElement)){
				post.setId(a);
				//LogUtil.i(a);
			}else if(">result>post>guid".equals(startElement)) {
				post.setGuid(a);
				//LogUtil.i(a);
			}else if(">result>post>text".equals(startElement)){
				post.setText(a);
				//LogUtil.i(a);
			}else if(">result>post>reply_count".equals(startElement)){
				post.setReply_count(a);
				//LogUtil.i(a);
			}else if(">result>post>user>id".equals(startElement)){
				post.setUserid(a);
				//LogUtil.i(a);
			}else if(">result>post>user>name".equals(startElement)){
				post.setUsername(a);
			}else if(">result>post>user>avstar".equals(startElement)){
				post.setUseravstar(a);
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
			
			if("post".equals(name)){
				//LogUtil.i("添加了一个post");
				list.add(post);
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
		public List<Post> getResult(){
			return list;
		}

}
