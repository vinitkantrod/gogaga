package com.example.gogaga;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<Post> postList;
    private Context context;

    public PostAdapter(Context c, List<Post> p) {
        this.context = c;
        this.postList = p;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post p = postList.get(position);
        holder.titleTxtview.setText(p.getTitle());
        holder.bodyTxtview.setText(p.getBody());
        holder.idTextview.setText(String.valueOf(p.getId()));
    }


    @Override
    public int getItemCount() {
        if (postList != null) {
            return postList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleTxtview;
        TextView bodyTxtview;
        TextView idTextview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxtview = itemView.findViewById(R.id.title_txtview);
            bodyTxtview = itemView.findViewById(R.id.body_txtview);
            idTextview = itemView.findViewById(R.id.user_id_text_view);
        }
    }

    public void setData(List<Post> pList) {
        postList = pList;
        notifyDataSetChanged();
    }
}
