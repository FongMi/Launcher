package com.fongmi.android.launcher.adapter;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fongmi.android.launcher.Utils;
import com.fongmi.android.launcher.bean.AppInfo;
import com.fongmi.android.launcher.databinding.AdapterItemBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Adapter extends RecyclerView.Adapter<Adapter.Holder> {

	private OnItemClickListener mItemClickListener;
	private final ExecutorService executor;
	private final List<AppInfo> mItems;
	private final Handler handler;

	public Adapter() {
		this.mItems = new ArrayList<>();
		this.executor = Executors.newSingleThreadExecutor();
		this.handler = new Handler(Looper.getMainLooper());
	}

	public interface OnItemClickListener {

		void onItemClick(AppInfo item);
	}

	public void setOnItemClickListener(OnItemClickListener itemClickListener) {
		this.mItemClickListener = itemClickListener;
	}

	class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

		private final AdapterItemBinding binding;

		public Holder(AdapterItemBinding binding) {
			super(binding.getRoot());
			this.binding = binding;
			binding.getRoot().setOnClickListener(this);
		}

		@Override
		public void onClick(View view) {
			mItemClickListener.onItemClick(mItems.get(getLayoutPosition()));
		}
	}

	public void getApp() {
		executor.execute(() -> {
			addAll(Utils.getApps());
			handler.post(this::notifyDataSetChanged);
		});
	}

	private void addAll(List<AppInfo> items) {
		mItems.clear();
		mItems.addAll(items);
		AppInfo.Sorter.sort(mItems);
	}

	@Override
	public int getItemCount() {
		return mItems.size();
	}

	@NonNull
	@Override
	public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new Holder(AdapterItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
	}

	@Override
	public void onBindViewHolder(@NonNull Holder holder, int position) {
		AppInfo item = mItems.get(position);
		holder.binding.name.setText(item.getName());
		holder.binding.icon.setImageDrawable(item.getIcon());
	}
}
