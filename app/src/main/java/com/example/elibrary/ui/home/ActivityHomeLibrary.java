package com.example.elibrary.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.SearchView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
//import android.widget.SearchView;

import com.example.elibrary.R;
import com.example.elibrary.ui.single.Constants;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ActivityHomeLibrary extends AppCompatActivity {

    List<String> list = new ArrayList<>();

    private RecyclerView recyclerView;
    private Toolbar mToolbar;
    private LibraryActivityAdaptor collectionAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_library);

        Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.home_library_toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout collToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);

        //float heightDp = getResources().getDimensionPixelSize(R.dimen.app_bar_layout_height_max_phone);
        //SingleApplBarLayout.getInstance().getAppBarLayout().getLayoutParams().height = (int)heightDp;

        list.add("nimal 1");
        list.add("kamal 2");
        list.add("nimal 3");
        list.add("amara 4");
        list.add("kamal 5");
        list.add("anur 6");
        list.add("anura 7");
        list.add("dimal 8");
        list.add("nimal 1");
        list.add("kamal 2");
        list.add("nimal 3");
        list.add("amara 4");
        list.add("kamal 5");
        list.add("anur 6");
        list.add("anura 7");
        list.add("dimal 8");

        //toolbar
        //mToolbar = (Toolbar) findViewById(R.id.home_library_toolbar);
        //setActionBar(mToolbar);

        recyclerView = findViewById(R.id.home_library_recycler_view);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        //new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL,false)
        recyclerView.setLayoutManager(mLayoutManager);
        collectionAdaptor = new LibraryActivityAdaptor(getApplicationContext(), list);
        recyclerView.setAdapter(collectionAdaptor);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        MenuItem item = menu.findItem(R.id.search);

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                collectionAdaptor.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}


class LibraryActivityAdaptor extends RecyclerView.Adapter<LibraryActivityAdaptor.ViewHolder> implements Filterable {

    private Context context;
    private List<String> filteredList = new ArrayList();
    private List<String> listAll = new ArrayList();

    public LibraryActivityAdaptor(Context context, List<String> list) {
        this.context = context;
        this.filteredList = list;
        this.listAll = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.library_single_row, parent, false);
        LibraryActivityAdaptor.ViewHolder viewHolder = new LibraryActivityAdaptor.ViewHolder(view);

        Constants constants = Constants.getInstance();

        view.getLayoutParams().width = constants.screenWidthInPx/2 - (int) (constants.density * constants.defaultMarginInDp*2 + 0.5f);
        view.getLayoutParams().height = (int) (constants.density * constants.cardDefaultheightInDp + 0.5f);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LibraryActivityAdaptor.ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();

                if(charSequence==null||charSequence.length()==0){
                    filterResults.values = listAll;
                    filterResults.count = listAll.size();
                }else{
                    String searchStr = charSequence.toString().toLowerCase();
                    List<String> resultData= new ArrayList<>();

                    for (String  s: listAll) {
                        if(s.toLowerCase().contains(searchStr)){
                            filterResults.values=resultData.add(s);
                        }
                    }
                    filterResults.values = resultData;
                    filterResults.count = resultData.size();
                }

                Log.d("A", "Member last"+filterResults.values);
                return filterResults;

//                if (charSequence.toString().isEmpty()) {
//                    filteredList.addAll(listAll);
//                } else {
//                    for (String item: listAll) {
//                        if (item.toLowerCase().contains(charSequence.toString().toLowerCase())){
//                            filteredList.add(item);
//                            Log.d("Home", "test home publishResults "+ item);
//                        }
//                    }
//                }
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredList = (List<String>) filterResults.values;
                Log.d("A", "Member membersListFiltered"+filteredList);
                notifyDataSetChanged();
            }
        };
        return filter;
    }
    public class ViewHolder  extends RecyclerView.ViewHolder{

        ConstraintLayout row;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}