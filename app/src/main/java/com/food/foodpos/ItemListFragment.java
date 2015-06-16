package com.food.foodpos;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.food.db.domainType.Meal;
import com.food.foodpos.dto.BillSon;
import com.food.foodpos.dto.JsonBill;
import com.food.foodpos.dto.RestObj;
import com.food.foodpos.dummy.DummyContent;
import com.food.foodpos.util.BillAsyTask;
import com.food.foodpos.util.gcm.GenericuRestTask;
import com.food.foodpos.util.gcm.RestAsyTaskListener;
import com.food.foodpos.util.gcm.RestResultException;

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

    private BillAsyTask billRestAsyTask = null;
    private JsonBill jsonBill = null;
    private ExpandableListView expandableListView;

    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;

    @Override
    public void message(RestResultException e, JsonBill content) {
        if (e == null) {
            this.jsonBill = content;
            Log.e(TAG, "loadinng fail e:", e);
        } else {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        this.billRestAsyTask = null;

        ExpandableAdapter billAdpter = new ExpandableAdapter(jsonBill.getContent());
        expandableListView.setAdapter(billAdpter);

    }


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


        this.load();
    }

    private void load() {
        if (this.billRestAsyTask == null) {
            this.billRestAsyTask = new BillAsyTask();
        }
        this.billRestAsyTask.setUrl("/bill/query/unBuy/today");
        this.billRestAsyTask.setRestAsyTaskListener(this);
        this.billRestAsyTask.execute();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.bill_layout, container, false);
        this.expandableListView = (ExpandableListView) rootView.findViewById(R.id.expandableListView);

        return rootView;
    }

    private class ExpandableAdapter extends BaseExpandableListAdapter {
        private List<BillSon> billSon = null;
        private LayoutInflater mChildInflater;
        private LayoutInflater mGroupInflater;

        public ExpandableAdapter(List<BillSon> billSon) {
            this.billSon = billSon;
            mChildInflater = (LayoutInflater) getActivity()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mGroupInflater = (LayoutInflater) getActivity()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getGroupCount() {
            return jsonBill.getContent().size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return 2;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return jsonBill.getContent().get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return jsonBill.getContent().get(groupPosition).getMeals().get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            GroupViewHolder holder = null;
            View view = convertView;
            if (null == view) {


                view = mGroupInflater.inflate(R.layout.bill_title_layout, parent, false);


                holder = new GroupViewHolder();
                holder.titile = (TextView) view.findViewById(R.id.title);
                holder.buyBillBtn = (Button) view.findViewById(R.id.buyBillBtn);

                view.setTag(holder);
            } else {
                holder = (GroupViewHolder) view.getTag();
            }


            final BillSon son =
                    jsonBill.getContent().get(groupPosition);

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
            buffer.append("[$" + son.getBill().getDollar() + "]");
            holder.titile.setText(buffer);
            return view;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ChildViewHolder holder = null;
            View view = convertView;
            if (null == view) {
                view = mChildInflater.inflate(R.layout.bill_item_layout, parent, true);

                holder = new ChildViewHolder();
                holder.name = (TextView) view.findViewById(R.id.foodItem);
                view.setTag(holder);
            } else {
                holder = (ChildViewHolder) view.getTag();
            }

            Meal meal =
                    jsonBill.getContent().get(groupPosition).getMeals().get(childPosition);


            StringBuffer buffer = new StringBuffer();

            buffer.append("<h2> " + meal.getNumber() + ":" + meal.getName() + "</h2>");

            if (!meal.getSpcialize().equals("")) {
                buffer.append("<font color=\"#FF0000\">(" + meal.getSpcialize() + ")</font>");
            }


            holder.name.setText(Html.fromHtml(buffer.toString()));
            return view;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        class GroupViewHolder {
            TextView titile;
            Button buyBillBtn;
        }

        class ChildViewHolder {
            TextView name;
            Button isFoodOut;
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
