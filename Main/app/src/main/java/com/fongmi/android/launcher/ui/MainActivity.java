package com.fongmi.android.launcher.ui;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.fongmi.android.launcher.Utils;
import com.fongmi.android.launcher.adapter.Adapter;
import com.fongmi.android.launcher.bean.AppInfo;
import com.fongmi.android.launcher.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

	private ActivityMainBinding binding;
	private Adapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityMainBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());
		Utils.hideSystemUI(this);
		initView();
		initEvent();
	}

	private void initView() {
		setRecyclerView();
		mAdapter.getApp();
	}

	private void initEvent() {
		mAdapter.setOnItemClickListener(this::openApp);
	}

	private void setRecyclerView() {
		binding.recycler.setHasFixedSize(true);
		binding.recycler.setAdapter(mAdapter = new Adapter());
	}

	private void openApp(AppInfo item) {
		try {
			startActivity(getPackageManager().getLaunchIntentForPackage(item.getPack()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onConfigurationChanged(@NonNull Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		Utils.hideSystemUI(this);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus) Utils.hideSystemUI(this);
	}
}
