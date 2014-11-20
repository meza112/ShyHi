package dev.rug.shyhi;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;



public class ViewHolderAdapter extends ArrayAdapter<MyObj> {

	private static String TAG = "RecycleAdapter";

	private Activity mContext;
	private LayoutInflater mInflater;

	public ViewHolderAdapter(Activity context, int textViewResourceId) {
		super(context, textViewResourceId);
		mContext = context;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	static class ViewHolder {
		TextView name;
		TextView longtext;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		Log.d(TAG, "position=" + position);

		ViewHolder holder;

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_item, parent, false);
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.longtext = (TextView) convertView
					.findViewById(R.id.longtext);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		MyObj data = getItem(position);
		holder.name.setText(data.name);
		holder.longtext.setText(data.longText);

		return convertView;

	}

}
