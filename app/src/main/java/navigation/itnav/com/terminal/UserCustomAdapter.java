package navigation.itnav.com.terminal;

/**
 * Created by IBRAHIM on 15/09/2016.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class UserCustomAdapter extends ArrayAdapter<UserItem> {
    UserItem[] list_values;
    Context ctx;
    public UserCustomAdapter(Context context, int resource, int textViewResourceId, UserItem[] objects) {
        super(context, resource, textViewResourceId, objects);
        list_values = objects;
        ctx = context;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.users_list_item, null);
            RadioButton r = (RadioButton)v.findViewById(R.id.usersRadioButton);
        }
        TextView tv = (TextView)v.findViewById(R.id.txtUsersItem);
        tv.setText(list_values[position].getText());
        RadioButton r = (RadioButton)v.findViewById(R.id.usersRadioButton);
        if(list_values[position].getHasRadioButton() == true){
            r.setVisibility(View.VISIBLE);
            r.setText(list_values[position].getRadioButtonText());
                /*r.setChecked(position == selectedPosition);
                r.setTag(position);
                r.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectedPosition = (Integer)view.getTag();
                        notifyDataSetChanged();
                    }
                });*/
        }
        else {
            r.setVisibility(View.INVISIBLE);
            RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.topMargin = 15;
            layoutParams.bottomMargin -= 35;
            tv.setLayoutParams(layoutParams);
        }
        return v;
    }
}
