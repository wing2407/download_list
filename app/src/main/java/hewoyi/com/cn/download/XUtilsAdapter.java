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

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.io.File;
import java.util.List;

public class XUtilsAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<String> url;
    private ListView lv;

    public XUtilsAdapter(Context mContext,List<String> url,ListView lv) {
        this.mInflater = LayoutInflater.from(mContext);
        this.url = url;
        this.lv = lv;

    }

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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.xutils_item, null);
            viewHolder.pb_xutils = (ProgressBar) convertView.findViewById(R.id.pb_xutils);
            viewHolder.current_xutils = (TextView) convertView.findViewById(R.id.tv_current_xutils);
            viewHolder.percent_xutils = (TextView) convertView.findViewById(R.id.tv_percent_xutils);
            viewHolder.start_xutils = (Button) convertView.findViewById(R.id.btn_xutils_start);
            //保存position位置在对应的控件里
            viewHolder.start_xutils.setTag(position);
            viewHolder.start_xutils.setOnClickListener(new MyClickListener() {

                //框架的http类
                private HttpUtils http = new HttpUtils();
                private HttpHandler handler;

                //各种下载状态
                private static final int START = 1;
                private static final int STOP = 2;
                private static final int FINISH = 3;
                private int state = 1;//默认状态是START

                @Override
                public void myOnClick(int position, View v) {

                    Log.d("Adapter", "position: " + (position - lv.getFirstVisiblePosition()));
                    //获取被点击Item的view，然后得到各控件，无奈要声明为final，但不影响使用
                    final View childAt = lv.getChildAt(position - lv.getFirstVisiblePosition());
                    final Button start_xutils = (Button)childAt.findViewById(R.id.btn_xutils_start);
                    final TextView tv_current = (TextView) childAt.findViewById(R.id.tv_current_xutils);
                    final TextView tv_percent = (TextView) childAt.findViewById(R.id.tv_percent_xutils);
                    final ProgressBar pb_cutils = (ProgressBar)childAt.findViewById(R.id.pb_xutils);

                    switch (state){
                        case START:
                            handler = http.download(url.get(position),
                                    "/sdcard/" + position,//下载的文件路径，该方法默认下载完毕后自动重命名
                                    true,// 如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时将从新下载。
                                    true,// 如果从请求返回信息中获取到文件名，下载完成后自动重命名。
                                    new RequestCallBack<File>() {

                                        @Override
                                        public void onStart() {
                                            start_xutils.setText("停止");
                                            //Log.d("Adapter", "start...");
                                        }

                                        @Override
                                        public void onLoading(long total, long current, boolean isUploading) {

                                            Log.d("Adapter", current+ "/" + total);
                                            if (childAt != null) {
                                                tv_current.setText(current+ "/" + total);
                                                tv_percent.setText(current * 100 / total + "%");
                                                pb_cutils.setProgress((int)(current * 100 / total));
                                            }
                                        }

                                        @Override
                                        public void onSuccess(ResponseInfo<File> responseInfo) {
                                            Log.d("Adapter", "downloaded:" + responseInfo.result.getPath());
                                            start_xutils.setText("完成");
                                        }

                                        @Override
                                        public void onFailure(HttpException error, String msg) {
                                            Log.d("Adapter", msg);
                                        }
                                    });
                            //设状态为可停止
                            state = STOP;
                            break;
                        case STOP:
                            start_xutils.setText("开始");
                            handler.cancel();
                            //设状态为可开始
                            state = START;
                            break;
                        case FINISH:
                            //Finish代码
                            break;
                        default:
                            break;
                    }
                }
            });
        } else {
            Log.d("Adapter","else");
            //convertView.setTag(viewHolder);
        }
        return convertView;
    }

    /**
     * 优化类
     */
    private class ViewHolder {
        public ProgressBar pb_xutils;
        public TextView current_xutils;
        public TextView percent_xutils;
        public Button start_xutils;

    }

    /**
     * 针对listView中单个Item的button点击事件
     */
    private static abstract class MyClickListener implements View.OnClickListener {


        @Override
        public void onClick(View v) {
            myOnClick((Integer)v.getTag(),v);
        }

        public abstract void myOnClick(int position,View v);
    }

}
