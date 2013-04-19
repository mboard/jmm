package com.droiddnamk.sharedrive;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;
import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;


public class ListView extends Activity implements OnItemClickListener {

	// Ova e test lista.. ke bide promeneta..

	private static ArrayList<String> items;
	static{
		items = new ArrayList<String>();
		
		items.add("Ajax Amsterdam");
		items.add("Barcelona");
		items.add("Manchester United");
		items.add("Chelsea");
		items.add("Real Madrid");
		items.add("Bayern Munchen");
		items.add("Internazionale");
		items.add("Valencia");
		items.add("Arsenal");
		items.add("AS Roma");
		items.add("Tottenham Hotspur");
		items.add("PSV");
		items.add("Olympique Lyon");
		items.add("AC Milan");
		items.add("Dortmund");
		items.add("Schalke 04");
		items.add("Twente");
		items.add("Porto");
		items.add("Juventus");
	}
	
	public PullToRefreshListView listView;
    private CustomAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        
        listView = (PullToRefreshListView) findViewById(R.id.pull_to_refresh_listview);

        listView.setLockScrollWhileRefreshing(true);

         listView.setShowLastUpdatedText(true);

         listView.setLastUpdatedDateFormat(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"));

        // listView.setTextPullToRefresh("Pull to Refresh");
        // listView.setTextReleaseToRefresh("Release to Refresh");
        // listView.setTextRefreshing("Refreshing");


        listView.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				
				listView.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						listView.onRefreshComplete();
					}
				}, 2000);
			}
		});
        
        adapter = new CustomAdapter(this, items);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
    	TextView temp = (TextView) view.findViewById(R.id.custom_text);
        Toast.makeText(this,temp.getText().toString(), Toast.LENGTH_SHORT).show();
    }
}
