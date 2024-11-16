package vn.edu.ueh.thanhdnh.firebase_example;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ShowDataActivity extends AppCompatActivity {
  FirebaseFirestore db;
    RecyclerView recyclerView;
    List<User> users = new ArrayList();

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
        //users.add(new User("default", "000"));

        recyclerView = findViewById(R.id.reclyclerview);
        UserViewAdapter adapter = new UserViewAdapter(getBaseContext(), users);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        /*db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
          @Override
          public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if(task.isSuccessful()){
              users.clear();
              for(QueryDocumentSnapshot q : task.getResult()){
                Map<String, Object> data = q.getData();
                User user = new User((String)data.get("name"), (String)data.get("phone"));
                users.add(user);
              }
              adapter.update(users);
              adapter.notifyDataSetChanged();
            }
          }
        });*/
      db.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
        @Override
        public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException error) {
          if (snapshots != null) {
            users.clear();
            for (QueryDocumentSnapshot q : snapshots) {
              Map<String, Object> data = q.getData();
              User user = new User((String) data.get("name"), (String) data.get("phone"));
              users.add(user);
            }
            adapter.update(users);
            adapter.notifyDataSetChanged();
          }
        }
      });
    }
}
