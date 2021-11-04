package com.fongmi.android.launcher;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.view.View;

import com.fongmi.android.launcher.bean.AppInfo;

import java.util.ArrayList;
import java.util.List;

public class Utils {

	public static void hideSystemUI(Activity activity) {
		int flags = View.SYSTEM_UI_FLAG_LOW_PROFILE | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
		activity.getWindow().getDecorView().setSystemUiVisibility(flags);
	}

	@SuppressLint("QueryPermissionsNeeded")
	public static List<AppInfo> getApps() {
		List<AppInfo> items = new ArrayList<>();
		for (ApplicationInfo info : App.get().getPackageManager().getInstalledApplications(0)) {
			if (info.packageName.equals(App.get().getPackageName())) continue;
			if ((info.flags & ApplicationInfo.FLAG_SYSTEM) != 0) continue;
			items.add(AppInfo.get(info));
		}
		return items;
	}
}
