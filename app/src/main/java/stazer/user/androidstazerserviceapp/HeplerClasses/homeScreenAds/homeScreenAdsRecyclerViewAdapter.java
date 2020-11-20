package stazer.user.androidstazerserviceapp.HeplerClasses.homeScreenAds;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import stazer.user.androidstazerserviceapp.R;

public class homeScreenAdsRecyclerViewAdapter extends RecyclerView.Adapter<homeScreenAdsRecyclerViewAdapter.HomeScreenAdViewHolder> {
    ArrayList<homeScreenAdsRecyclerViewHelperClass> homeScreenAds;

    public homeScreenAdsRecyclerViewAdapter(ArrayList<homeScreenAdsRecyclerViewHelperClass> homeScreenAds) {
        this.homeScreenAds = homeScreenAds;
    }

    @NonNull
    @Override
    public HomeScreenAdViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_screen_ads_recycler_cardview, parent, false);
        HomeScreenAdViewHolder homeScreenAdViewHolder = new HomeScreenAdViewHolder(view);
        return homeScreenAdViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull HomeScreenAdViewHolder holder, int position) {
        homeScreenAdsRecyclerViewHelperClass homeScreenAdsRecyclerViewHelperClass = homeScreenAds.get(position);
        holder.adimage.setImageResource(homeScreenAdsRecyclerViewHelperClass.getAdImage());
        holder.adTitle.setText(homeScreenAdsRecyclerViewHelperClass.getAdTitle());
        holder.adRate1.setText(homeScreenAdsRecyclerViewHelperClass.getAdRate1());
        holder.adRate2.setText(homeScreenAdsRecyclerViewHelperClass.getAdRate2());



    }

    @Override
    public int getItemCount() {
        return homeScreenAds.size();
    }


    public static class HomeScreenAdViewHolder extends RecyclerView.ViewHolder {

        ImageView adimage;
        TextView adTitle, adRate1, adRate2;

        public HomeScreenAdViewHolder(@NonNull View itemView) {
            super(itemView);

            //Hooks
            adimage = itemView.findViewById(R.id.ads_image);
            adTitle = itemView.findViewById(R.id.cardTitleAds);
            adRate1 = itemView.findViewById(R.id.cardTitleRate1Ads);
            adRate2 = itemView.findViewById(R.id.cardTitleRate2Ads);

        }
    }

}
