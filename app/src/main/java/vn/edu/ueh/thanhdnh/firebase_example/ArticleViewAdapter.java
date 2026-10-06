package vn.edu.ueh.thanhdnh.firebase_example;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class ArticleViewAdapter extends RecyclerView.Adapter<ArticleViewHolder> {
  private LayoutInflater mInflater;
  private List<Article> articles;

  public ArticleViewAdapter(Context context, List<Article> articles) {
    this.mInflater = LayoutInflater.from(context);
    this.articles = articles;
  }

  public void update(List<Article> articles) {
    this.articles = articles;
  }

  @NonNull
  @Override
  public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View customView = mInflater.inflate(R.layout.contact_list, parent, false);
    return new ArticleViewHolder(customView, this);
  }

  @Override
  public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
    Article currentArticle = articles.get(position);
    if (holder.getTxtId() != null) {
      if (currentArticle.getId() != null && !currentArticle.getId().isEmpty()) {
        holder.getTxtId().setVisibility(View.VISIBLE);
        holder.getTxtId().setText("ID: " + currentArticle.getId());
      } else {
        holder.getTxtId().setVisibility(View.GONE);
      }
    }
    holder.getTxtTitle().setText(currentArticle.getTitle());
    holder.getTxtDescription().setText(currentArticle.getDescription());
    if (holder.getTxtViews() != null) {
      holder.getTxtViews().setText(currentArticle.getViews() + " xem");
    }

    // Tải ảnh trực tiếp từ URL Internet lưu trên Firebase qua Glide
    String img = currentArticle.getImgCover();
    if (img != null && !img.trim().isEmpty()) {
      Glide.with(holder.itemView.getContext())
              .load(img.trim())
              .centerCrop()
              .diskCacheStrategy(DiskCacheStrategy.ALL)
              .placeholder(R.drawable.ic_cover)
              .error(R.drawable.ic_cover)
              .into(holder.getIvCover());
    } else {
      holder.getIvCover().setImageResource(R.drawable.ic_cover);
    }

    // Bấm vào bài viết để mở màn hình chi tiết
    holder.itemView.setOnClickListener(v -> {
      Intent intent = new Intent(v.getContext(), ArticleDetailActivity.class);
      intent.putExtra("article", currentArticle);
      v.getContext().startActivity(intent);
    });
  }

  @Override
  public int getItemCount() {
    return articles.size();
  }
}
