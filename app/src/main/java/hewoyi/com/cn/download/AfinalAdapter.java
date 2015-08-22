package hewoyi.com.cn.download;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.HttpHandler;

import java.io.File;
import java.util.List;

public class AfinalAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<String> url;
    private ListView lv;
    private Context mContext;

    public AfinalAdapter(Context mContext,List<String> url,ListView lv) {
        this.mInflater = LayoutInflater.from(mContext);
        this.url = url;
        this.lv = lv;
        this.mContext =mContext;

    }

    /**
     * 自定义接口，用于回调按钮点击事件到Activity
     */
    private interface Callback {
        public void click(View v);
    }

   /* //响应按钮点击事件,调用子定义接口，并传入View
    @Override
    public void onClick(View v) {
    }*/

    @Override
    public int getCount() {
        return url.size();
    }

    @Override
    public Object getItem(int position) {
        return url.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.afinal_item, null);
            viewHolder.pb_afinal = (ProgressBar) convertView.findViewById(R.id.pb_afinal);
            viewHolder.current_afinal = (TextView) convertView.findViewById(R.id.tv_current_afinal);
            viewHolder.percent_afinal = (TextView) convertView.findViewById(R.id.tv_percent_afinal);
            viewHolder.start_afinal = (Button) convertView.findViewById(R.id.btn_afinal_start);
            //保存position位置在对应的控件里
            viewHolder.start_afinal.setTag(position);
            viewHolder.start_afinal.setOnClickListener(new View.OnClickListener() {

                private FinalHttp fh = new FinalHttp();
                private HttpHandler handler;

                //各种下载状态
                private static final int START = 1;
                private static final int STOP = 2;
                private static final int FINISH = 3;
                private int state = 1;//默认状态是START

                @Override
                public void onClick(View convertView) {
                    int position = (Integer) convertView.getTag();
                    Log.d("Adapter", "final:" + position);

                    Log.d("Adapter", "position: " + (position - lv.getFirstVisiblePosition()));
                    //获取被点击Item的view，然后得到各控件，无奈要声明为final，但不影响使用
                    final View childAt = lv.getChildAt(position - lv.getFirstVisiblePosition());
                    final Button start_afinal = (Button) childAt.findViewById(R.id.btn_afinal_start);
                    final TextView tv_current = (TextView) childAt.findViewById(R.id.tv_current_afinal);
                    final TextView tv_percent = (TextView) childAt.findViewById(R.id.tv_percent_afinal);
                    final ProgressBar pb_cutils = (ProgressBar) childAt.findViewById(R.id.pb_afinal);

                    switch (state) {
                        case START:
                            Toast.makeText(mContext,"position"+position+"\n不用点了....网上查了是框架编译器版本问题...fuck！浪费我时间\n提示：method does not override method from its superclass",Toast.LENGTH_LONG).show();
                               /* handler = fh.download("http://www.xxx.com/下载路径/xxx.apk", //这里是下载的路径
                                        true,//true:断点续传 false:不断点续传（全新下载）
                                        "/mnt/sdcard/testapk.apk", //这是保存到本地的路径
                                        new AjaxCallBack() {
                                            @Override
                                            public void onLoading(long count, long current) {
                                                tv_current.setText("下载进度："+current+"/"+count);
                                            }

                                            @Override
                                            public void onSuccess(File t) {
                                                tv_current.setText(t == null ? "null" : t.getAbsoluteFile().toString());
                                            }

                                        });
                                //设状态为可停止
                                state = STOP;
                                break;
                            case STOP:
                                start_afinal.setText("开始");
                                handler.stop();
                                //设状态为可开始
                                state = START;
                                break;
                            case FINISH:
                                //Finish代码
                                break;
                            default:
                                Log.d("Adapter", "position: " + (position - lv.getFirstVisiblePosition()));
                                break;*/
                    }
                }
            });
        } else {
            Log.d("Adapter", "else");
            //convertView.setTag(viewHolder);
        }
        return convertView;
    }

    /**
     * 优化类
     */
    private class ViewHolder {
        public ProgressBar pb_afinal;
        public TextView current_afinal;
        public TextView percent_afinal;
        public Button start_afinal;

    }
}
