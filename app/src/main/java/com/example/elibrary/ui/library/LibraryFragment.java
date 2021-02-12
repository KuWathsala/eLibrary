package com.example.elibrary.ui.library;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.elibrary.R;

public class LibraryFragment extends Fragment {

    private LibraryViewModel libraryFragment;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        libraryFragment = ViewModelProviders.of(this).get(LibraryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_library, container, false);

        return root;
    }

}

/*
public class LibraryFragment extends Fragment {

    List<String> list = new ArrayList<>();

    private RecyclerView recyclerView;
    private ViewPager2 mLibraryViewPager;
    private LibraryViewModel libraryViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        libraryViewModel = ViewModelProviders.of(this).get(LibraryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_library, container, false);

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
        SheduleAdaptor adaptor = new SheduleAdaptor(root.getContext(), list);
        recyclerView.setAdapter(adaptor);

        recyclerView = root.findViewById(R.id.library_recycler_view_2);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL,false));
        SheduleAdaptor adaptor1 = new SheduleAdaptor(root.getContext(), list);
        recyclerView.setAdapter(adaptor1);

        //mLibraryViewPager = root.findViewById(R.id.library_view_pager);
        //mLibraryViewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        //mLibraryViewPager.setOffscreenPageLimit(3);
        //final float pageMargin = getResources().getDimensionPixelOffset(R.dimen.pageMargin);
        //final float pageOffset = getResources().getDimensionPixelOffset(R.dimen.offset);
        //mLibraryViewPager.setPageTransformer(new ViewPager2.PageTransformer() {
        //    @Override
        //    public void transformPage(@NonNull View page, float position) {
        //    }
        //});
        //SheduleAdaptor adaptor = new SheduleAdaptor(root.getContext(), list);
        //mLibraryViewPager.setAdapter(adaptor);

        return root;
    }

}

class SheduleAdaptor extends RecyclerView.Adapter<SheduleAdaptor.ViewHolder>{

    private Context context;
    private List<String> sheduleList = new ArrayList();

    public SheduleAdaptor(Context context, List<String> sheduleList) {
        this.context = context;
        this.sheduleList = sheduleList;
    }

    @NonNull
    @Override
    public SheduleAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.library_single_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SheduleAdaptor.ViewHolder holder, int position) {
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
*/