package com.samhan.foodtogov10.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.samhan.foodtogov10.Adapters.CommentAdapter;
import com.samhan.foodtogov10.Models.Comment;
import com.samhan.foodtogov10.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class PostDetails_activity extends AppCompatActivity {

   ImageView imgPost,imgUserPost ,imgCurrentUser;
   TextView txtPostDesc,txtPostDateName , txtPostTitle;
   EditText editTextComment;
   Button btnAddComment;

   String postKey;

   FirebaseAuth firebaseAuth;
   FirebaseUser currentUser;
   FirebaseDatabase firebaseDatabase;

   RecyclerView RvComment;
   CommentAdapter commentAdapter;
    List<Comment> listComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);


        RvComment=findViewById(R.id.rv_comment);

        imgPost=findViewById(R.id.post_detail_img);
        imgUserPost=findViewById(R.id.post_detail_user_img);
        imgCurrentUser=findViewById(R.id.post_detail_currentuser_img);

        txtPostTitle=findViewById(R.id.post_detail_title);
        txtPostDesc=findViewById(R.id.post_detail_desc);
        txtPostDateName=findViewById(R.id.post_detail_date_name);

        editTextComment=findViewById(R.id.post_detail_comment);
        btnAddComment=findViewById(R.id.post_detail_add_comment_btn);

        firebaseAuth=FirebaseAuth.getInstance();
        currentUser=firebaseAuth.getCurrentUser();
        firebaseDatabase=FirebaseDatabase.getInstance();

        btnAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference commentReference=firebaseDatabase.getReference("Comment").child(postKey).push();
                String comment_content=editTextComment.getText().toString();
                String uid=currentUser.getUid();
                String uname= currentUser.getDisplayName();
                String uimg=currentUser.getPhotoUrl().toString();

                Comment comment=new Comment(comment_content,uid,uimg,uname);

                commentReference.setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(PostDetails_activity.this," comment added", Toast.LENGTH_SHORT).show();
                        editTextComment.setText("");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PostDetails_activity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });


        String postImage=getIntent().getExtras().getString("postImage");
        Glide.with(this).load(postImage).into(imgPost);

        String postTitle=getIntent().getExtras().getString("title");
        txtPostTitle.setText(postTitle);

        String userImage=getIntent().getExtras().getString("userPhoto");
        Glide.with(this).load(userImage).into(imgUserPost);

        String postDescription=getIntent().getExtras().getString("description");
        txtPostDesc.setText(postDescription);

        Glide.with(this).load(currentUser.getPhotoUrl()).into(imgCurrentUser);

        postKey=getIntent().getExtras().getString("postKey");

        String date=timestampToString(getIntent().getExtras().getLong("postDate"));
        txtPostDateName.setText(date);
        
        intRvComment();

    }

    private void intRvComment() {

        RvComment.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference commentRef=firebaseDatabase.getReference("Comment").child(postKey);
        commentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listComment=new ArrayList<>();

                for(DataSnapshot snap: dataSnapshot.getChildren()){
                    Comment comment=snap.getValue(Comment.class);
                    listComment.add(comment);
                }

                Collections.reverse(listComment);

                commentAdapter=new CommentAdapter(getApplicationContext(),listComment);
                RvComment.setAdapter(commentAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }

    private String timestampToString(long time) {

        Calendar calendar=Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        String date= DateFormat.format("dd-MM-yyyy",calendar).toString();
        return date;
    }
}