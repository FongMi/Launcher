package com.fongmi.android.launcher;

import android.app.Application;

public class App extends Application {

	private static App instance;

	public static App get() {
		return App.instance;
	}

	public App() {
		instance = this;
	}
}
