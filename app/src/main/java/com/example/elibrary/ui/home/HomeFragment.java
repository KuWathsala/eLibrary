package com.example.elibrary.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.elibrary.R;
import com.example.elibrary.ui.library.LibraryViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    List<String> list = new ArrayList<>();

    //add beta
    private LibraryActivityAdaptor collectionAdaptor;

    private RecyclerView recyclerView;
    private ViewPager2 mLibraryViewPager;
    private LibraryViewModel libraryViewModel;
    private Button mBtnMore;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        libraryViewModel = ViewModelProviders.of(this).get(LibraryViewModel.class);
        //View root = inflater.inflate(R.layout.fragment_home, container, false);

        ActivityHomeLibrary activityHomeLibrary = new ActivityHomeLibrary();
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        /*

        list.add("shedule 1");
        list.add("shedule 2");
        list.add("shedule 3");
        list.add("shedule 4");
        list.add("shedule 5");
        list.add("shedule 6");
        list.add("shedule 7");
        list.add("shedule 8");

        recyclerView = root.findViewById(R.id.library_recycler_view_1);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL,false));
        LibraryAdaptor adaptorNew1 = new LibraryAdaptor(root.getContext(), list);
        recyclerView.setAdapter(adaptorNew1);

        recyclerView = root.findViewById(R.id.library_recycler_view_2);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL,false));
        LibraryAdaptor adaptorRecomond = new LibraryAdaptor(root.getContext(), list);
        recyclerView.setAdapter(adaptorRecomond);

        recyclerView = root.findViewById(R.id.library_recycler_view_3);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL,false));
        LibraryAdaptor adaptorTop = new LibraryAdaptor(root.getContext(), list);
        recyclerView.setAdapter(adaptorTop);

        //more button
        mBtnMore = root.findViewById(R.id.home_button_more);
        mBtnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ActivityHomeLibrary.class);
                Bundle bundle = new Bundle();
                intent.putExtras(bundle);
                getContext().startActivity(intent);
            }
        });

         */

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

        recyclerView = root.findViewById(R.id.home_library_recycler_view_beta);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        collectionAdaptor = new LibraryActivityAdaptor(getContext(), list);
        recyclerView.setAdapter(collectionAdaptor);

        setHasOptionsMenu(true);

        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.options_menu, menu);

        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("A", "onQueryTextChange "+newText);
                collectionAdaptor.getFilter().filter(newText);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu,inflater);
    }

//    class LibraryAdaptor extends RecyclerView.Adapter<LibraryAdaptor.ViewHolder>{
//
//        private Context context;
//        private List<String> list = new ArrayList();
//
//        public LibraryAdaptor(Context context, List<String> list) {
//            this.context = context;
//            this.list = list;
//        }
//
//        @NonNull
//        @Override
//        public LibraryAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.library_single_row, parent, false);
//            ViewHolder viewHolder = new ViewHolder(view);
//            return viewHolder;
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull LibraryAdaptor.ViewHolder holder, int position) {
//            //holder.sheduleName.setText(sheduleList.get(position));
//            holder.btnMore.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(view.getContext(), HomeBookDetailsActivity.class);
//                    Bundle bundle = new Bundle();
//                    intent.putExtras(bundle);
//                    context.startActivity(intent);
//                }});
//        }
//
//        @Override
//        public int getItemCount() {
//            return list.size();
//        }
//
//        public class ViewHolder  extends RecyclerView.ViewHolder{
//
//            TextView sheduleName;
//            Button btnMore;
//
//            public ViewHolder(@NonNull View itemView) {
//                super(itemView);
//                //sheduleName = itemView.findViewById(R.id.);
//                btnMore = itemView.findViewById(R.id.card_button_more);
//            }
//        }
//
//    }

}

