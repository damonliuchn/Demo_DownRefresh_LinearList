package com.mentor.firstdemo.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

public class XMLUtil {
	public static void parseXml(String str, DefaultHandler handler){

		// 获取SAX工厂对象
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(false);
		factory.setValidating(false);
		SAXParser parser;
		try {
			// 获取SAX解析器
			parser = factory.newSAXParser();
			// 将解析器和解析规则联系起来，开始解析
			//parser.parse(filename, handler);
			InputStream   is   =   new   ByteArrayInputStream(str.getBytes());
			parser.parse(is, handler);
		}catch(Exception e){
			LogUtil.i(""+e);
		} finally {
			factory = null;
			parser = null;
			handler = null;
		}
	}
	public static void parseXml(InputStream is, DefaultHandler handler){

		// 获取SAX工厂对象
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(false);
		factory.setValidating(false);
		SAXParser parser;
		try {
			// 获取SAX解析器
			parser = factory.newSAXParser();
			// 将解析器和解析规则联系起来，开始解析
			//parser.parse(filename, handler);
			//InputStream   is   =   new   ByteArrayInputStream(str.getBytes());
			parser.parse(is, handler);
		}catch(Exception e){
			LogUtil.i(""+e);
		} finally {
			factory = null;
			parser = null;
			handler = null;
		}
	}
}
