package com.example.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.DatabaseMetaData;

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker OtherDeviceMarker;
    private DatabaseReference locationRef;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationRef = FirebaseDatabase.getInstance().getReference().child("ubicaciones");

        locationRef.orderByKey().limitToLast(1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Obtener la ubicación actualizada del otro dispositivo
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String locationString = childSnapshot.getValue(String.class);
                    if (locationString != null && !locationString.isEmpty()) {
                        String[] coordinates = locationString.split(", ");
                        if (coordinates.length == 2) {
                            try {
                                Double lat = Double.parseDouble(coordinates[0]);
                                Double lng = Double.parseDouble(coordinates[1]);
                                if (lat != null && lng != null) {
                                    LatLng newLocation = new LatLng(lat, lng);
                                    // Actualizar la posición del marcador del otro dispositivo en el mapa
                                    try {
                                        if (OtherDeviceMarker != null) {
                                            OtherDeviceMarker.setPosition(newLocation);
                                        } else {
                                            OtherDeviceMarker = mMap.addMarker(new MarkerOptions()
                                                    .position(newLocation)
                                                    .title("Ubicación del otro dispositivo"));
                                        }
                                    }catch (Exception e){
                                        Log.e("MapsFragment", "Error al actualizar marcador");
                                    }
                                }
                            } catch (NumberFormatException e) {
                                Log.e("MapsFragment", "Error al convertir coordenadas: " + e.getMessage());
                            }
                        } else {
                            Log.e("MapsFragment", "Formato de coordenadas incorrecto: " + locationString);
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("MapsFragment", "Error al recibir la ubicación: " + error.getMessage());
            }
        });
    }

    Object[][] recycleCenters = new Object[][]{
            {"Recicladora de Papel S.A. de C.V.", 28.652667679251763, -106.06601643311855, 1},
            {"Fierro Viejo y Cartón", 28.651163955122623, -106.06340708182734, 2},
            {"Tecnologías de Reciclado", 28.69310993368951, -106.10049722528184, 3},
            {"Kalisch Recycling 1", 28.69491690242366, -106.11835000809594, 4},
            {"Kalisch Recycling 2", 28.612909810559174, -106.0886353927554, 5},
            {"Chatarra 1", 28.61897953067645, -106.07839638848286, 6},
            {"Metales Reciclados", 28.61837522160605, -106.0535792554501, 7},
            {"Kalisch Recycling 3", 28.615145327276956, -106.02185731779988, 8},
            {"Chatarra 2", 28.596759045805445, -106.02958207959446, 9},
            {"Reduce A.C", 28.609376603882655, -106.00517038009228, 10},
            {"Charly chatarra", 28.60769968975533, -106.00333372893131, 11},
            {"Comercializadora de Chatarra", 28.607171991951837, -105.99951844252341, 12},
            {"RECHISA", 28.606933358189092, -105.9992622004532, 13},
            {"Chatarra La Junta Sucursal Fuentes Mares", 28.60576538160158, -105.99782453645254, 14},
            {"Reciclaje Romero", 28.605068357128907, -105.99784599412419, 15},
            {"Recicladora Lom Moya", 28.606845744164094, -105.9654603300531, 16},
            {"Reci Pet", 28.671043558104014, -106.0645863284326, 17},
            {"Cajas Recicladas Chihuahua", 28.653754700042487, -106.0150956164143, 18},
            {"Recilogic Planta Chihuahua", 28.64597261424409, -106.00583801632348, 19}
    };
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;


        // Verifica si tienes permiso para acceder a la ubicación
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Si no tienes permiso, solicítalo al usuario
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // Si tienes permiso, muestra la ubicación actual
            mMap.setMyLocationEnabled(true);
            LatLng chihuahua = new LatLng(28.6674057, -106.0576012);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(chihuahua, 11.75f));

            Drawable drawable = getResources().getDrawable(R.drawable.trash_truck);

// Convert the VectorDrawable to a Bitmap
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);

// Scale down the Bitmap
            Bitmap smallMarkerBuses = Bitmap.createScaledBitmap(bitmap, 75, 75, false);

// Create a BitmapDescriptor from the scaled Bitmap
            BitmapDescriptor iconBuses = BitmapDescriptorFactory.fromBitmap(smallMarkerBuses);

// Add the marker with the scaled icon
            OtherDeviceMarker = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(0, 0)) // Initialize latitude and longitude to 0
                    .title("Ubicación del otro dispositivo")
                    .icon(iconBuses));

            try {
                boolean success = mMap.setMapStyle(
                        MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.map_style));

                if (!success) {
                    Log.e("MapsFragment", "Fallo al aplicar el estilo del mapa.");
                }
            } catch (Resources.NotFoundException e) {
                Log.e("MapsFragment", "No se pudo encontrar el archivo de estilo. Error: ", e);
            }

            BitmapDescriptor iconCenters = BitmapDescriptorFactory.fromResource(R.drawable.recycle);
            int width = 75; // Ancho deseado en píxeles
            int height = 75; // Alto deseado en píxeles
            Bitmap bitmapCenters = ((BitmapDrawable) getResources().getDrawable(R.drawable.recycle)).getBitmap();
            Bitmap smallMarkerCenters = Bitmap.createScaledBitmap(bitmapCenters, width, height, false);
            iconCenters = BitmapDescriptorFactory.fromBitmap(smallMarkerCenters);

            for (int i = 0; i < recycleCenters.length; i++) {
                String centerName = (String) recycleCenters[i][0];
                double latitude = (double) recycleCenters[i][1];
                double longitude = (double) recycleCenters[i][2];

                LatLng centersLocation = new LatLng(latitude, longitude);
                mMap.addMarker(new MarkerOptions().position(centersLocation).title(centerName).icon(iconCenters));
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        return view;
    }
}
