package com.mentor.firstdemo.util;


import java.lang.reflect.Method;
import java.util.ArrayList;

import android.util.Log;

/**
 * 日志辅助类
 * @author liumeng
 * 2011-5-23下午04:47:58
 */
public class LogUtil {
	

		public static boolean logFlag = true;

		public static void d(String tag,String msg){
			if (logFlag) {
				Log.d(tag, msg);
			}
		}
		public static void e(String tag,String msg){
			if (logFlag) {
				Log.e(tag, msg);
			}
		}
		
		public static void i(String tag,String msg){
			if (logFlag) {
				Log.i(tag, msg);
			}
		}
		public static void w(String tag,String msg){
			if (logFlag) {
				Log.w(tag, msg);
			}
		}
		public static void v(String tag,String msg){
			if (logFlag) {
				Log.v(tag, msg);
			}
		}
		public static void println(String msg){
			if (logFlag) {
				System.out.println(msg);
			}
		}
        public static void i(String msg){  
        	    if(logFlag){
        	    	try{
        	    		//new Throwable() 可以获得运行到某一行代码时涉及到的 类、函数
        	    		for(StackTraceElement st:(new Throwable()).getStackTrace()){
                        	if(classname.equals(st.getClassName()) || methods.contains(st.getMethodName())){
                                continue;
                            }else{
                            	int b=st.getClassName().lastIndexOf(".")+1;
                				Log.i(st.getClassName().substring(b), st.getMethodName()+":"+st.getLineNumber()+"_"+msg);
                				
                            	//System.out.println(st.getFileName()+":"++":"+st.getMethodName()+":"+st.getLineNumber());
                                break;
                            }
                        }
        	    	}catch(Exception e){}
        	    }
                
        }
//
//        public static ArrayList<StackTraceElement>  getStackTraceAll(){
//                ArrayList<StackTraceElement> result = new ArrayList<StackTraceElement>();
//                for(StackTraceElement st:(new Throwable()).getStackTrace()){                                
//                        if(!classname.equals(st.getClassName()) && !methods.contains(st.getMethodName())){
//                                result.add(st);
//                        }
//                }
//                return result;
//        }
        private static String classname;
        private static ArrayList<String> methods;
        static{
                classname = LogUtil.class.getName();
                methods = new ArrayList<String>();
                 
                Method [] ms= LogUtil.class.getDeclaredMethods();
                for(Method m:ms){
                        methods.add(m.getName());
                }                         
        }
}
