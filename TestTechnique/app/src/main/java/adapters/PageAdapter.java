package adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lelivrescolaire.testtechnique.R;
import com.lelivrescolaire.testtechnique.activity.PageActivity;
import com.squareup.picasso.Picasso;

import io.realm.RealmList;
import models.Page;
import models.RealmBook;
import models.RealmPage;

/**
 * Created by MagicMirouf on 04/04/16.
 * todo setHeightItem vs ScreenSize
 */
public class PageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private RealmList<RealmPage> pages;
    private Context context;

    public PageAdapter(RealmList<RealmPage> pages, Context context) {
        this.pages = pages;
        this.context = context;
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_title;
        private ImageView iv_cover;
        private View main_view;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.page_adapter_title);
            iv_cover = (ImageView) itemView.findViewById(R.id.page_adapter_cover);
            main_view = itemView;
        }

        public void setDatas(final RealmPage page){
            tv_title.setText(page.getTitle());
            Picasso.with(context).load(page.getCover()).into(iv_cover);
            main_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PageActivity.class);
                    intent.putExtra(PageActivity.PAGE_ID,page.getId());
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.page_adapter_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).setDatas(pages.get(position));
    }

    @Override
    public int getItemCount() {
        return pages.size();
    }
}
