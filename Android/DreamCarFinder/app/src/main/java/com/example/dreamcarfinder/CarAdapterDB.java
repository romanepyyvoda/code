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
import com.example.dreamcarfinder.CarObjectsSet.CarEntity;


import java.util.List;

public class CarAdapterDB extends RecyclerView.Adapter<CarAdapterDB.ViewHolder> {
    // Member variables.
    private List<CarEntity> mCarsData;
    private Context mContext;

    /**
     * Constructor that passes in the cars data and the context.
     *
     * @param carsData ArrayList containing the cars data.
     * @param context Context of the application.
     */
    CarAdapterDB(Context context, List<CarEntity> carsData) {
        this.mCarsData = carsData;
        this.mContext = context;
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
    public CarAdapterDB.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false));

    }

    /**
     * Required method that binds the data to the viewholder.
     *
     * @param holder The viewholder into which the data should be put.
     * @param position The adapter position.
     */
    @Override
    public void onBindViewHolder(CarAdapterDB.ViewHolder holder,
                                 int position) {
        // Get current car.
        CarEntity currentCar = mCarsData.get(position);

        // Populate the textviews with data.
        holder.bindTo(currentCar);
    }

    /**
     * Required method for determining the size of the data set.
     *
     * @return Size of the data set.
     */
    @Override
    public int getItemCount() {
        if (mCarsData != null)
            return mCarsData.size();
        else return 0;
    }

    /**
     * Gets the word at a given position.
     * This method is useful for identifying which word
     * was clicked or swiped in methods that handle user events.
     *
     * @param position The position of the word in the RecyclerView
     * @return The word at the given position
     */
    public CarEntity getCarAtPosition(int position) {
        return mCarsData.get(position);
    }

    /**
     *     Associate a list of cars with this adapter
     * @param cars
     */
    void setCars(List<CarEntity> cars) {
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

        void bindTo(CarEntity currentCar){
            // Populate the textviews with data.
            mTitleText.setText(currentCar.getYear() + " " + currentCar.getMake() + " " + currentCar.getModel());
            mPriceText.setText(currentCar.getPrice());
            mLocationText.setText(currentCar.getLocation());

            // Load the images into the ImageView using the Glide library.
            Glide.with(mContext).load(currentCar.getImglink())
                    .error(R.drawable.default_car)
                    .into(mCarsImage);
        }

        /**
         * Handle click to show CarDescription.
         *
         * @param view View that is clicked.
         */
        @Override
        public void onClick(View view) {
            CarEntity currentCar = mCarsData.get(getAdapterPosition());
            Intent descriptionIntent = new Intent(mContext, FavouritesDescription.class);
            descriptionIntent.putExtra("id", currentCar.getId());
            descriptionIntent.putExtra("image_resource", currentCar.getImglink());
            descriptionIntent.putExtra("title", currentCar.getYear() + " " + currentCar.getMake() + " " + currentCar.getModel());
            descriptionIntent.putExtra("price",currentCar.getPrice());
            descriptionIntent.putExtra("location",currentCar.getLocation());
            descriptionIntent.putExtra("description",currentCar.getDescription());
            descriptionIntent.putExtra("link",currentCar.getWeblink());
            mContext.startActivity(descriptionIntent);
        }
    }
}
