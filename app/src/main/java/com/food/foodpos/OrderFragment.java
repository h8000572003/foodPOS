package com.food.foodpos;


import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.food.db.domainType.Bill;
import com.food.db.domainType.Meal;
import com.food.foodpos.dto.BaseRestObj;
import com.food.foodpos.dto.BillSon;
import com.food.foodpos.dto.OrderAddItem;
import com.food.foodpos.dto.OrderDTO;
import com.food.foodpos.util.AeUtils;
import com.food.foodpos.util.Feature;
import com.food.foodpos.util.Food;
import com.food.foodpos.util.FoodUploadAsyTask;
import com.food.foodpos.util.Util;
import com.food.foodpos.util.gcm.GenericuRestTask;
import com.food.foodpos.util.gcm.RestResultException;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Andy on 2015/6/18.
 */
public class OrderFragment extends Fragment implements GenericuRestTask.RestAsyTaskListener<BaseRestObj> {

    private static final String TAG = "OrderFragment";
    private ViewHold viewHold = new ViewHold();
    private OrderDTO orderDTO = new OrderDTO();

    private FoodAdapter foodAdapter = null;
    private OrderAdapter orderAdapter = null;
    private FeatureAdapter featureAdapter = null;

    private static final String MESSAGE = "[%s]%s-----[$%d]";//名稱 [數量]-[$]=[$]

    private static FoodUploadAsyTask foodUploadAsyTask;

    @Override
    public void message(RestResultException e, BaseRestObj content) {
        Log.i(TAG, "insert...");

    }


    private class ViewHold {
        private RadioGroup outOrInRadioGroup;
        private RadioButton inRadioBtn;
        private RadioButton outRadioBtn;

        private GridView foodGridView;
        private GridView featureGridView;
        private ListView orderList;

        private EditText featureEdit;
        private EditText foodEdit;
        private EditText noEdit;
        private TextView totalMoneyLab;

        private Button b1;
        private Button b2;
        private Button b3;
        private Button b4;
        private Button b5;
        private Button b6;
        private Button b7;
        private Button b8;
        private Button b9;
        private Button b0;
        private Button cleanBtn;
        private Button addBtn;
        private Button orderBtn;

    }

    protected interface OnSetInitLinster {
        void onClick();
    }

    private class OrderAdapter extends BaseAdapter implements OnSetInitLinster {

        private List<OrderAddItem> items;
        private int totlaMoney = 0;
        private TextView toatalMoney = null;

        private LayoutInflater layoutInflater;

