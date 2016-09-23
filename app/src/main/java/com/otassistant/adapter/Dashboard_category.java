package com.otassistant.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.innodroid.expandablerecycler.ExpandableRecyclerAdapter;
import com.otassistant.R;
import com.otassistant.activities.Donate_Blood_Activity;
import com.otassistant.activities.Splash_Blood_compatibility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ankit on 19/09/16.
 */
public class Dashboard_category extends ExpandableRecyclerAdapter<Dashboard_category.PeopleListItem> {
    private Context context;
    public static final int TYPE_PERSON = 1001;

    public Dashboard_category(Context context) {
        super(context);
        this.context = context;

        setItems(getSampleItems());
    }

    public static class PeopleListItem extends ExpandableRecyclerAdapter.ListItem {
        public String Text;

        public PeopleListItem(String group) {
            super(TYPE_HEADER);

            Text = group;
        }

        public PeopleListItem(String first, String last) {
            super(TYPE_PERSON);

            Text = first + " " + last;
        }
    }

    public class HeaderViewHolder extends ExpandableRecyclerAdapter.HeaderViewHolder {
        TextView name;

        public HeaderViewHolder(View view) {
            super(view, (ImageView) view.findViewById(R.id.item_arrow));

            name = (TextView) view.findViewById(R.id.item_header_name);
        }

        public void bind(int position) {
            super.bind(position);

            name.setText(visibleItems.get(position).Text);
        }
    }

    public class PersonViewHolder extends ExpandableRecyclerAdapter.ViewHolder {
        TextView name;

        public PersonViewHolder(View view) {
            super(view);

            name = (TextView) view.findViewById(R.id.item_name);
            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (name.getText().equals("Request Blood")) {
                        Toast.makeText(context, name.getText(), Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(context, Splash_Blood_compatibility.class);
                        context.startActivity(i);
                    }
                    if (name.getText().equals("Donate Blood")) {
                        Toast.makeText(context, name.getText(), Toast.LENGTH_SHORT).show();
                       Intent i = new Intent(context, Donate_Blood_Activity.class);
                        context.startActivity(i);
                    }
                    if (name.getText().equals("Tips To Stay Healthy")) {
                        Toast.makeText(context, name.getText(), Toast.LENGTH_SHORT).show();
                       /* Intent i = new Intent(context, .class);
                        context.startActivity(i);*/
                    }
                    if (name.getText().equals("Configure Now")) {
                        Toast.makeText(context, name.getText(), Toast.LENGTH_SHORT).show();
                       /* Intent i = new Intent(context, .class);
                        context.startActivity(i);*/
                    }
                    if (name.getText().equals("Ask Our Doc Bots")) {
                        Toast.makeText(context, name.getText(), Toast.LENGTH_SHORT).show();
                       /* Intent i = new Intent(context, .class);
                        context.startActivity(i);*/
                    }
                    if (name.getText().equals("Medicine Search")) {
                        Toast.makeText(context, name.getText(), Toast.LENGTH_SHORT).show();
                       /* Intent i = new Intent(context, .class);
                        context.startActivity(i);*/
                    }
                    if (name.getText().equals("Trending Diseases")) {
                        Toast.makeText(context, name.getText(), Toast.LENGTH_SHORT).show();
                       /* Intent i = new Intent(context, .class);
                        context.startActivity(i);*/
                    }
                    if (name.getText().equals("Health Reports")) {
                        Toast.makeText(context, name.getText(), Toast.LENGTH_SHORT).show();
                       /* Intent i = new Intent(context, .class);
                        context.startActivity(i);*/
                    }

                }
            });
        }

        public void bind(int position) {
            name.setText(visibleItems.get(position).Text);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEADER:
                return new HeaderViewHolder(inflate(R.layout.dashboard_item_header, parent));
            case TYPE_PERSON:
            default:
                return new PersonViewHolder(inflate(R.layout.dashboard_subcategory_list, parent));
        }
    }

    @Override
    public void onBindViewHolder(ExpandableRecyclerAdapter.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                ((HeaderViewHolder) holder).bind(position);
                break;
            case TYPE_PERSON:
            default:
                ((PersonViewHolder) holder).bind(position);
                break;
        }
    }

    private List<PeopleListItem> getSampleItems() {
        List<PeopleListItem> items = new ArrayList<>();

        items.add(new PeopleListItem("Banks"));
        items.add(new PeopleListItem("Request", "Blood"));
        items.add(new PeopleListItem("Donate", "Blood"));
        items.add(new PeopleListItem("Request", "Organ"));
        items.add(new PeopleListItem("Donate", "Organ"));
        items.add(new PeopleListItem("Tips To", "Stay Healthy"));
        items.add(new PeopleListItem("Help Me! M In Trouble"));
        items.add(new PeopleListItem("Configure", "Now"));
        items.add(new PeopleListItem("Find Solutions"));
        items.add(new PeopleListItem("Ask Our", "Doc Bots")); //chat room
        items.add(new PeopleListItem("Medicine", "Search")); //Ask Disease
        items.add(new PeopleListItem("Keep Me Updated"));
        items.add(new PeopleListItem("Trending", "Diseases")); // fetch place =>  search and populate
        items.add(new PeopleListItem("Health", "Reports")); // fetch place =>  search and populate
        return items;
    }
}