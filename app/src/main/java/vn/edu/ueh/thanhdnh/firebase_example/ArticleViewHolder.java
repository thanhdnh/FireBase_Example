package vn.edu.ueh.thanhdnh.firebase_example;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ArticleViewHolder extends RecyclerView.ViewHolder {
  private ImageView ivCover;
  private TextView txtId, txtTitle, txtDescription, txtViews;
  private ArticleViewAdapter adapter;

  public ArticleViewHolder(@NonNull View itemView, ArticleViewAdapter adapter) {
    super(itemView);
    ivCover = itemView.findViewById(R.id.ivCover);
    txtId = itemView.findViewById(R.id.txt_id);
    txtTitle = itemView.findViewById(R.id.txt_title);
    txtDescription = itemView.findViewById(R.id.txt_description);
    txtViews = itemView.findViewById(R.id.txt_views);
    this.adapter = adapter;
  }

  public ImageView getIvCover() {
    return ivCover;
  }

  public TextView getTxtId() {
    return txtId;
  }

  public TextView getTxtTitle() {
    return txtTitle;
  }

  public TextView getTxtDescription() {
    return txtDescription;
  }

  public TextView getTxtViews() {
    return txtViews;
  }
}
