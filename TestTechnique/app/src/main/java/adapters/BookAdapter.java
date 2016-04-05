package adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lelivrescolaire.testtechnique.R;
import com.lelivrescolaire.testtechnique.activity.BookActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import io.realm.RealmList;
import models.Book;
import models.RealmBook;

/**
 * Created by MagicMirouf on 04/04/16.
 * todo setHeightItem vs ScreenSize
 */
public class BookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private RealmList<RealmBook> books;
    private Context context;

    public BookAdapter(RealmList<RealmBook> books, Context context) {
        this.books = books;
        this.context = context;
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_title;
        private ImageView iv_cover;
        private View main_view;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.book_adapter_title);
            iv_cover = (ImageView) itemView.findViewById(R.id.book_adapter_cover);
            main_view = itemView;
        }

        public void setDatas(final RealmBook book){
            tv_title.setText(book.getTitle());
            Picasso.with(context).load(book.getCover()).into(iv_cover);
            main_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, BookActivity.class);
                    intent.putExtra(BookActivity.BOOK_ID,book.getId());
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_adapter_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).setDatas(books.get(position));
    }

    @Override
    public int getItemCount() {
        return books.size();
    }
}
