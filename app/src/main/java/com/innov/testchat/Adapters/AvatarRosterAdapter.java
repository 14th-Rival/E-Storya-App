package com.innov.testchat.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.innov.testchat.DataModels.AvatarModels;
import com.innov.testchat.DataModels.SelectedAvatar;
import com.innov.testchat.Interfaces.onItemClick;
import com.innov.testchat.R;

import java.util.ArrayList;
import java.util.List;

public class AvatarRosterAdapter extends RecyclerView.Adapter<AvatarRosterAdapter.AvatarRosterViewholder> {

    public SelectedAvatar selectedAvatar;

    private List<AvatarModels> avatarModelsList = new ArrayList<>();
    private Context context;
    private int selectedItem;

    private onItemClick onItemClick;

    public void setOnItemClick(onItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public AvatarRosterAdapter(List<AvatarModels> avatarModelsList, Context context) {
        this.avatarModelsList = avatarModelsList;
        this.context = context;
        selectedItem = 0;
    }

    @NonNull
    @Override
    public AvatarRosterAdapter.AvatarRosterViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_avatar_cardview, parent, false);
        return new AvatarRosterViewholder(view, onItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull AvatarRosterAdapter.AvatarRosterViewholder holder, int position) {
        AvatarModels avatarModels = avatarModelsList.get(position);

        holder.imageView_avatar.setImageBitmap(avatarModels.getAvatar_image());

        if (selectedItem == holder.getAdapterPosition()) {
            holder.imageView_avatar.setBackground(ContextCompat.getDrawable(holder.imageView_avatar.getContext(), R.drawable.avatar_selected));


            // Pass item to the selected avatar constructor
            selectedAvatar = new SelectedAvatar(avatarModels.getAvatar_image());
        }

        else {
            holder.imageView_avatar.setBackground(ContextCompat.getDrawable(holder.imageView_avatar.getContext(), R.drawable.avatar_not_selected));
        }

        holder.itemView.setOnClickListener(v -> {

            int previousItem = selectedItem;
            selectedItem = holder.getAdapterPosition();

            notifyItemChanged(previousItem);
            notifyItemChanged(holder.getAdapterPosition());

        });

    }

    @Override
    public int getItemCount() {
        return avatarModelsList.size();
    }


    public static class AvatarRosterViewholder extends RecyclerView.ViewHolder {
        private ImageView imageView_avatar;
        public AvatarRosterViewholder(@NonNull View itemView, onItemClick onItemClick) {
            super(itemView);

            imageView_avatar = itemView.findViewById(R.id.imageView_sAvatar);

            itemView.setOnClickListener(v -> {
                if (onItemClick != null){
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        onItemClick.onItemClick(v, position);
                    }
                }
            });
        }
    }

}
