package com.xiberty.propongo.councils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiberty.propongo.Constants;
import com.xiberty.propongo.R;
import com.xiberty.propongo.councils.adapters.CommentAdapter;
import com.xiberty.propongo.database.Comment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class CommentsActivity extends AppCompatActivity {


    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.placeholder_text)
    TextView placeholderText;
    @BindView(R.id.placeholder)
    LinearLayout placeholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        ButterKnife.bind(this);

        setComments();

    }

    @OnClick(R.id.btnGoBack)
    public void goBack(View view) {
        finish();
    }

    private void setComments() {
        Bundle bundle = getIntent().getExtras();
        if (!bundle.isEmpty()) {
            String commentStr = bundle.getString(Constants.KEY_COMMENTS);
            Gson gson = new Gson();
            List<Comment> comments = gson.fromJson(commentStr, new TypeToken<List<Comment>>() {
            }.getType());
            if (!comments.isEmpty() && comments !=null)
                fillComments(comments);
            else
                placeholder.setVisibility(View.VISIBLE);
            placeholderText.setText("No Existen Datos");

        }
    }

    private void fillComments(List<Comment> comments) {
        /**
         * Show just 3 comments
         * **/
        List<Comment> threeComments = new ArrayList<>();
        int count = 0;
        for (Comment comment : comments) {
            count++;
            threeComments.add(comment);
            if (count > 3) break;
        }

        CommentAdapter adapter = new CommentAdapter(this, threeComments);
        listView.setAdapter(adapter);
    }


}
