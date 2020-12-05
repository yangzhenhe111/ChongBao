package com.example.pet.chat;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pet.R;

import java.util.List;

/**
 * Created by dell on 2018/6/3.
 * Created by qiyueqing on 2018/6/3.
 */
public class BookAdapter extends RecyclerView.Adapter<com.example.pet.chat.BookAdapter.ViewHolder>{
    private List<Book> mBookList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView bookImage;
        TextView bookName;
        public ViewHolder(View view){
            super(view);
            bookImage=view.findViewById(R.id.booking_reimg);
            bookName=view.findViewById(R.id.booking_retext);
        }
    }
    public BookAdapter(List<Book> bookList){
        mBookList=bookList;
    }
    @Override
    public com.example.pet.chat.BookAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_recycler,parent,false);
        RecyclerView.ViewHolder holder=new ViewHolder(view);
        return (ViewHolder) holder;
    }
    @Override
    public void onBindViewHolder(com.example.pet.chat.BookAdapter.ViewHolder holder, int position) {
        Book book=mBookList.get(position);
        holder.bookImage.setImageResource(book.getImageId());
        holder.bookName.setText(book.getName());
    }
    @Override
    public int getItemCount() {
        return mBookList.size();
    }
}
