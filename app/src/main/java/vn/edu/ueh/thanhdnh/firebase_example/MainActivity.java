package vn.edu.ueh.thanhdnh.firebase_example;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
  private FirebaseFirestore db;
  private Button btAdd, btShow;
  private EditText etId, etTitle, etDescription, etImgCover;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_main);
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });

    FirebaseApp.initializeApp(this);
    db = FirebaseFirestore.getInstance();

    btAdd = findViewById(R.id.btAdd);
    btShow = findViewById(R.id.btShow);
    etId = findViewById(R.id.etId);
    etTitle = findViewById(R.id.etTitle);
    etDescription = findViewById(R.id.etDescription);
    etImgCover = findViewById(R.id.etImgCover);

    btAdd.setOnClickListener(this);
    btShow.setOnClickListener(this);
  }

  @Override
  public void onClick(View view) {
    if (view.getId() == R.id.btAdd) {
      String id = etId.getText().toString().trim();
      String title = etTitle.getText().toString().trim();
      String desc = etDescription.getText().toString().trim();
      String img = etImgCover.getText().toString().trim();

      if (id.isEmpty()) {
        etId.setError("Vui lòng nhập ID bài viết");
        etId.requestFocus();
        return;
      }

      if (title.isEmpty()) {
        etTitle.setError("Vui lòng nhập tiêu đề");
        etTitle.requestFocus();
        return;
      }

      if (desc.isEmpty()) {
        etDescription.setError("Vui lòng nhập mô tả / nội dung");
        etDescription.requestFocus();
        return;
      }

      Article article = new Article(id, title, desc, img, 0);
      db.collection("articles").document(id).set(article)
          .addOnSuccessListener(aVoid -> {
            Toast.makeText(this, "Đã thêm bài viết thành công!", Toast.LENGTH_SHORT).show();
            etId.setText("");
            etTitle.setText("");
            etDescription.setText("");
            etImgCover.setText("");
            etId.requestFocus();
          })
          .addOnFailureListener(e -> {
            Toast.makeText(this, "Lỗi thêm bài viết: " + e.getMessage(), Toast.LENGTH_SHORT).show();
          });

    } else if (view.getId() == R.id.btShow) {
      Intent intent = new Intent(getBaseContext(), ShowDataActivity.class);
      startActivity(intent);
    }
  }
}
