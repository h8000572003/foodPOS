package com.food.foodpos;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.food.ae.util.AeUtils;
import com.food.db.dao.BillDAOImpl;
import com.food.db.dao.FoodDAOImpl;
import com.food.db.domainType.Bill;
import com.food.db.domainType.Food;
import com.food.db.domainType.Meal;
import com.food.db.util.DBFactory;
import com.food.db.util.DBHelp;
import com.food.db.util.DBMain;
import com.food.foodpos.dto.BillAndMeals;
import com.food.foodpos.dto.MealDTO;
import com.food.foodpos.dummy.DummyContent;
import com.food.foodpos.util.CommonUtil;
import com.parse.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A list fragment representing a list of Items. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link ItemDetailFragment}.
 * <p/>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class ItemListFragment extends ListFragment {

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

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.insertFakeData();
        this.loadBills2UpateView();
    }

    private void insertFakeData() {
        DBMain dbMain =
                DBFactory.getColudeDB(getActivity());

        try {
            String txId = AeUtils.getNowDate() + AeUtils.getNowTime();
            dbMain.beginTransaction();


            Bill bill = new Bill();
            bill.setDollar("500");
            bill.setIsPaid("Y");
            bill.setIsMealOut("外帶");
            bill.setFeature("夫妻");

            bill.setOrderDate(AeUtils.getNowDate());
            bill.setOrderTime(AeUtils.getNowTime());
            bill.setSeat("井邊");
            bill.setTxId(txId);

            dbMain.insert(bill);


            Meal meal = new Meal();
            meal.setTxId(txId);
            meal.setDolloar("40");
            meal.setName("貢丸湯");
            meal.setSpcialize("不加香菜");
            meal.setNumber("4");

            Meal meal2 = new Meal();
            meal2.setTxId(txId);
            meal2.setDolloar("50");
            meal2.setName("排骨湯");
            meal2.setSpcialize("不加香菜");
            meal2.setNumber("2");

            dbMain.insert(meal);
            dbMain.insert(meal2);
            dbMain.setTransactionSuccessful();

        } finally {
            dbMain.endTransaction();
            dbMain.close();

        }
    }

    private void loadBills2UpateView() {
        DBMain dbMain =
                DBFactory.getColudeDB(getActivity());
        List<Bill> bills = dbMain.query(Bill.class, "select * from bill ", new String[]{});
        dbMain.close();
        new LoadData(bills).execute();
    }

    private class LoadData extends AsyncTask<Void, Void, Void> {

        private List<Bill> bills;
        private Map<Bill, List<Meal>> map = new HashMap<>();

        public LoadData(List<Bill> bills) {
            this.bills = bills;
        }

        @Override
        protected Void doInBackground(Void... params) {

            DBMain dbMain =
                    DBFactory.getColudeDB(getActivity());


            List<Bill> bills = dbMain.query(Bill.class, "", new String[]{});


            for (Bill bill : bills) {
                List<Meal> meals = dbMain.query(Meal.class, "select * from meal where txId=?", new String[]{bill.getTxId()});
                this.map.put(bill, meals);
            }
            dbMain.close();
            Log.i(TAG, "foods=" + bills.toString());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            BillAdpter adpter = new BillAdpter(getActivity());


            for (Map.Entry<Bill, List<Meal>> data : map.entrySet()) {
                adpter.addBillsAndMeal(data.getKey(), data.getValue());
            }
            setListAdapter(adpter);

            super.onPostExecute(aVoid);
        }
    }


    protected class MealAdapter extends BaseAdapter {
        private LayoutInflater li;
        private BillAndMeals billAndMeals;

        public MealAdapter(Context context, BillAndMeals billAndMeals) {
            this.li = LayoutInflater.from(context);
            this.billAndMeals = billAndMeals;
        }

        @Override
        public int getCount() {
            return billAndMeals.getMealList().size();
        }

        @Override
        public Object getItem(int position) {
            return billAndMeals.getMealList().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
            if (convertView == null) {

                convertView = li.inflate(R.layout.bill_item_layout, parent, false);
                holder = new ViewHolder();
                holder.foodItem = (TextView) convertView.findViewById(R.id.foodItem);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            MealDTO mealDTO = billAndMeals.getMealList().get(position);

            holder.foodItem.setText(String.format("[%d]%s",//
                    mealDTO.getMeal().getNumber(),//
                    mealDTO.getMeal().getSpcialize() + mealDTO.getMeal().getName()));//
            return convertView;
        }

        protected class ViewHolder {
            private TextView foodItem;


        }
    }

    protected class BillAdpter extends BaseAdapter {

        private LayoutInflater li;
        private List<BillAndMeals> billAndMealses = new ArrayList<>();
        private Context context;

        public BillAdpter(Context context) {
            this.context = context;
            this.li = LayoutInflater.from(context);

        }

        public BillAdpter addBillsAndMeal(Bill bill, List<Meal> meals) {

            BillAndMeals billAndMeals = new BillAndMeals();
            billAndMeals.setBill(bill);
            List<MealDTO> mealDTOs = new ArrayList<>();
            billAndMeals.setMealList(mealDTOs);


            for (Meal meal : meals) {
                mealDTOs.add(new MealDTO(meal));
            }


            this.billAndMealses.add(billAndMeals);
            return this;
        }

        @Override
        public int getCount() {
            return billAndMealses.size();
        }

        @Override
        public Object getItem(int position) {
            return this.billAndMealses.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
            if (convertView == null) {

                convertView = li.inflate(R.layout.bill_title_layout, parent, false);
                holder = new ViewHolder();
                holder.title = (TextView) convertView.findViewById(R.id.title);
                holder.buyBillBtn = (Button) convertView.findViewById(R.id.buyBillBtn);
                holder.orderBtn = (Button) convertView.findViewById(R.id.orderBtn);
                holder.toFoodBtn = (Button) convertView.findViewById(R.id.toFoodBtn);
                holder.listView = (ListView) convertView.findViewById(R.id.listView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            BillAndMeals meals = billAndMealses.get(position);
            Bill bill = meals.getBill();


            holder.getTitle().setText(String.format("[%s][%d][%s]%s [%s]",//
                    bill.getOutOrIn(),
                    bill.getId(),//
                    bill.getSeat(),///
                    bill.getFeature(),//
                    bill.getDollar()));//

            holder.buyBillBtn.setSelected(bill.getIsPaid().equals("Y") ? true : false);
            holder.orderBtn.setSelected(meals.isIoOrder());
            holder.toFoodBtn.setSelected(bill.getIsMealOut().equals("Y") ? true : false);
            holder.listView.setAdapter(new MealAdapter(context, meals));
            return convertView;
        }


        protected class ViewHolder {
            private TextView title;
            private Button buyBillBtn;
            private Button orderBtn;
            private Button toFoodBtn;
            private ListView listView;

            public TextView getTitle() {
                return title;
            }

            public void setTitle(TextView title) {
                this.title = title;
            }

            public Button getBuyBillBtn() {
                return buyBillBtn;
            }

            public void setBuyBillBtn(Button buyBillBtn) {
                this.buyBillBtn = buyBillBtn;
            }

            public Button getOrderBtn() {
                return orderBtn;
            }

            public void setOrderBtn(Button orderBtn) {
                this.orderBtn = orderBtn;
            }

            public Button getToFoodBtn() {
                return toFoodBtn;
            }

            public void setToFoodBtn(Button toFoodBtn) {
                this.toFoodBtn = toFoodBtn;
            }

            public ListView getListView() {
                return listView;
            }

            public void setListView(ListView listView) {
                this.listView = listView;
            }
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
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
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);

        // Notify the active callbacks interface (the activity, if the
        // fragment is attached to one) that an item has been selected.
        mCallbacks.onItemSelected(DummyContent.ITEMS.get(position).id);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }
}
