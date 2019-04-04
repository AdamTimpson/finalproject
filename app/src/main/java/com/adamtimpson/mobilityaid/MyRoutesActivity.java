package com.adamtimpson.mobilityaid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.adamtimpson.mobilityaid.database.entry.RouteEntry;
import com.adamtimpson.mobilityaid.database.model.Route;
import com.adamtimpson.mobilityaid.util.ActivityUtils;
import com.adamtimpson.mobilityaid.util.LogInUtils;

import java.util.List;

public class MyRoutesActivity extends AppCompatActivity {

    private RouteEntry routeEntry = new RouteEntry(this);

    private List<Route> routesList;
    private String[] routeNames;

    public static Route selectedRoute = null;

    private ListView routesListView;

    private ActivityUtils activityUtils = new ActivityUtils(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_routes);

        routesList = routeEntry.getRoutesByUserId(LogInUtils.getInstance().getCurrentUser().getId());

        routeNames = getRouteNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, routeNames);

        routesListView = (findViewById(R.id.routesListView));
        routesListView.setAdapter(adapter);
        routesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                List<Route> routes = routeEntry.getRoutesByUserId(LogInUtils.getInstance().getCurrentUser().getId());
                Route route = null;
                for(Route r: routes) {
                    String selectedItem = routesListView.getItemAtPosition(position).toString();
                    Integer routeId = Integer.parseInt(selectedItem.substring(1, 2));

                    if(r.getId() == routeId) {
                        Route selectedRoute = routeEntry.getRoute(routeId);
                        MyRoutesActivity.selectedRoute = selectedRoute;

                        activityUtils.moveToMaps();
                    }
                }
            }
        });
    }

    private String[] getRouteNames() {
        String[] s = new String[routesList.size()];
        for(int i = 0; i < routesList.size(); i ++) {
            s[i] = ("#" + routesList.get(i).getId() + ": " + routesList.get(i).getName());
        }

        return s;
    }

}
