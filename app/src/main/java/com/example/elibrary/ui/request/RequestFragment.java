package com.example.elibrary.ui.request;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.elibrary.R;
import com.example.elibrary.ui.library.LibraryViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class RequestFragment extends Fragment {

    List<String> list = new ArrayList<>();

    private RecyclerView mRecyclerViewRequest;
    private RecyclerView mRecyclerViewSuggestion;
    private TabLayout mTabLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_request, container, false);

        list.add("shedule 1");
        list.add("shedule 2");
        list.add("shedule 3");
        list.add("shedule 4");
        list.add("shedule 5");
        list.add("shedule 6");
        list.add("shedule 7");
        list.add("shedule 8");

        mRecyclerViewRequest = root.findViewById(R.id.request_recycler_view_requests);
        mRecyclerViewRequest.setLayoutManager(new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL,false));
        RequestAdaptor adaptorRequest = new RequestAdaptor(root.getContext(), list);
        mRecyclerViewRequest.setAdapter(adaptorRequest);

        mRecyclerViewSuggestion = root.findViewById(R.id.request_recycler_view_suggestions);
        mRecyclerViewSuggestion.setLayoutManager(new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL,false));
        RequestAdaptor adaptorSuggestions = new RequestAdaptor(root.getContext(), list);
        mRecyclerViewSuggestion.setAdapter(adaptorSuggestions);

        mTabLayout = root.findViewById(R.id.request_tab_layout);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText()==root.getResources().getString(R.string.suggestions)) {
                    mRecyclerViewRequest.setVisibility(View.GONE);
                    mRecyclerViewSuggestion.setVisibility(View.VISIBLE);
                } else {
                    mRecyclerViewRequest.setVisibility(View.VISIBLE);
                    mRecyclerViewRequest.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return root;
    }

}

class RequestAdaptor extends RecyclerView.Adapter<RequestAdaptor.ViewHolder>{

    private Context context;
    private List<String> sheduleList = new ArrayList();

    public RequestAdaptor(Context context, List<String> sheduleList) {
        this.context = context;
        this.sheduleList = sheduleList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_single_row, parent, false);
        RequestAdaptor.ViewHolder viewHolder = new RequestAdaptor.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RequestAdaptor.ViewHolder holder, int position) {
        //holder.sheduleName.setText(sheduleList.get(position));
        //holder.row.setOnClickListener(new View.OnClickListener() {
        //@Override
        //public void onClick(View view) {
        //Intent intent = new Intent(view.getContext(), ActivityMemberEvents.class);
        //Bundle bundle = new Bundle();
        //intent.putExtras(bundle);
        //context.startActivity(intent);
        // }
        //});
    }

    @Override
    public int getItemCount() {
        return sheduleList.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder{

        TextView sheduleName;
        ConstraintLayout row;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //sheduleName = itemView.findViewById(R.id.);
            //row = itemView.findViewById(R.id.sheduleSingleRow);
        }
    }

}