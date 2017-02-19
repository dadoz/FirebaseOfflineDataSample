package com.application.davidelm.firebaseofflinedatasample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.application.davidelm.firebaseofflinedatasample.auth.EmailPasswordActivity;
import com.application.davidelm.firebaseofflinedatasample.models.Bookmark;
import com.firebase.*;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;
import com.google.firebase.database.Query;
import com.google.firebase.provider.FirebaseInitProvider;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ListActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "ListActivity";
    @BindView(R.id.firebaseRecyclerViewId)
    RecyclerView firebaseRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);

        findViewById(R.id.findDataButtonId).setOnClickListener(this);
        auth();
        initView();
    }

    /**
     * async but right now it doesnt matter
     */
    private void auth() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Log.e(TAG, task.getException().getMessage());
                            return;
                        }
                        Log.e(TAG, "successfully logged in");
                        initView();
                    }
                });
    }

    private void initView() {
        initActionBar();
        readFromFirebase();
        (findViewById(R.id.fab)).setOnClickListener(this);
    }

    /**
     *
     */
    private void readFromFirebase() {
        Log.e("TAG", "READING");
        LinearLayoutManager mManager = new LinearLayoutManager(this);
        mManager.setOrientation(LinearLayoutManager.VERTICAL);
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        firebaseRecyclerView.setLayoutManager(mManager);
        firebaseRecyclerView.setHasFixedSize(true);

        FirebaseRecyclerAdapterCustom adapter = new FirebaseRecyclerAdapterCustom(Bookmark.class,
                R.layout.bookmark_item, BookmarkViewHolder.class, getQuery());
        firebaseRecyclerView.setAdapter(adapter);
    }

    /**
     * query
     * @return
     */
    public Query getQuery() {
        DatabaseReference firebaseRef = FirebaseDatabase.getInstance().getReference();
        return firebaseRef.child("bookmarks").getRef();
    }

    private void initActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.findDataButtonId) {
            initView();
            return;
        }
        startActivity(new Intent(this, EmailPasswordActivity.class));
        Snackbar.make(view, "Start auth", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }


    /**
     * firebase custom adapter
     */
    public class FirebaseRecyclerAdapterCustom extends FirebaseRecyclerAdapter<Bookmark, BookmarkViewHolder> {


        public FirebaseRecyclerAdapterCustom(Class<Bookmark> modelClass, int modelLayout, Class<BookmarkViewHolder> viewHolderClass, Query ref) {
            super(modelClass, modelLayout, viewHolderClass, ref);
        }

        public FirebaseRecyclerAdapterCustom(Class<Bookmark> modelClass, int modelLayout, Class<BookmarkViewHolder> viewHolderClass, DatabaseReference ref) {
            super(modelClass, modelLayout, viewHolderClass, ref);
        }

        @Override
        protected void populateViewHolder(BookmarkViewHolder viewHolder, Bookmark model, int position) {
            Log.e(TAG, model.getName());
            viewHolder.bookmarkTextView.setText(model.getName());
        }
    }

    /**
     * bookmarkView holder
     */
    public static class BookmarkViewHolder extends RecyclerView.ViewHolder {
        public TextView bookmarkTextView;

        public BookmarkViewHolder(View v) {
            super(v);
            Log.e(TAG, "view holder created");
            bookmarkTextView = (TextView) itemView.findViewById(R.id.bookmarkTextViewId);
        }
    }
}
