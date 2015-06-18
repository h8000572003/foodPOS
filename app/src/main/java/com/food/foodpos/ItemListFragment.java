package com.food.foodpos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.food.db.domainType.Meal;
import com.food.foodpos.dto.BillSon;
import com.food.foodpos.dto.JsonBill;
import com.food.foodpos.util.BillAsyTask;
import com.food.foodpos.util.Update2PayAsyTask;
import com.food.foodpos.util.gcm.GenericuRestTask;
import com.food.foodpos.util.gcm.RestResultException;

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
    private JsonBill jsonBill = null;

    private BillSon nowWorkingList=null;;

    private ListView listView = null;
    private ListView unListView = null;


    private GridView gridView;
    private GridView unGridView;

    private boolean isTable = true;//是平板

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
        if (e == null) {
            this.jsonBill = content;


            Log.e(TAG, "loadinng fail e:", e);
        } else {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
        }


        this.billRestAsyTask = null;

        this.listView.setAdapter(new MyAdapter());

    }

    private void load() {
        if (this.billRestAsyTask == null) {
            this.billRestAsyTask = new BillAsyTask();
        }
        this.billRestAsyTask.setUrl("/bill/query/unBuy/today");
        this.billRestAsyTask.setRestAsyTaskListener(this);
        this.billRestAsyTask.execute();
    }
    private class UnWorkAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }

    private class MyAdapter extends BaseAdapter {
        private LayoutInflater mChildInflater;

        public MyAdapter() {
            mChildInflater = (LayoutInflater) getActivity()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return jsonBill.getContent().size();
        }

        @Override
        public Object getItem(int position) {
            return jsonBill.getContent().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            GroupViewHolder holder = null;
            View view = convertView;

            view = mChildInflater.inflate(R.layout.bill_title_layout, parent, false);
            holder = new GroupViewHolder();
            holder.titile = (TextView) view.findViewById(R.id.title);
            holder.buyBillBtn = (Button) view.findViewById(R.id.buyBillBtn);
            holder.meals = (LinearLayout) view.findViewById(R.id.linearLayout);
            view.setTag(holder);


            final BillSon son =
                    jsonBill.getContent().get(position);

            //"[外/內][001][井邊]夫妻 [$500]"
            StringBuffer buffer = new StringBuffer();


            if (son.getBill().getOutOrIn().equals("O")) {
                buffer.append("[外帶]");
            } else {
                buffer.append("[內用]");
            }

            buffer.append("[" + son.getBill().getSeat() + "]");


            if (!son.getBill().getFeature().equals("")) {
                buffer.append("[" + son.getBill().getFeature() + "]");
            }

            holder.titile.setText(buffer);
            if (son.getBill().getIsPaid().equals("Y")) {
                holder.buyBillBtn.setTextColor(Color.GRAY);
            } else {

                holder.buyBillBtn.setTextColor(Color.RED);
            }
            holder.buyBillBtn.setText("$" + son.getBill().getDollar());

            holder.buyBillBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("買單");
                    builder.setNegativeButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            son.getBill().setIsPaid("Y");
                            new Update2PayAsyTask(son.getBill().getTxId()).execute();
                            notifyDataSetChanged();
                        }
                    });
                    builder.setPositiveButton("取消", null);
                    builder.create().show();

                }
            });

            for (Meal meal : son.getMeals()) {
                TextView textView = new TextView(getActivity());
                textView.setText(meal.getName());

                View addView = mChildInflater.inflate(R.layout.bill_item_layout, parent, false);

                ItemHolder itemHolder = new ItemHolder();
                itemHolder.titile = (TextView) addView.findViewById(R.id.foodItem);
                itemHolder.toFoodBtn = (Button) addView.findViewById(R.id.toFoodBtn);
                itemHolder.reduceNumBtn = (Button) addView.findViewById(R.id.reduceNumBtn);
                addView.setTag(itemHolder);


                StringBuffer itemName = new StringBuffer();
                itemName.append("[");
                itemName.append(meal.getNumber());
                itemName.append("]");
                itemName.append(meal.getName());
                if (!meal.getSpcialize().equals("")) {
                    itemName.append("(");
                    itemName.append(meal.getSpcialize());
                    itemName.append(")");
                }

                itemHolder.titile.setText(itemName.toString());
                itemHolder.reduceNumBtn.setText(meal.getNumber());

                holder.meals.addView(addView);


            }

            return view;
        }

        class GroupViewHolder {
            private TextView titile;
            private Button buyBillBtn;
            private LinearLayout meals;
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
        load();

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.bill_layout, container, false);


        this.gridView = (GridView) rootView.findViewById(R.id.gridView);
        this.unGridView = (GridView) rootView.findViewById(R.id.unGridView);

        this.listView = (ListView) rootView.findViewById(R.id.listView);
        this.unListView = (ListView) rootView.findViewById(R.id.unListView);

        if (this.gridView != null) {
            this.isTable = true;
        } else {
            this.isTable = false;
        }


        return rootView;
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
