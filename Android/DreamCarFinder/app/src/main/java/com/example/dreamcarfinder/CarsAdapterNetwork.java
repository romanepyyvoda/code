package com.example.dreamcarfinder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dreamcarfinder.CarObjectsSet.Car;


import java.util.List;

public class CarsAdapterNetwork extends RecyclerView.Adapter<CarsAdapterNetwork.ViewHolder> {
    // Member variables.
    private List<Car> mCarsData;
    private Context mContext;
    private MethodCaller mCaller;

    /**
     * Constructor that passes in the cars data and the context.
     *
     * @param carsData ArrayList containing the cars data.
     * @param context Context of the application.
     */
    CarsAdapterNetwork(Context context, List<Car> carsData, final MethodCaller caller) {
        this.mCarsData = carsData;
        this.mContext = context;
        this.mCaller = caller;
    }


    /**
     * Required method for creating the viewholder objects.
     *
     * @param parent The ViewGroup into which the new View will be added
     *               after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return The newly created ViewHolder.
     */
    @Override
    public CarsAdapterNetwork.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false));

    }

    /**
     * Required method that binds the data to the viewholder.
     *
     * @param holder The viewholder into which the data should be put.
     * @param position The adapter position.
     */
    @Override
    public void onBindViewHolder(CarsAdapterNetwork.ViewHolder holder,
                                 int position) {
        // Get current car.
        Car currentCar = mCarsData.get(position);

        // Populate the textviews with data.
        holder.bindTo(currentCar);
    }

    /**
     * Required method for determining the size of the data set.
     *
     * @return Size of the data set.
     */
    @Override
    public int getItemCount(){
        if (mCarsData != null)
            return mCarsData.size();
        else return 0;
    }

    /**
     *     Associate a list of cars with this adapter
     */
    void setCars(List<Car> cars) {
        mCarsData = cars;
        notifyDataSetChanged();
    }

    /**
     * ViewHolder class that represents each row of data in the RecyclerView.
     */
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        // Member Variables for the TextViews
        private TextView mTitleText;
        private TextView mPriceText;
        private TextView mLocationText;
        private ImageView mCarsImage;

        /**
         * Constructor for the ViewHolder, used in onCreateViewHolder().
         *
         * @param itemView The rootview of the list_item.xml layout file.
         */
        ViewHolder(View itemView) {
            super(itemView);

            // Initialize the views.
            mTitleText = itemView.findViewById(R.id.car_year_make_model);
            mPriceText = itemView.findViewById(R.id.price);
            mLocationText = itemView.findViewById(R.id.location);
            mCarsImage = itemView.findViewById(R.id.carImage);

            // Set the OnClickListener to the entire view.
            itemView.setOnClickListener(this);
        }

        void bindTo(Car currentCar){
            // Populate the textviews with data.
            mTitleText.setText(currentCar.getBuild().getYear() + " " + currentCar.getBuild().getMake() + " " + currentCar.getBuild().getModel());
            mPriceText.setText(currentCar.getPrice());
            mLocationText.setText(currentCar.getLocation().toString());
            // Load the images into the ImageView using the Glide library.
            if(!currentCar.getMedia().getPhoto_links().isEmpty()){
                Glide.with(mContext).load(currentCar.getMedia().getPhoto_links().get(0))
                    .error(R.drawable.default_car)
                    .into(mCarsImage);
            }
            else{
                Glide.with(mContext).load(R.drawable.default_car)
                        .error(R.drawable.default_car)
                        .into(mCarsImage);
            }

        }

        /**
         * Handle click to show CarDescription.
         *
         * @param view View that is clicked.
         */
        @Override
        public void onClick(View view) {
            Car currentCar = mCarsData.get(getAdapterPosition());
            String imglink = "";
            if(!currentCar.getMedia().getPhoto_links().isEmpty()){
                imglink = currentCar.getMedia().getPhoto_links().get(0);
            }
            Intent descriptionIntent = new Intent(mContext, CarDescription.class);
            descriptionIntent.putExtra("image_resource", imglink);
            descriptionIntent.putExtra("title", currentCar.getBuild().getYear() + " " + currentCar.getBuild().getMake() + " " + currentCar.getBuild().getModel());
            descriptionIntent.putExtra("year",currentCar.getBuild().getYear());
            descriptionIntent.putExtra("make",currentCar.getBuild().getMake());
            descriptionIntent.putExtra("model",currentCar.getBuild().getModel());
            descriptionIntent.putExtra("price",currentCar.getPrice());
            descriptionIntent.putExtra("location",currentCar.getLocation().toString());
            descriptionIntent.putExtra("description",currentCar.getBuild().toString());
            descriptionIntent.putExtra("link",currentCar.getLink());
            mCaller.positionSaver();
            mContext.startActivity(descriptionIntent);
        }
    }
}
