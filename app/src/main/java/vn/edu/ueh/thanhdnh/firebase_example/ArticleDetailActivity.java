package vn.edu.ueh.thanhdnh.firebase_example;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;

public class ArticleDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.detailRoot), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        android.view.View btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        Article article = (Article) getIntent().getSerializableExtra("article");

        if (article != null) {
            ImageView ivCover = findViewById(R.id.ivDetailCover);
            TextView tvTitle = findViewById(R.id.tvDetailTitle);
            TextView tvViews = findViewById(R.id.tvDetailViews);
            TextView tvContent = findViewById(R.id.tvDetailContent);
            TextView tvDetailId = findViewById(R.id.tvDetailId);

            int newViews = article.getViews() + 1;
            article.setViews(newViews);

            if (tvDetailId != null && article.getId() != null && !article.getId().isEmpty()) {
                tvDetailId.setText("BÀI VIẾT #" + article.getId());
            }
            tvTitle.setText(article.getTitle());
            tvViews.setText(newViews + " lượt xem");
            tvContent.setText(article.getDescription());

            // Tải ảnh trực tiếp từ URL Internet lưu trên Firebase
            String img = article.getImgCover();
            if (img != null && !img.trim().isEmpty()) {
                Glide.with(this)
                        .load(img.trim())
                        .centerCrop()
                        .placeholder(R.drawable.ic_cover)
                        .error(R.drawable.ic_cover)
                        .into(ivCover);
            } else {
                ivCover.setImageResource(R.drawable.ic_cover);
            }

            // Tự động tăng view trên Firestore nếu có Document ID
            if (article.getId() != null && !article.getId().isEmpty()) {
                FirebaseFirestore.getInstance()
                        .collection("articles")
                        .document(article.getId())
                        .update("views", newViews);
            }
        }
    }
}
