package com.baway.lizhiqiang;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import junit.framework.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * date: 2017/7/17
 * author: 李志强
 * function:
 */

public class ShoppingCartAdapter extends BaseAdapter {
    private Context context;
    private List<ShoppBean> loans;
    private LayoutInflater inflater;
    private static HashMap<Integer, Boolean> isSelected;
    private static HashMap<Integer, Integer> numbers;
    private Handler handler;
    int num;// 商品数量

    static class ViewHolder { // 自定义控件集合
        public CheckBox ck_select;
        public ImageView pic_goods;
        public TextView id_goods;
        public TextView integral_goods;
        public LinearLayout layout;
        public TextView number;
        public Button minus;
        public Button plus;
    }

    /**
     * 实例化Adapter
     *
     * @param context
     * @param data
     */
    public ShoppingCartAdapter(Context context, Handler handler, List<ShoppBean> data) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.loans = data;
        this.handler = handler;
        isSelected = new HashMap<Integer, Boolean>();
        numbers = new HashMap<Integer, Integer>();
        initDate();
    }

    private void initDate() {
        for (int i = 0; i < loans.size(); i++) {
            getIsSelected().put(i, false);
            getNumbers().put(i, 1);
        }
    }

    @Override
    public int getCount() {
        return loans.size();
    }

    @Override
    public Object getItem(int position) {
        return loans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // 自定义视图
        ViewHolder itemView = null;
        if (convertView == null) {
            // 获取list_item布局文件的视图
            itemView = new ViewHolder();
            convertView = inflater.inflate(R.layout.activity_adapter,
                    null);
            // 获取控件对象
            itemView.ck_select = (CheckBox) convertView
                    .findViewById(R.id.ck_select);
            itemView.pic_goods = (ImageView) convertView
                    .findViewById(R.id.pic_goods);
            itemView.id_goods = (TextView) convertView
                    .findViewById(R.id.id_goods);


            itemView.integral_goods = (TextView) convertView
                    .findViewById(R.id.integral_goods);
            itemView.number = (TextView) convertView.findViewById(R.id.number);
            itemView.minus = (Button) convertView.findViewById(R.id.minus);

            itemView.plus = (Button) convertView.findViewById(R.id.plus);
            convertView.setTag(itemView);
        } else {
            itemView = (ViewHolder) convertView.getTag();
        }

        init(itemView, position);

        itemView.ck_select.setChecked(getIsSelected().get(position));

        itemView.number.setText(getNumbers().get(position).toString());
        if (getIsSelected().get(position)) {
            itemView.ck_select.setChecked(true);
        } else {
            itemView.ck_select.setChecked(false);
        }

        String a = itemView.number.getText().toString();
        loans.get(position).setNum(Integer.valueOf(a));



        itemView.pic_goods.setImageResource(R.mipmap.ic_launcher);
        return convertView;
    }

    private void init(final ViewHolder itemView, final int position) {

        itemView.ck_select
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView,
                                                 boolean isChecked) {
                        isSelected.put(position, true);
                        getIsSelected().put(position, isChecked);
                        itemView.ck_select.setChecked(getIsSelected().get(
                                position));
                        handler.sendMessage(handler.obtainMessage(10,
                                getTotalPrice()));

                        Iterator iterator = isSelected.entrySet().iterator();
                        List<Boolean> array = new ArrayList<Boolean>();
                        while (iterator.hasNext()) {
                            HashMap.Entry entry = (HashMap.Entry) iterator
                                    .next();
                            Integer key = (Integer) entry.getKey();
                            Boolean val = (Boolean) entry.getValue();
                            array.add(val);
                        }
                        handler.sendMessage(handler.obtainMessage(11, array));
                    }
                });

        final String numString = itemView.number.getText().toString();
        itemView.plus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (numString == null || numString.equals("")) {
                    num = 1;
                    itemView.number.setText("1");
                } else {
                    if (++num < 1) // 先加，再判断
                    {
                        num--;

                        Toast.makeText(context, "请输入一个大于0的数字",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        itemView.number.setText(String.valueOf(num));
                        loans.get(position).setNum(num);
                        numbers.put(position, num);
                        handler.sendMessage(handler.obtainMessage(10,
                                getTotalPrice()));
                        Log.i("test", "+:" + num);
                    }
                }
            }
        });

        itemView.minus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (numString == null || numString.equals("")) {
                    num = 1;
                    itemView.number.setText("1");
                } else {
                    if (--num < 1) // 先加，再判断
                    {
                        num++;
                        Log.i("test", "-:" + num);
                        Toast.makeText(context, "请输入一个大于0的数字",
                                Toast.LENGTH_SHORT).show();
                        Log.i("test", "-:" + num);
                    } else {
                        itemView.number.setText(String.valueOf(num));
                        Log.i("test", "-:" + num);
                        loans.get(position).setNum(num);
                        numbers.put(position, num);
                        handler.sendMessage(handler.obtainMessage(10,
                                getTotalPrice()));
                    }
                }

            }
        });

    }

    /**
     * 计算选中商品的积分
     *
     * @return 返回需要付费的总积分
     */
    private float getTotalPrice() {
        ShoppBean bean = null;
        float totalPrice = 0;
        for (int i = 0; i < loans.size(); i++) {
            bean = loans.get(i);
            if (ShoppingCartAdapter.getIsSelected().get(i)) {
                totalPrice += bean.getNum()
                        * Integer.valueOf(bean.getIntegral());
            }
        }
        return totalPrice;
    }

    public static HashMap<Integer, Boolean> getIsSelected() {
        return isSelected;
    }

    public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
        ShoppingCartAdapter.isSelected = isSelected;
    }

    public static HashMap<Integer, Integer> getNumbers() {
        return numbers;
    }

    public static void setNumbers(HashMap<Integer, Integer> numbers) {
        ShoppingCartAdapter.numbers = numbers;
    }
}
