package com.bitc.testapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bitc.testapp.holder.commentviewholder;
import com.bitc.testapp.model.commentmodel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

public class comment extends AppCompatActivity {
    EditText commenttext;
    Button commentsubmit;
    DatabaseReference userref, commentref;
    String postkey;
    RecyclerView recview;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_detail);

        postkey = getIntent().getStringExtra("postkey");
        userref = FirebaseDatabase.getInstance().getReference().child("userprofile");
        commentref = FirebaseDatabase.getInstance().getReference().child(postkey).child("comments");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String userId= user.getUid();

        commenttext =(EditText) findViewById(R.id.comment_text);
        commentsubmit = (Button) findViewById(R.id.comment_submit);

        recview =(RecyclerView)findViewById(R.id.comment_recview);
        recview.setLayoutManager(new LinearLayoutManager(this));

            commentsubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    userref.child(userId).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                String username=snapshot.child("uname").getValue().toString();
                                String uimage=snapshot.child("uname").getValue().toString();
                            }
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

                private void processcomment(String username, String uimage){

                    String commentpost = commenttext.getText().toString();
                    String randompostkey=userId+""+new Random().nextInt(1000);

                    Calendar datevalue = Calendar.getInstance();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm--yy");
                    String cdate = dateFormat.format(datevalue.getTime());

                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                    String ctime = timeFormat.format(datevalue.getTime());

                    HashMap cmnt = new HashMap();
                    cmnt.put("uid",userId);
                    cmnt.put("username", username);
                    cmnt.put("userimage",uimage);
                    cmnt.put("usermsg",commentpost);
                    cmnt.put("date",cdate);
                    cmnt.put("time",ctime);

                    commentref.child(randompostkey).updateChildren(cmnt)
                            .addOnCompleteListener(new OnCompleteListener(){
                        @Override
                        public void onComplete(@NonNull Task task){
                            if(task.isSuccessful())
                                Toast.makeText(getApplicationContext(),"Comment Added",Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(getApplicationContext(),task.toString(),Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener(){

                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });

                }


            });
    }

    @Override
    protected  void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<commentmodel> options =
                new FirebaseRecyclerOptions.Builder<commentmodel>().setQuery(commentref,commentmodel.class).build();

        FirebaseRecyclerAdpter<commentmodel, commentviewholder>firebaseRecyclerAdpter= new FirebaseRecyclerAdpter<commentmodel,commentviewholder>(options);

                @Override
                protected void onBindViewHolder(@NonNull commentviewholder holder, int position, @NonNull commentmodel model, @NonNull commentviewholder holder){
                    holder.cuname.setText(model.getUsername());
                    holder.cumessage.setText(model.getUsermsg());
                    holder.cudt.setText("Date :"+model.getDate()+"Time :"+model.getTime());
                    Gilde.with(holder.cuimage.getContext()).load(model.getUserimage()).into(holder.cuimage);

        }

        @NonNull
        @Override
        public commentviewholder onCreateViewHolder(@NonNull viewGroup parent, int viewType){
           View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_single_row,parent,false);
           return new commentviewholder(view);
        }
    };

    firebaseRecyclerAdapter.startListening();
    recview.setAdapter(firebaseRecyclerAdapter);

}
