package brayan0428.androidgooglesheet.com.android_googlesheet.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import brayan0428.androidgooglesheet.com.android_googlesheet.AddUserActivity;
import brayan0428.androidgooglesheet.com.android_googlesheet.Configuration.Variables;
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.nameUser.setText(users.get(position).getNames());
        holder.emailUser.setText(users.get(position).getEmail());
        Picasso.get()
                .load(users.get(position).getImage())
                //.placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .into(holder.imgUser);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddUserActivity.class);
                intent.putExtra("Id",users.get(position).getId());
                intent.putExtra("Names",users.get(position).getNames());
                intent.putExtra("LastNames",users.get(position).getLastNames());
                intent.putExtra("Address",users.get(position).getAddress());
                intent.putExtra("Email",users.get(position).getEmail());
                intent.putExtra("Image",users.get(position).getImage());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        holder.deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Esta seguro que desea eliminar el usuario?")
                        .setTitle("Confirmación")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                final RequestQueue request = Volley.newRequestQueue(v.getContext());
                                final ProgressDialog progressDialog = ProgressDialog.show(v.getContext(),"Eliminando Usuario","Espere un momento...",false,false);
                                StringRequest postRequest = new StringRequest(Request.Method.POST, Variables.URL_API, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        progressDialog.dismiss();
                                        users.remove(position);
                                        notifyDataSetChanged();
                                        Toast.makeText(context,"Eliminado exitosamente",Toast.LENGTH_LONG).show();
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        progressDialog.dismiss();
                                        Toast.makeText(context,error.toString(),Toast.LENGTH_LONG).show();
                                    }
                                }){
                                    @Override
                                    protected Map<String, String> getParams()
                                    {
                                        Map<String, String>  params = new HashMap<>();
                                        params.put("action","delete");
                                        params.put(Variables.ID,users.get(position).getId());
                                        return params;
                                    }
                                };
                                request.add(postRequest);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                builder.create();
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {
        CardView cardView;
        TextView nameUser,emailUser;
        ImageView deleteUser,imgUser;
        public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardUsers);
            nameUser = itemView.findViewById(R.id.nameUser);
            emailUser = itemView.findViewById(R.id.emailUser);
            deleteUser = itemView.findViewById(R.id.deleteUser);
            imgUser = itemView.findViewById(R.id.imageUser);
        }
    }
}