        public OrderAdapter(List<OrderAddItem> items, TextView toatalMoney) {
            this.layoutInflater = LayoutInflater.from(getActivity());
            this.items = items;
            this.toatalMoney = toatalMoney;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            Holder holder = null;
            if (convertView == null) {
                view = layoutInflater.inflate(R.layout.item_order_layout, null);
                holder = new Holder();
                holder.title = (TextView) view.findViewById(R.id.title);
                holder.cancelBtn = (Button) view.findViewById(R.id.cancelBtn);

                view.setTag(holder);
            } else {
                holder = (Holder) view.getTag();
            }


            final OrderAddItem item = items.get(position);

            String title = String.format(MESSAGE, item.getNo(), item.getName(), item.getDollar());//名稱 [數量]\t[$]=[$];
            if (StringUtils.isNotBlank(item.getFeature())) {
                title += "\n" + item.getFeature();
            }
            holder.title.setText(title);
            holder.cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    items.remove(item);
                    notifyDataSetChanged();

                }
            });


            return view;
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();

            int toatal = 0;
            for (OrderAddItem item1 : items) {
                toatal += item1.getDollar() * Integer.parseInt(item1.getNo());

            }
            this.totlaMoney = toatal;
            this.toatalMoney.setText("$" + this.totlaMoney);

        }

        @Override
        public void onClick() {
            items.clear();
            notifyDataSetChanged();
        }

        private class Holder {
            private TextView title;
            private Button cancelBtn;
        }
    }

    private class FoodAdapter extends BaseAdapter implements OnSetInitLinster {


        private Set<Integer> locations = new HashSet<>();

        private LayoutInflater layoutInflater;

        private Food[] foods = Food.values();

        private EditText editText;

        public FoodAdapter(EditText editText) {
            this.layoutInflater = LayoutInflater.from(getActivity());
            this.editText = editText;
        }

        @Override
        public int getCount() {
            return foods.length;
        }

        @Override
        public Object getItem(int position) {
            return foods[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = convertView;
            Holder holder = null;
            if (convertView == null) {
                view = layoutInflater.inflate(R.layout.feature_layout, null);
                holder = new Holder();
                holder.textView = (TextView) view.findViewById(R.id.text);
                holder.textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        locations.clear();
                        locations.add(position);
                        notifyDataSetChanged();
                    }
                });

                view.setTag(holder);
            } else {
                holder = (Holder) view.getTag();
            }


            final Food f = foods[position];

            holder.textView.setText(Html.fromHtml(f.getName() + "<font color=\"red\">" + f.getDollar() + "</font>"));


            if (locations.contains(position)) {
                holder.textView.setBackgroundResource(android.R.color.holo_green_light);
            } else {
                holder.textView.setBackgroundColor(Color.WHITE);
            }

            List<String> featuresDes = new ArrayList<>();
            for (Integer p : locations) {
                featuresDes.add(foods[p].getName());
            }
            editText.setText(StringUtils.join(featuresDes, ","));

            return view;
        }

        @Override
        public void onClick() {
            locations.clear();
            notifyDataSetChanged();
        }

        private class Holder {
            private TextView textView;
        }

        public Food getNowSelectFood() {
            Food f = null;

            for (Integer lo : locations) {
                f = foods[lo];
            }
            return f;
        }
    }

    private class FeatureAdapter extends BaseAdapter implements OnSetInitLinster {

        private Feature[] features = Feature.values();
        private LayoutInflater layoutInflater;
        private Set<Integer> locations = new HashSet<>();
        private EditText editText;


        public FeatureAdapter(EditText editText) {
            this.editText = editText;
            this.layoutInflater = LayoutInflater.from(getActivity());
        }

        @Override
        public int getCount() {
            return features.length;
        }

        @Override
        public Object getItem(int position) {
            return features[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {


            View view = convertView;
            Holder holder = null;
            if (convertView == null) {
                view = layoutInflater.inflate(R.layout.feature_layout, null);


                holder = new Holder();
                holder.textView = (TextView) view.findViewById(R.id.text);
                holder.textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if (locations.contains(position)) {
                            locations.remove(position);
                        } else {
                            locations.add(position);
                        }

                        notifyDataSetChanged();
                    }
                });

                view.setTag(holder);
            } else {
                holder = (Holder) view.getTag();
            }

            if (locations.contains(position)) {
                holder.textView.setBackgroundResource(android.R.color.holo_green_light);
            } else {
                holder.textView.setBackgroundColor(Color.WHITE);
            }

            List<String> featuresDes = new ArrayList<>();
            for (Integer p : locations) {
                featuresDes.add(features[p].getName());
            }
            editText.setText(StringUtils.join(featuresDes, ","));


            holder.textView.setText(features[position].getName());

            return view;
        }

        @Override
        public void onClick() {
            locations.clear();
            notifyDataSetChanged();
        }


        private class Holder {
            private TextView textView;

        }
    }

    private class MyClick implements View.OnClickListener {

        private String value = "";
        private EditText editText;

        protected MyClick(String value, EditText editText) {
            this.value = value;
            this.editText = editText;
        }

        @Override
        public void onClick(View v) {
            if (StringUtils.isBlank(value)) {
                editText.setText("");
            } else {
                editText.setText(editText.getText().toString() + value);
            }
        }
    }

    private class ToOrderListener implements View.OnClickListener {


        @Override
        public void onClick(View view) {


            switch (viewHold.outOrInRadioGroup.getCheckedRadioButtonId()) {
                case R.id.inRadioBtn:
                    orderDTO.setOutOrIn("I");
                    break;

                case R.id.orderBtn:
                    orderDTO.setOutOrIn("O");
                    break;
                default:
                    orderDTO.setOutOrIn(StringUtils.EMPTY);
                    break;
            }

            if (orderDTO.getItems().isEmpty()) {
                Toast.makeText(getActivity(), "至少選一道菜", Toast.LENGTH_SHORT).show();
                return;
            } else if (StringUtils.isBlank(orderDTO.getOutOrIn())) {
                Toast.makeText(getActivity(), "請選擇「內用」還是「外帶」", Toast.LENGTH_SHORT).show();
                return;
            }
            orderDTO.setUseNo(Util.getSeq(getActivity()));
            orderDTO.setNowDate(AeUtils.getNowDate());
            orderDTO.setNowTime(AeUtils.getNowTime());
            orderDTO.setTxId(orderDTO.getNowDate() + orderDTO.getNowTime());


            final StringBuffer buffer = new StringBuffer();
            int totoal = 0;
            buffer.append("序號：" + orderDTO.getUseNo() + "\n");
            for (OrderAddItem item : orderDTO.getItems()) {
                buffer.append("[" + item.getNo() + "]");
                buffer.append(item.getName() + "\n");
                totoal += Integer.parseInt(item.getNo()) * item.getDollar();
            }
            buffer.append("===");
            buffer.append("總金額:$" + totoal);

            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("買單").setMessage(buffer.toString());
            builder.setPositiveButton("買單", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    FoodUploadAsyTask foodUploadAsyTask = new FoodUploadAsyTask(getActivity());


                    Bill bill = new Bill();

                    bill.setFeature(StringUtils.EMPTY);
                    bill.setIsMealOut("N");
                    bill.setIsMealOut("N");
                    bill.setIsPaid("Y");
                    bill.setSeat("1");
                    bill.setTxId(orderDTO.getTxId());
                    bill.setUseNo(orderDTO.getUseNo());
                    bill.setOrderDate(orderDTO.getNowDate());
                    bill.setOrderTime(orderDTO.getNowTime());
                    bill.setIsSpeakOut("N");
                    bill.setIsMealOut("N");
                    bill.setOutOrIn(orderDTO.getOutOrIn());

                    List<Meal> meals = new ArrayList<>();

                    int total = 0;
                    for (OrderAddItem item : orderDTO.getItems()) {
                        final Meal meal = new Meal();
                        meal.setTxId(bill.getTxId());
                        meal.setDollar(item.getDollar() + "");
                        meal.setName(item.getName());
                        meal.setSpcialize(item.getFeature());
                        meal.setNumber(item.getNo());
                        meal.setUseNumber("0");
                        meals.add(meal);
                        total += Integer.parseInt(item.getNo()) * item.getDollar();

                    }
                    bill.setDollar(total + "");

                    BillSon son = new BillSon();
                    son.setBill(bill);
                    son.setMeals(meals);

                    foodUploadAsyTask.setSon(son);
                    foodUploadAsyTask.setRestAsyTaskListener(OrderFragment.this);
                    foodUploadAsyTask.execute();
                    cleanAllView();


                    Toast.makeText(getActivity(), "菜單送出", Toast.LENGTH_SHORT).show();


                }
            });
            builder.setNegativeButton("發現有錯，調整一下", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.create().show();


        }
    }

    private void cleanAllView() {

        orderDTO.getItems().clear();

        foodAdapter.onClick();
        orderAdapter.onClick();
        featureAdapter.onClick();


        featureAdapter.notifyDataSetChanged();
        viewHold.featureEdit.setText(StringUtils.EMPTY);
        viewHold.noEdit.setText(StringUtils.EMPTY);
        viewHold.foodEdit.setText(StringUtils.EMPTY);
        viewHold.outOrInRadioGroup.clearCheck();
    }

    private class MyAdd implements View.OnClickListener {
        private EditText noEdit;
        private EditText foodEit;
        private EditText feathureEdit;

        public MyAdd(EditText noEdit, EditText foodEit, EditText feathureEdit) {
            this.noEdit = noEdit;
            this.foodEit = foodEit;
            this.feathureEdit = feathureEdit;
        }

        @Override
        public void onClick(View v) {


            if (StringUtils.isBlank(foodEit.getText().toString())) {
                Toast.makeText(getActivity(), "食物要選", Toast.LENGTH_SHORT).show();
            } else {

                final OrderAddItem item = new OrderAddItem();
                item.setNo(StringUtils.isBlank(noEdit.getText().toString()) ? "1" : noEdit.getText().toString());
                item.setName(foodEit.getText().toString());
                item.setFeature(feathureEdit.getText().toString());
                item.setDollar(foodAdapter.getNowSelectFood().getDollar());

                boolean hasTheSameFood = false;
                for (OrderAddItem orderAddItem : orderDTO.getItems()) {
                    if (StringUtils.equals(orderAddItem.getName(), item.getName()) && StringUtils.equals(orderAddItem.getFeature(), item.getFeature())) {
                        hasTheSameFood = true;

                        orderAddItem.setNo(Integer.parseInt(orderAddItem.getNo()) + Integer.parseInt(item.getNo()) + "");
                    }
                }
                if (!hasTheSameFood) {
                    orderDTO.getItems().add(item);
                }


                orderAdapter.notifyDataSetChanged();

                noEdit.setText("");
                foodEit.setText("");
                feathureEdit.setText("");


                foodAdapter.onClick();
                featureAdapter.onClick();
            }


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_order_layout, container, false);

        this.viewHold.outOrInRadioGroup = (RadioGroup) rootView.findViewById(R.id.outOrInRadioGroup);
        this.viewHold.inRadioBtn = (RadioButton) rootView.findViewById(R.id.inRadioBtn);
        this.viewHold.outRadioBtn = (RadioButton) rootView.findViewById(R.id.outRadioBtn);

        this.viewHold.foodGridView = (GridView) rootView.findViewById(R.id.foodGridView);
        this.viewHold.featureGridView = (GridView) rootView.findViewById(R.id.featureGridView);
        this.viewHold.orderList = (ListView) rootView.findViewById(R.id.orderList);
        this.viewHold.featureEdit = (EditText) rootView.findViewById(R.id.featureEdit);
        this.viewHold.foodEdit = (EditText) rootView.findViewById(R.id.foodEdit);
        this.viewHold.noEdit = (EditText) rootView.findViewById(R.id.noEdit);
        this.viewHold.totalMoneyLab = (TextView) rootView.findViewById(R.id.totalMoneyLab);
        this.viewHold.orderBtn = (Button) rootView.findViewById(R.id.orderBtn);

        this.viewHold.b0 = (Button) rootView.findViewById(R.id.b0);
        this.viewHold.b1 = (Button) rootView.findViewById(R.id.b1);
        this.viewHold.b2 = (Button) rootView.findViewById(R.id.b2);
        this.viewHold.b3 = (Button) rootView.findViewById(R.id.b3);
        this.viewHold.b4 = (Button) rootView.findViewById(R.id.b4);
        this.viewHold.b5 = (Button) rootView.findViewById(R.id.b5);
        this.viewHold.b6 = (Button) rootView.findViewById(R.id.b6);
        this.viewHold.b7 = (Button) rootView.findViewById(R.id.b7);
        this.viewHold.b8 = (Button) rootView.findViewById(R.id.b8);
        this.viewHold.b9 = (Button) rootView.findViewById(R.id.b9);
        this.viewHold.b0 = (Button) rootView.findViewById(R.id.b0);
        this.viewHold.cleanBtn = (Button) rootView.findViewById(R.id.cleanBtn);
        this.viewHold.addBtn = (Button) rootView.findViewById(R.id.addBtn);

        this.viewHold.b0.setOnClickListener(new MyClick("0", this.viewHold.noEdit));
        this.viewHold.b1.setOnClickListener(new MyClick("1", this.viewHold.noEdit));
        this.viewHold.b2.setOnClickListener(new MyClick("2", this.viewHold.noEdit));
        this.viewHold.b3.setOnClickListener(new MyClick("3", this.viewHold.noEdit));
        this.viewHold.b4.setOnClickListener(new MyClick("4", this.viewHold.noEdit));
        this.viewHold.b5.setOnClickListener(new MyClick("5", this.viewHold.noEdit));
        this.viewHold.b6.setOnClickListener(new MyClick("6", this.viewHold.noEdit));
        this.viewHold.b7.setOnClickListener(new MyClick("7", this.viewHold.noEdit));
        this.viewHold.b8.setOnClickListener(new MyClick("8", this.viewHold.noEdit));
        this.viewHold.b9.setOnClickListener(new MyClick("9", this.viewHold.noEdit));
        this.viewHold.cleanBtn.setOnClickListener(new MyClick("", this.viewHold.noEdit));
        this.viewHold.addBtn.setOnClickListener(new MyAdd(this.viewHold.noEdit, this.viewHold.foodEdit, this.viewHold.featureEdit));
        this.viewHold.orderBtn.setOnClickListener(new ToOrderListener());


        this.featureAdapter = new FeatureAdapter(viewHold.featureEdit);
        this.viewHold.featureGridView.setAdapter(featureAdapter);


        this.foodAdapter = new FoodAdapter(viewHold.foodEdit);
        this.viewHold.foodGridView.setAdapter(foodAdapter);
        this.orderAdapter = new OrderAdapter(orderDTO.getItems(), this.viewHold.totalMoneyLab);
        this.viewHold.orderList.setAdapter(orderAdapter);


        return rootView;
    }


}
