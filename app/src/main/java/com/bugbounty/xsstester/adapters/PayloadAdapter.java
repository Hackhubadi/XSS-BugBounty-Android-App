package com.bugbounty.xsstester.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bugbounty.xsstester.R;
import com.bugbounty.xsstester.models.Payload;
import java.util.ArrayList;
import java.util.List;

public class PayloadAdapter extends RecyclerView.Adapter<PayloadAdapter.PayloadViewHolder> {

    private List<Payload> payloadList;
    private List<Payload> payloadListFull; // For search/filter
    private Context context;
    private OnPayloadClickListener listener;

    public interface OnPayloadClickListener {
        void onPayloadClick(Payload payload);
    }

    public PayloadAdapter(List<Payload> payloadList, Context context, OnPayloadClickListener listener) {
        this.payloadList = payloadList;
        this.payloadListFull = new ArrayList<>(payloadList);
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PayloadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_payload, parent, false);
        return new PayloadViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PayloadViewHolder holder, int position) {
        Payload payload = payloadList.get(position);
        
        holder.tvPayloadName.setText(payload.getName());
        holder.tvPayloadContent.setText(payload.getPayload());
        holder.tvPayloadCategory.setText(payload.getCategory());
        
        // Set category color
        int categoryColor = getCategoryColor(payload.getCategory());
        holder.tvPayloadCategory.setBackgroundColor(categoryColor);
        
        // Click listener
        holder.cardView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onPayloadClick(payload);
            }
        });
    }

    @Override
    public int getItemCount() {
        return payloadList.size();
    }

    public void filter(String query) {
        payloadList.clear();
        
        if (query.isEmpty()) {
            payloadList.addAll(payloadListFull);
        } else {
            String lowerCaseQuery = query.toLowerCase();
            for (Payload payload : payloadListFull) {
                if (payload.getName().toLowerCase().contains(lowerCaseQuery) ||
                    payload.getPayload().toLowerCase().contains(lowerCaseQuery) ||
                    payload.getCategory().toLowerCase().contains(lowerCaseQuery)) {
                    payloadList.add(payload);
                }
            }
        }
        notifyDataSetChanged();
    }

    private int getCategoryColor(String category) {
        switch (category) {
            case "Basic":
                return 0xFF4CAF50; // Green
            case "Advanced":
                return 0xFF2196F3; // Blue
            case "Bypass":
                return 0xFFFF9800; // Orange
            case "WAF Bypass":
                return 0xFFFF5722; // Deep Orange
            case "Polyglot":
                return 0xFF9C27B0; // Purple
            case "Cookie Steal":
                return 0xFFF44336; // Red
            case "DOM XSS":
                return 0xFF00BCD4; // Cyan
            case "Context":
                return 0xFF795548; // Brown
            case "mXSS":
                return 0xFFE91E63; // Pink
            default:
                return 0xFF9E9E9E; // Grey
        }
    }

    static class PayloadViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tvPayloadName;
        TextView tvPayloadContent;
        TextView tvPayloadCategory;

        public PayloadViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardViewPayload);
            tvPayloadName = itemView.findViewById(R.id.tvPayloadName);
            tvPayloadContent = itemView.findViewById(R.id.tvPayloadContent);
            tvPayloadCategory = itemView.findViewById(R.id.tvPayloadCategory);
        }
    }
}
