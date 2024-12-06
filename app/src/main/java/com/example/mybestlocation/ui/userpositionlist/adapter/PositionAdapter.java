package com.example.mybestlocation.ui.userpositionlist.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.Manifest;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.example.mybestlocation.DatabaseHelper;
import com.example.mybestlocation.Position;
import com.example.mybestlocation.R;
import com.google.android.gms.location.LocationServices;

import java.util.List;

public class PositionAdapter extends RecyclerView.Adapter<PositionAdapter.PositionViewHolder> {

    private List<Position> positionList;
    private Context context;
    private DatabaseHelper databaseHelper;
    private FusedLocationProviderClient fusedLocationClient;
    public PositionAdapter(Context context, List<Position> positionList) {
        this.context = context;
        this.positionList = positionList;
        this.databaseHelper = new DatabaseHelper(context);
        this.fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }

    @NonNull
    @Override
    public PositionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_position, parent, false);
        return new PositionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PositionViewHolder holder, int position) {
        Position pos = positionList.get(position);
        holder.pseudoTextView.setText(pos.getPseudo());
        holder.phoneNumberTextView.setText(pos.getNumber());
        holder.latitudeTextView.setText(pos.getLatitude());
        holder.longitudeTextView.setText(pos.getLongitude());

        // Set actions for edit, delete, and send SMS
        holder.editButton.setOnClickListener(v -> editPosition(pos));
        holder.deleteButton.setOnClickListener(v -> deletePosition(pos.getIdPosition()));
        holder.smsButton.setOnClickListener(v -> sendSMS(pos.getNumber(), pos.getLongitude(), pos.getLatitude()));
    }

    @Override
    public int getItemCount() {
        return positionList.size();
    }

    private void editPosition(Position position) {
        // Implement editing functionality
        // You can either show a dialog to edit details or navigate to another fragment to edit
    }

    private void deletePosition(int positionId) {
        // Delete the position from the database
        databaseHelper.deletePosition(positionId);
        // Refresh the list after deletion
        positionList.removeIf(position -> position.getIdPosition() == positionId);
        notifyDataSetChanged();
    }

    /* private void sendSMS(String phoneNumber, String longitude, String latitude) {
        // Send SMS to the phone number
       /* Intent smsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phoneNumber));
        smsIntent.putExtra("sms ", "Here is my postion : Longitude," + longitude + "Latitude:" + latitude);
        context.startActivity(smsIntent);*/
        /*if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
        /*fusedLocationClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        String currentLongitude = String.valueOf(location.getLongitude());
                        String currentLatitude = String.valueOf(location.getLatitude());

                        String message = "Here is my current position:\n" +
                                "Longitude: " + currentLongitude + "\n" +
                                "Latitude: " + currentLatitude;

                        Intent smsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phoneNumber));
                        smsIntent.putExtra("sms_body", message);
                        context.startActivity(smsIntent);
                    } else {
                        // Handle the case where location is null (e.g., location is disabled)
                        String fallbackMessage = "Unable to retrieve location. " +
                                "Last known position:\n" +
                                "Longitude: " + longitude + "\n" +
                                "Latitude: " + latitude;

                        Intent smsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phoneNumber));
                        smsIntent.putExtra("sms_body", fallbackMessage);
                        context.startActivity(smsIntent);
                    }
                });*/

            /*fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(location -> {
                        if (location != null) {
                            String currentLongitude = String.valueOf(location.getLongitude());
                            String currentLatitude = String.valueOf(location.getLatitude());

                            // Génération du lien Google Maps
                            String mapsLink = "https://www.google.com/maps?q=" + currentLatitude + "," + currentLongitude;

                            // Message contenant le lien
                            String message = "Here is my current position: " + mapsLink;

                            // Intent pour envoyer un SMS
                            Intent smsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phoneNumber));
                            smsIntent.putExtra("sms_body", message);
                            context.startActivity(smsIntent);
                        } else {
                            // Si la localisation actuelle n'est pas disponible, utilisez les coordonnées sauvegardées
                            String mapsLink = "https://www.google.com/maps?q=" + latitude + "," + longitude;
                            String fallbackMessage = "Unable to retrieve current location. Last known position: " + mapsLink;

                            Intent smsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phoneNumber));
                            smsIntent.putExtra("sms_body", fallbackMessage);
                            context.startActivity(smsIntent);
                        }
                    });

    }*/

    private void sendSMS(String phoneNumber, String longitude, String latitude) {
        // Générer un lien Google Maps basé sur les coordonnées de l'utilisateur
        String mapsLink = "https://www.google.com/maps?q=" + latitude + "," + longitude;

        // Message contenant la position
        String message = "Here is the user's position: " + mapsLink;

        // Intent pour envoyer un SMS
        Intent smsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phoneNumber));
        smsIntent.putExtra("sms_body", message);
        context.startActivity(smsIntent);
    }


    public static class PositionViewHolder extends RecyclerView.ViewHolder {

        TextView pseudoTextView, phoneNumberTextView, latitudeTextView, longitudeTextView;
        Button editButton, deleteButton, smsButton;

        public PositionViewHolder(@NonNull View itemView) {
            super(itemView);
            pseudoTextView = itemView.findViewById(R.id.pseudoTextView);
            phoneNumberTextView = itemView.findViewById(R.id.phoneNumberTextView);
            latitudeTextView = itemView.findViewById(R.id.latitudeTextView);
            longitudeTextView = itemView.findViewById(R.id.longitudeTextView);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            smsButton = itemView.findViewById(R.id.smsButton);
        }
    }
}
