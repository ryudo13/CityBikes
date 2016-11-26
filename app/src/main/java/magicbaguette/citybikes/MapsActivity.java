package magicbaguette.citybikes;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sdoward.rxgooglemap.MapObservableProvider;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

	private GoogleMap mMap;
	private MapObservableProvider mapObservableProvider;

	//---> Views
	@BindView(R.id.sliding_layout)
	SlidingUpPanelLayout sliding_layout;
	@BindView(R.id.tv_sliding_content)
	TextView tv_sliding_content;
	//<--- Views

	//---> Actions
	Action1 markerClickAction = new Action1<Marker>() {
		@Override
		public void call(Marker marker) {
			sliding_layout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);

		}
	};
	Action1 mapClickAction = new Action1<LatLng>() {
		@Override
		public void call(LatLng latLng) {
			sliding_layout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);


		}
	};
	//<--- Actions

	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */
	private GoogleApiClient client;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps);
		ButterKnife.bind(this);

		init();
		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
	}

	private void init() {
		// Obtain the SupportMapFragment and get notified when the map is ready to be used.
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);
		mapObservableProvider = new MapObservableProvider(mapFragment);
		sliding_layout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
	}


	/**
	 * Manipulates the map once available.
	 * This callback is triggered when the map is ready to be used.
	 * This is where we can add markers or lines, add listeners or move the camera. In this case,
	 * we just add a marker near Sydney, Australia.
	 * If Google Play services is not installed on the device, the user will be prompted to install
	 * it inside the SupportMapFragment. This method will only be triggered once the user has
	 * installed Google Play services and returned to the app.
	 */
	@Override
	public void onMapReady(GoogleMap googleMap) {
		initMap(googleMap);
		addMarkers();


	}
	
	private void initMap(GoogleMap map) {
		mMap = map;
		mapObservableProvider.getMarkerClickObservable().subscribe(markerClickAction);
		mapObservableProvider.getMapClickObservable().subscribe(mapClickAction);
	}
	
	private void addMarkers() {
		ButterKnife.bind(this);

		// Add a marker in Belgium and move the camera
		LatLng belgium = new LatLng(50.5039, 4.4699);
		mMap.addMarker(new MarkerOptions().position(belgium)).setTitle("Bikes in Belgium");
		mMap.moveCamera(CameraUpdateFactory.newLatLng(belgium));
	}


	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */
	public Action getIndexApiAction() {
		Thing object = new Thing.Builder()
				.setName("Maps Page") // TODO: Define a title for the content shown.
				// TODO: Make sure this auto-generated URL is correct.
				.setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
				.build();
		return new Action.Builder(Action.TYPE_VIEW)
				.setObject(object)
				.setActionStatus(Action.STATUS_TYPE_COMPLETED)
				.build();
	}

	@Override
	public void onStart() {
		super.onStart();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client.connect();
		AppIndex.AppIndexApi.start(client, getIndexApiAction());
	}

	@Override
	public void onStop() {
		super.onStop();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		AppIndex.AppIndexApi.end(client, getIndexApiAction());
		client.disconnect();
	}
}
