package brayan0428.androidgooglesheet.com.android_googlesheet.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import brayan0428.androidgooglesheet.com.android_googlesheet.POJOS.Users;
import brayan0428.androidgooglesheet.com.android_googlesheet.R;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {
    Context context;
    List<Users> users;
    public UsersAdapter(Context context, List<Users> usersList){
        this.context = context;
        this.users = usersList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {
        CardView cardView;
        TextView nameUser,emailUser;
        ImageView imgUser;
        public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.imageUser);
            nameUser = itemView.findViewById(R.id.nameUser);
            emailUser = itemView.findViewById(R.id.emailUser);
            imgUser = itemView.findViewById(R.id.imageUser);
        }
    }
}
