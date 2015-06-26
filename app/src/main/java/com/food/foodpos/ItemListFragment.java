package com.food.foodpos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.food.db.domainType.Bill;
import com.food.db.domainType.Meal;
import com.food.foodpos.dto.BillSon;
import com.food.foodpos.dto.ItemListDTO;
import com.food.foodpos.dto.JsonBill;
import com.food.foodpos.service.ToFoodService;
import com.food.foodpos.service.ToFoodServiceImpl;
import com.food.foodpos.util.BillAsyTask;
import com.food.foodpos.util.LoadNoSpeakOutBillTask;
import com.food.foodpos.util.Update2PayAsyTask;
import com.food.foodpos.util.UpdateIsSpeakOutAsyTask;
import com.food.foodpos.util.gcm.GenericuRestTask;
import com.food.foodpos.util.gcm.RestResultException;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A list fragment representing a list of Items. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link ItemDetailFragment}.
 * <p/>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class ItemListFragment extends Fragment implements GenericuRestTask.RestAsyTaskListener<JsonBill> {

    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";
    private static final String TAG = "ItemListFragment";

    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private Callbacks mCallbacks = sDummyCallbacks;

    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;

    private BillAsyTask billRestAsyTask = null;
    private ItemListDTO itemListDTO = new ItemListDTO();


    private ListView gridView;
    private ListView unGridView;
    private TextView loadUnSpeakOutBillBtn;
    private TextView showMeaage;


    private ImageButton loadBtn;


    private UnWorkAdapter unWorkAdapter;
    private WorkAdapter workAdapter;

    private ToFoodService toFoodService = ToFoodServiceImpl.get();


    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(String id);
    }

    /**
     * A dummy implementation of the {@link Callbacks} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(String id) {
        }
    };


    @Override
    public void message(RestResultException e, JsonBill content) {
        this.loadUnSpeakOutBillBtn.setVisibility(View.GONE);
        if (e == null) {


            itemListDTO.getUnAddList().clear();
            itemListDTO.getUnAddList().addAll(content.getContent());
            setShowMeaage();

        } else {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e(TAG, "loadinng fail e:", e);
        }


        this.billRestAsyTask = null;

        this.unWorkAdapter.notifyDataSetChanged();

    }


    private class UnWorkAdapter extends BaseAdapter {

        private List<BillSon> unAddList;
        private LayoutInflater mChildInflater;

        public UnWorkAdapter(List<BillSon> unAddList) {
            this.unAddList = unAddList;
            mChildInflater = (LayoutInflater) getActivity()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return unAddList.size();
        }

        @Override
        public Object getItem(int position) {
            return unAddList.get(0);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent)

        {

            View rootView = convertView;
            Holder holder = null;
            if (rootView == null) {
                rootView = mChildInflater.inflate(R.layout.item_un_add_bill_layout, parent, false);

                holder = new Holder();
                holder.message = (TextView) rootView.findViewById(R.id.message);
                holder.addBillBtn = (Button) rootView.findViewById(R.id.addBillBtn);

                holder.addBillBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final BillSon son =
                                unAddList.get(position);

                        unAddList.remove(son);
                        itemListDTO.setNowSelectBill(son);
                        toFoodService.insertToWork(itemListDTO, getActivity());
                        itemListDTO.getAddList().clear();
                        itemListDTO.getAddList().addAll(toFoodService.queryNowWorkList(itemListDTO, getActivity()));
                        notifyDataSetChanged();
                        workAdapter.notifyDataSetChanged();
                        setShowMeaage();
                        new UpdateIsSpeakOutAsyTask(son.getBill().getTxId(), "Y", getActivity()).execute();
                    }
                });


                rootView.setTag(holder);
            } else {
                holder = (Holder) rootView.getTag();
            }


            BillSon son = unAddList.get(position);
            StringBuffer stringBuffer = new StringBuffer();
            for (Meal meal : son.getMeals()) {
                stringBuffer.append("[" + meal.getNumber() + "]");
                stringBuffer.append("" + meal.getName() + "");
                if (!StringUtils.isEmpty(meal.getSpcialize())) {
                    stringBuffer.append("(" + meal.getSpcialize() + ")\n");
                }
            }
            holder.message.setText(stringBuffer.toString());


            return rootView;


        }

        private class Holder {
            private TextView message;
            private Button addBillBtn;
        }
    }

    private class WorkAdapter extends BaseAdapter {
        private LayoutInflater mChildInflater;
        private List<BillSon> addList;


        public WorkAdapter(List<BillSon> addList) {
            mChildInflater = (LayoutInflater) getActivity()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.addList = addList;
        }

        @Override
        public int getCount() {
            return addList.size();
        }

        @Override
        public Object getItem(int position) {
            return this.addList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            GroupViewHolder holder = null;
            View view = convertView;

            if (view == null) {
                view = mChildInflater.inflate(R.layout.bill_title_layout, parent, false);
                holder = new GroupViewHolder();
                holder.titile = (TextView) view.findViewById(R.id.title);
                holder.buyBillBtn = (Button) view.findViewById(R.id.buyBillBtn);
                holder.meals = (LinearLayout) view.findViewById(R.id.linearLayout);
                holder.closeBtn = (Button) view.findViewById(R.id.closeBtn);

                view.setTag(holder);
            } else {
                holder = (GroupViewHolder) view.getTag();
            }
            holder.meals.removeAllViews();


            final BillSon son =
                    addList.get(position);


            this.setTitleShow(son, holder.titile);
            this.setBuyBtnShow(son, holder.buyBillBtn);
            this.setCloseBtnShow(son, holder.closeBtn);


            for (final Meal meal : son.getMeals()) {

                final View addView = mChildInflater.inflate(R.layout.bill_item_layout, parent, false);

                final ItemHolder itemHolder = new ItemHolder();
                itemHolder.titile = (TextView) addView.findViewById(R.id.foodItem);
                itemHolder.toFoodBtn = (Button) addView.findViewById(R.id.toFoodBtn);
                itemHolder.reduceNumBtn = (Button) addView.findViewById(R.id.reduceNumBtn);
                addView.setTag(itemHolder);

                this.setSubTitle(meal, itemHolder.titile);//設定顯示標題
                this.setReduceBtn(son, meal, itemHolder.reduceNumBtn);
                this.setFoodBtn(son, meal, itemHolder.toFoodBtn);

                holder.meals.addView(addView);


            }

            return view;
        }

        private void setFoodBtn(final BillSon json, final Meal meal, Button button) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    itemListDTO.setNowSelectBill(json);
                    itemListDTO.setNowMeal(meal);
                    toFoodService.toFood(itemListDTO, getActivity());
                    workAdapter.notifyDataSetChanged();
                    setShowMeaage();
                }
            });

        }

        private void setReduceBtn(final BillSon json, final Meal meal, Button button) {

            int showValue = Integer.parseInt(meal.getNumber()) - Integer.parseInt(meal.getUseNumber());
            button.setText(showValue + "");

            if (StringUtils.equals(meal.getUseNumber(), meal.getNumber())) {

                button.setEnabled(false);
            }

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    itemListDTO.setNowSelectBill(json);
                    itemListDTO.setNowMeal(meal);

                    toFoodService.reduceNum(itemListDTO, getActivity());


                    notifyDataSetChanged();
                    setShowMeaage();
                }
            });
        }

        public void setSubTitle(Meal meal, TextView titile) {
            final StringBuffer itemName = new StringBuffer();
            itemName.append("[");
            itemName.append(meal.getNumber());
            itemName.append("]");
            itemName.append(meal.getName());
            if (!meal.getSpcialize().equals("")) {
                itemName.append("(");
                itemName.append(")");
            }

            titile.setText(itemName.toString());
        }


        private String getTitle(BillSon son) {

            //"[外/內][001][井邊]夫妻 [$500]"
            StringBuffer buffer = new StringBuffer();
            buffer.append("sn:" + son.getBill().getUseNo());
            if (son.getBill().getOutOrIn().equals("O")) {
                buffer.append("[外帶]");
            } else {
                buffer.append("[內用]");
            }
            buffer.append("[" + son.getBill().getSeat() + "]");
            if (!son.getBill().getFeature().equals("")) {
                buffer.append("[" + son.getBill().getFeature() + "]");
            }
            return buffer.toString();
        }

        private void setTitleShow(final BillSon json, TextView textView) {
            textView.setText(this.getTitle(json));

        }

        private void setCloseBtnShow(final BillSon json, Button button) {

            final Bill bill =
                    json.getBill();

            if (this.checkIsMealOutAndPay(bill)) {
                button.setVisibility(View.VISIBLE);
            } else {
                button.setVisibility(View.GONE);
            }

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toFoodService.toClose(itemListDTO, getActivity());
                    notifyDataSetChanged();
                }
            });
        }

        /**
         * 是否已經出菜且結帳
         *
         * @param bill
         * @return
         */
        private boolean checkIsMealOutAndPay(Bill bill) {
            boolean isMealOut = StringUtils.equals(bill.getIsMealOut(), "Y");
            boolean isPaid = StringUtils.equals(bill.getIsPaid(), "Y");
            if (isMealOut && isPaid) {
                return true;
            } else {
                return false;
            }
        }

        private void setBuyBtnShow(final BillSon son, Button button) {
            if (son.getBill().getIsPaid().equals("Y")) {
                button.setTextColor(Color.GRAY);
            } else {
                button.setTextColor(Color.RED);
            }

            button.setText("$" + son.getBill().getDollar());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("買單");
                    builder.setNegativeButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            son.getBill().setIsPaid("Y");
                            new Update2PayAsyTask(son.getBill().getTxId(), getActivity()).execute();
                            notifyDataSetChanged();
                        }
                    });
                    builder.setPositiveButton("取消", null);
                    builder.create().show();

                }
            });
        }

        class GroupViewHolder {
            private TextView titile;
            private Button buyBillBtn;
            private LinearLayout meals;
            private Button closeBtn;
        }

        class ItemHolder {
            private TextView titile;
            private Button toFoodBtn;
            private Button reduceNumBtn;


        }
    }


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemListFragment() {
    }

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    private BroadcastReceiver mUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ItemListFragment.this.broadcastReceived(intent);
        }
    };

    @Override
    public void onPause() {

        super.onPause();
        getActivity().unregisterReceiver(mUpdateReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addCategory("com.food.foodpos");
        intentFilter.addAction("com.google.android.c2dm.intent.RECEIVE");
        intentFilter.addAction("com.google.android.c2dm.intent.REGISTRATION");

        intentFilter.setPriority(Integer.MAX_VALUE);

        getActivity().registerReceiver(this.mUpdateReceiver, intentFilter);
    }

    public void broadcastReceived(Intent intent) {
        Bundle extras = intent.getExtras();
        String mes = extras.getString("message");

        Log.d(TAG, "GET GOOGLE MESSAGe=" + mes);

        if (StringUtils.equals(mes, "A001")) {
            this.loadUnSpeakOutBillBtn.setVisibility(View.VISIBLE);
        }


    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bill_layout, container, false);


        this.gridView = (ListView) rootView.findViewById(R.id.gridView);
        this.unGridView = (ListView) rootView.findViewById(R.id.unGridView);
        this.loadUnSpeakOutBillBtn = (TextView) rootView.findViewById(R.id.loadUnSpeakOutBillBtn);
        this.showMeaage = (TextView) rootView.findViewById(R.id.showMeaage);
        this.loadUnSpeakOutBillBtn.setVisibility(View.GONE);
        this.loadBtn = (ImageButton) rootView.findViewById(R.id.loadBtn);


        this.workAdapter = new WorkAdapter(itemListDTO.getAddList());
        this.gridView.setAdapter(this.workAdapter);

        this.unWorkAdapter = new UnWorkAdapter(itemListDTO.getUnAddList());
        this.unGridView.setAdapter(this.unWorkAdapter);

        /**
         *
         */
        this.loadUnSpeakOutBillBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadUnSpeakOutBillBtn.setVisibility(View.GONE);
                LoadNoSpeakOutBillTask loadNoSpeakOutBillTask = new LoadNoSpeakOutBillTask(getActivity());
                loadNoSpeakOutBillTask.setRestAsyTaskListener(new GenericuRestTask.RestAsyTaskListener<JsonBill>() {
                    @Override
                    public void message(RestResultException e, JsonBill content) {
                        loadBtn.setVisibility(View.VISIBLE);
                        //
                        itemListDTO.getUnAddList().clear();
                        itemListDTO.getUnAddList().addAll(content.getContent());
                        unWorkAdapter.notifyDataSetChanged();
                    }
                });
                loadNoSpeakOutBillTask.execute();
            }
        });


        /**
         * 重新查詢
         */
        this.loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadBtn.setVisibility(View.GONE);
                LoadNoSpeakOutBillTask loadNoSpeakOutBillTask = new LoadNoSpeakOutBillTask(getActivity());
                loadNoSpeakOutBillTask.setRestAsyTaskListener(new GenericuRestTask.RestAsyTaskListener<JsonBill>() {
                    @Override
                    public void message(RestResultException e, JsonBill content) {
                        loadBtn.setVisibility(View.VISIBLE);
                        //
                        itemListDTO.getUnAddList().clear();
                        itemListDTO.getUnAddList().addAll(content.getContent());
                        unWorkAdapter.notifyDataSetChanged();
                    }
                });
                loadNoSpeakOutBillTask.execute();
            }
        });

        itemListDTO.getAddList().clear();
        itemListDTO.getAddList().addAll(toFoodService.queryNowWorkList(itemListDTO, getActivity()));


        workAdapter.notifyDataSetChanged();
        this.setShowMeaage();

        return rootView;
    }

    private void setShowMeaage() {


        final List<FoddNo> foddNos = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();
        for (BillSon son : itemListDTO.getAddList()) {
            for (Meal meal : son.getMeals()) {

                if (!StringUtils.equals(meal.getUseNumber(), meal.getNumber())) {

                    String food = meal.getName();
                    if (StringUtils.isNotBlank(meal.getSpcialize())) {
                        food += "[" + meal.getSpcialize() + "]";
                    }
                    final FoddNo foodNo = new FoddNo();
                    foodNo.setFood(food);

                    int noToFoodNum = Integer.parseInt(meal.getNumber()) - Integer.parseInt(meal.getUseNumber());
                    if (foddNos.contains(foodNo)) {
                        int indexFoodNo =
                                foddNos.indexOf(foddNos);
                        final FoddNo nowFood = foddNos.get(indexFoodNo);
                        nowFood.setNo(nowFood.getNo() + noToFoodNum);
                    } else {
                        foodNo.setNo(noToFoodNum);
                        foodNo.setFood(food);
                        foddNos.add(foodNo);
                    }


                }
            }
            Collections.sort(foddNos);
        }

        StringBuffer showMessageBur = new StringBuffer();
        for (FoddNo foddNo : foddNos) {
            showMessageBur.append("[" + foddNo.getNo() + "]" + foddNo.food + "\n");

        }
        showMeaage.setText(showMessageBur.toString());


    }

    private class FoddNo implements Comparable<FoddNo> {
        private String food;
        private int no;

        public String getFood() {
            return food;
        }

        public void setFood(String food) {
            this.food = food;
        }


        @Override
        public boolean equals(Object o) {
            FoddNo ob = (FoddNo) o;
            if (this.food.equals(ob.getFood())) {
                return true;
            } else {
                return false;
            }
        }

        public void setNo(int no) {
            this.no = no;
        }

        @Override
        public int hashCode() {
            return 1;
        }

        public int getNo() {
            return no;
        }

        @Override
        public int compareTo(FoddNo foddNo) {
            return foddNo.getFood().compareTo(this.getFood());
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = sDummyCallbacks;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }


}
