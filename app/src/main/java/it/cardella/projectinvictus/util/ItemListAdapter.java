package it.cardella.projectinvictus.util;

import java.util.List;

import it.cardella.projectinvictus.R;
import it.cardella.projectinvictus.data.Item;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemListAdapter extends ArrayAdapter<Item> {

	private Context context;
	
	public ItemListAdapter(Context context, int resource, List<Item> objects) {
		super(context, resource, objects);
		this.context = context;
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.listview_row_layout, null);
		}
		
		Item item = getItem(position);
		if(item != null){
			
			TextView title = (TextView) view
					.findViewById(R.id.title_textView);
			TextView description = (TextView) view
					.findViewById(R.id.description_textView);
            ImageView image = (ImageView) view
                    .findViewById(R.id.imageView);

            // setto il titolo
			if (title != null) {
				title.setTextColor(context.getResources().getColor(R.color.DarkRed));
				title.setText(item.getTitle());
			}
            // setto la descrizione
			if (description != null) {
                String desc = item.getDescription();
                if(desc.length() < 197){
                    description.setText(Html.fromHtml(desc+"..."));
                }else{
				    description.setText(Html.fromHtml(item.getDescription().substring(0, 197)+"..."));
                }
			}
            // setto l'immagine
            if(image != null){
                Bitmap img = item.getImage();
                if(img != null){
                    image.setImageBitmap(img);
                }
            }
		}
		return view;
	}
}
