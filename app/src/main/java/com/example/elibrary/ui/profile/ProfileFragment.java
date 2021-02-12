package com.example.elibrary.ui.profile;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.elibrary.R;
import com.example.elibrary.models.User;
import com.example.elibrary.ui.library.LibraryViewModel;
import com.example.elibrary.ui.single.Constants;
import com.example.elibrary.ui.single.SingleApplBarLayout;
import com.example.elibrary.ui.single.SingleCollapsingToolbar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    List<String> list = new ArrayList<>();

    private RecyclerView recyclerView;
    private ViewPager2 mLibraryViewPager;
    private LibraryViewModel libraryViewModel;
    private ProfileViewModel profileViewModel;
    private User user;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        float heightDp = getResources().getDimensionPixelSize(R.dimen.app_bar_layout_height_max_phone);
        SingleApplBarLayout.getInstance().getAppBarLayout().getLayoutParams().height = (int)heightDp;

        //set user data to profile view
        user = User.getInstance();
        SingleCollapsingToolbar.getInstance().getCollapsingToolbarLayout().setTitle(user.name);
        Uri imgUri=Uri.parse(String.valueOf(user.profileImage));
        //SingleCollapsingToolbar.getInstance().getToolbarImageView().setImageURI(null);
        //SingleCollapsingToolbar.getInstance().getToolbarImageView().setImageURI(imgUri);
        Picasso.with(getContext()).load(user.profileImage).into(SingleCollapsingToolbar.getInstance().getToolbarImageView());

        list.add("shedule 1");
        list.add("shedule 2");
        list.add("shedule 3");
        list.add("shedule 4");
        list.add("shedule 5");
        list.add("shedule 6");
        list.add("shedule 7");
        list.add("shedule 8");

        recyclerView = root.findViewById(R.id.profile_recycler_view_1_collection);
        GridLayoutManager mLayoutManager = new GridLayoutManager(root.getContext(), 2);
        //new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL,false)
        recyclerView.setLayoutManager(mLayoutManager);
        CollectionAdaptor collectionAdaptor = new CollectionAdaptor(root.getContext(), list);
        recyclerView.setAdapter(collectionAdaptor);

        return root;
    }

}

class CollectionAdaptor extends RecyclerView.Adapter<CollectionAdaptor.ViewHolder>{

    private Context context;
    private List<String> sheduleList = new ArrayList();

    public CollectionAdaptor(Context context, List<String> sheduleList) {
        this.context = context;
        this.sheduleList = sheduleList;
    }

    @NonNull
    @Override
    public CollectionAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.library_single_row, parent, false);
        CollectionAdaptor.ViewHolder viewHolder = new CollectionAdaptor.ViewHolder(view);

        Constants constants = Constants.getInstance();

        view.getLayoutParams().width = constants.screenWidthInPx/2 - (int) (constants.density * constants.defaultMarginInDp*2 + 0.5f);
        view.getLayoutParams().height = (int) (constants.density * constants.cardDefaultheightInDp + 0.5f);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CollectionAdaptor.ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return sheduleList.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder{

        ConstraintLayout row;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}