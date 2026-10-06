package vn.edu.ueh.thanhdnh.firebase_example;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShowDataActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private ArticleViewAdapter adapter;
    private List<Article> articles = new ArrayList<>();
    private TextView tvCount;
    private View llEmptyState;
    private ProgressBar progressBar;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_data);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FirebaseApp.initializeApp(this);

        btnBack = findViewById(R.id.btnBack);
        tvCount = findViewById(R.id.tvCount);
        llEmptyState = findViewById(R.id.llEmptyState);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.reclyclerview);

        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        adapter = new ArticleViewAdapter(this, articles);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        db.collection("articles").addSnapshotListener(new EventListener<QuerySnapshot>() {
          @Override
          public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException error) {
            if (progressBar != null) {
              progressBar.setVisibility(View.GONE);
            }

            if (snapshots != null) {
              articles.clear();
              for (QueryDocumentSnapshot q : snapshots) {
                Map<String, Object> data = q.getData();
                String title = data.containsKey("title") && data.get("title") != null ? String.valueOf(data.get("title")) : "";
                String desc = "";
                if (data.containsKey("description") && data.get("description") != null) {
                  desc = String.valueOf(data.get("description"));
                } else if (data.containsKey("content") && data.get("content") != null) {
                  desc = String.valueOf(data.get("content"));
                }
                String imgCover = "";
                if (data.containsKey("imgCover") && data.get("imgCover") != null) {
                  imgCover = String.valueOf(data.get("imgCover"));
                }
                int views = 0;
                if (data.containsKey("views") && data.get("views") != null) {
                  try {
                    views = ((Number) data.get("views")).intValue();
                  } catch (Exception ignored) {
                  }
                }
                String id = q.getId();
                if (data.containsKey("id") && data.get("id") != null && !String.valueOf(data.get("id")).isEmpty()) {
                  id = String.valueOf(data.get("id"));
                }
                Article article = new Article(id, title, desc, imgCover, views);
                articles.add(article);
              }
              adapter.update(articles);
              adapter.notifyDataSetChanged();

              if (tvCount != null) {
                tvCount.setText(articles.size() + " bài viết trong Firestore");
              }
              if (llEmptyState != null) {
                llEmptyState.setVisibility(articles.isEmpty() ? View.VISIBLE : View.GONE);
              }
            }
          }
        });
    }
}
