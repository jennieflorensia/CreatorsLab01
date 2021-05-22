package jennie.umn.ac.testregisfirebase.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import jennie.umn.ac.testregisfirebase.Model.Post;
import jennie.umn.ac.testregisfirebase.Model.User;
import jennie.umn.ac.testregisfirebase.R;

public class ProfileFragment extends Fragment {

    ImageView image_profile, btnback;
    TextView posts, bio, username;
    Button btnEditProfile, btnDetails;

    FirebaseUser firebaseUser;
    String profileid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        SharedPreferences prefs = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        profileid = prefs.getString("profileid", "none");

        image_profile = view.findViewById(R.id.image_profile);
        btnback = view.findViewById(R.id.btn_back);
        posts = view.findViewById(R.id.posts);
        bio = view.findViewById(R.id.bio);
        username = view.findViewById(R.id.username);
        btnEditProfile = view.findViewById(R.id.btn_edit_profile);
        btnDetails = view.findViewById(R.id.btn_details);

        userInfo();

        if(profileid.equals(firebaseUser.getUid())){
            btnEditProfile.setText("Edit Profile");
        }

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String btn = btnEditProfile.getText().toString();

                if(btn.equals("Edit Profile")){
                    //go to editprofile
                } else if(btn.equals("Details")){
                    //go to detail commission
                }
            }
        });

        return view;
    }

    private void userInfo(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(profileid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                if (getContext() == null){
                    return;
                }
                User user = datasnapshot.getValue(User.class);

                Glide.with(getContext()).load(user.getImageurl()).into(image_profile);
                username.setText(user.getUsername());
                bio.setText(user.getBio());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getPosts(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;
                for(DataSnapshot snapshot1 : dataSnapshot.getChildren()){
                    Post post = snapshot1.getValue(Post.class);
                    if (post.getPublisher().equals(profileid)){
                        i++;
                    }
                }
                posts.setText(""+i);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}