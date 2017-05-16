package id.sch.smktelkom_mlg.privateassignment.xirpl616.tugaspribadi;

/**
 * Created by Khofi Muffin on 15/05/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

public class Page3Adapter extends RecyclerView.Adapter<Page3Adapter.ViewHolder> {


    private List<Page3ListItem> page3ListItems;
    private Context context;

    public Page3Adapter(List<Page3ListItem> page3ListItems, Context context) {
        this.page3ListItems = page3ListItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.page_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Page3ListItem page3ListItem = page3ListItems.get(position);

        holder.textViewTitle.setText(page3ListItem.getTitle());
        holder.textViewContent.setText(page3ListItem.getContent());

        Glide
                .with(context)
                .load("http://image.tmdb.org/t/p/w500" + page3ListItem.getImageUrl())
                .into(holder.imageViewOtOf);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, page3ListItem.getTitle() + " selected", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(context, PageDetail3.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.putExtra("blog_id", position);
                context.startActivity(intent1);
            }
        });

    }

    @Override
    public int getItemCount() {
        return page3ListItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewTitle;
        public TextView textViewContent;
        public ImageView imageViewOtOf;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewTitle = (TextView) itemView.findViewById(R.id.textViewTitle);
            textViewContent = (TextView) itemView.findViewById(R.id.textViewContent);
            imageViewOtOf = (ImageView) itemView.findViewById(R.id.imageViewOtOf);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.LinearLayout);

        }
    }
}