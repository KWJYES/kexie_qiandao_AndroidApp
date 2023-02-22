package com.example.kexieqiandao.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kexieqiandao.R;
import com.example.kexieqiandao.callback.ComplaintListener;
import com.example.kexieqiandao.databinding.ItemRvOnlineUserBinding;
import com.example.kexieqiandao.entity.ResponseBean;

import java.util.ArrayList;
import java.util.List;

public class OnlineUserListAdapter extends RecyclerView.Adapter<OnlineUserListAdapter.ViewHolder> {

    private List<ResponseBean.DataDTO> itemList;
    private ComplaintListener complaintListener;

    public void setComplaintListener(ComplaintListener complaintListener) {
        this.complaintListener = complaintListener;
    }

    public OnlineUserListAdapter(List<ResponseBean.DataDTO> itemList) {
        this.itemList = new ArrayList<>(itemList);
    }

    @NonNull
    @Override
    public OnlineUserListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRvOnlineUserBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_rv_online_user,
                parent,
                false);
        ViewHolder holder = new ViewHolder(binding);
        holder.binding.setClick(new Click(holder));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull OnlineUserListAdapter.ViewHolder holder, int position) {
        holder.binding.setItem(itemList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ItemRvOnlineUserBinding binding;
        public ViewHolder(@NonNull ItemRvOnlineUserBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }

    public class Click{
        private ViewHolder holder;

        public Click(ViewHolder holder) {
            this.holder = holder;
        }

        public void complaint(View view){
            if (complaintListener!=null){
                int position = holder.getAdapterPosition();
                if (position == -1) return;
                complaintListener.onComplaint(itemList.get(position));
            }
        }
    }
}
