package moonc.example.com.useexternaldatabase;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    SqliteHelper sqliteHelper;
    Cursor cursor;
    ObjectCreated objectCreated;
    ArrayList<ObjectCreated> mArrayList;
    adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        init_views();
        inti_variables();
        init_functions();

    }

    private void init_functions() {

        sqliteHelper.CreateDb();

        cursor = sqliteHelper.Show();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            byte[] blob = cursor.getBlob(2);
            objectCreated = new ObjectCreated(id,name,blob);
            mArrayList.add(objectCreated);
        }

        mAdapter = new adapter(this,mArrayList);
        listView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this,Profile.class);
                intent.putExtra("id",mArrayList.get(position).getId());
                intent.putExtra("name",mArrayList.get(position).getName());
                intent.putExtra("image",mArrayList.get(position).getImage());
                startActivity(intent);
            }
        });

    }

    private void init_views() {
        listView = (ListView) findViewById(R.id.lst_load);
    }

    private void inti_variables() {
        sqliteHelper = new SqliteHelper(this);
        mArrayList = new ArrayList<>();
    }
}
