package com.otassistant.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.otassistant.R;
import com.otassistant.activities.RequestBloodLast;
import com.otassistant.dto.Request_Blood_dto;

import java.util.List;

/**
 * Created by Ankit on 22/09/16.
 */
public class RequestBloodAdapter extends RecyclerView.Adapter<RequestBloodAdapter.requestViewHolder> {
    private Context context;

    List<Request_Blood_dto> data;

    public RequestBloodAdapter(Context context, List<Request_Blood_dto> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public requestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_row_request, parent, false);
        requestViewHolder holder = new requestViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RequestBloodAdapter.requestViewHolder holder, int position) {
        final Request_Blood_dto current = data.get(position);
        holder.name.setText("Name: "+current.getName());
        holder.date.setText("Date of Availability: "+current.getDate());
        holder.address.setText(current.getAddress());
        holder.btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, RequestBloodLast.class);
                i.putExtra("name",current.getName().toString());
                i.putExtra("address",current.getAddress().toString());
                i.putExtra("mobile",current.getMobile().toString());
                context.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class requestViewHolder extends RecyclerView.ViewHolder {
        TextView name, address, date;
        Button btnRequest;

        public requestViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            address = (TextView) itemView.findViewById(R.id.address);
            date = (TextView) itemView.findViewById(R.id.date);
            btnRequest = (Button) itemView.findViewById(R.id.request);

        }

    }
}
