package com.mentor.firstdemo;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;


public class MyApplication extends Application {
	private static Set<Activity> allActivity = new HashSet<Activity>();

	public static void addNewActivity(Activity ac) {
		allActivity.add(ac);
	}
	
	public static void delNewActivity(Activity ac) {
		allActivity.remove(ac);
	}

	public static void finished() {
		Iterator<Activity> it = allActivity.iterator();
		while (it.hasNext()) {
			Activity ac = it.next();
			ac.finish();
		}
		allActivity.clear();
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	public static String access_token;
	
	
}
