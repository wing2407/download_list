package hewoyi.com.cn.download;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class Afinal extends ActionBarActivity {

    ListView lv;
    List<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afinal);

        lv = (ListView) findViewById(R.id.lv_afinal);
        list.add("http://p.gdown.baidu.com/b898029489f3258013809f8c37646b6be5a0f0599e0e86da452de2e975a5e5ab621130218047731bb9725a8eafb236714c78b4ae6158c6438adcf52d90e8b8658fb853e00407e3c11fd71faa7d972ccd90625637484ea7fbe9ef0d6f6cb7a9e94ea64f96f82c17725706ff695622a658");
        list.add("http://p.gdown.baidu.com/d7e547121af8dab4fdf173d43a20e5c64dbd3033c87e4c9aad889971d6e8254128c6b4f0a663fccb7d247c9fa3f9e51138574230085b8fbbce114c00857e5b3f4dd4e14dc8d79310467c55250c777b56d8cd0440d90324f5dbe2ea1216ce5ebc7f013c612fb4ecfed0a72592c96f70f48a886fa24cbe1065ac8b838d24114984c187947c14e04c8c19d7a09783bfdaee6254411a6e70738a");
        list.add("http://p.gdown.baidu.com/e5ffd8da9a1c3ecd2fcb005838a061accfdc7778595b582df7ba074e82b50d42584cd98f680800b17f54199102d4faab6bc111afaa5a8e1252651f73c5d0e63426843a9e3dfd23608e535954d7051c8d633721bfd57424c3d57d7a180052e45a43a4474d7a6f114eb78098e44310a680e318aa2ad95098a43bae7c471d1896b76458cc7fb9773edd6d6ec3e718dd72b0");
        AfinalAdapter afinalAdapter = new AfinalAdapter(Afinal.this,list,lv);
        lv.setAdapter(afinalAdapter);

        /*for (int i = 0; i < list.size(); i++) {
            Log.d("Adapter", list.get(i));
        }*/

        Button btn_goto_xutils = (Button)findViewById(R.id.btn_goto_xutils);
        btn_goto_xutils.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Afinal.this, XUtils.class);
                startActivity(intent);
            }
        });
        
    }

    @Override
    protected void onStart() {
        super.onStart();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                parent.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
                Toast.makeText(Afinal.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
