package com.food.foodpos;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.food.foodpos.dto.OrderAddItem;
import com.food.foodpos.dto.OrderDTO;
import com.food.foodpos.util.Feature;
import com.food.foodpos.util.Food;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Andy on 2015/6/18.
 */
public class OrderFragment extends Fragment {

    private ViewHold viewHold = new ViewHold();
    private OrderDTO orderDTO = new OrderDTO();

    private FoodAdapter foodAdapter = null;
    private OrderAdapter orderAdapter = null;
    private FeatureAdapter featureAdapter = null;

    private static final String MESSAGE = "[%s]%s-----[$%d]";//名稱 [數量]-[$]=[$]


    private class ViewHold {
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

           String title= String.format(MESSAGE,item.getNo(), item.getName(),  item.getDollar());//名稱 [數量]-[$]=[$];
           if(StringUtils.isNotBlank(item.getFeature())){
               title+="\n"+item.getFeature();
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

        this.viewHold.foodGridView = (GridView) rootView.findViewById(R.id.foodGridView);
        this.viewHold.featureGridView = (GridView) rootView.findViewById(R.id.featureGridView);
        this.viewHold.orderList = (ListView) rootView.findViewById(R.id.orderList);
        this.viewHold.featureEdit = (EditText) rootView.findViewById(R.id.featureEdit);
        this.viewHold.foodEdit = (EditText) rootView.findViewById(R.id.foodEdit);
        this.viewHold.noEdit = (EditText) rootView.findViewById(R.id.noEdit);
        this.viewHold.totalMoneyLab = (TextView) rootView.findViewById(R.id.totalMoneyLab);

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


        this.featureAdapter = new FeatureAdapter(viewHold.featureEdit);
        this.viewHold.featureGridView.setAdapter(featureAdapter);


        this.foodAdapter = new FoodAdapter(viewHold.foodEdit);
        this.viewHold.foodGridView.setAdapter(foodAdapter);
        this.orderAdapter = new OrderAdapter(orderDTO.getItems(), this.viewHold.totalMoneyLab);
        this.viewHold.orderList.setAdapter(orderAdapter);


        return rootView;
    }


}