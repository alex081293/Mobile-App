package cse.usf.edu.AutoPt;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.text.Editable;
import android.view.KeyEvent;

import cse.usf.edu.AutoPt.dummy.Menu;
import cse.usf.edu.AutoPt.MainActivity;
public class firstTime extends Fragment {
	
	
public firstTime() {
}

@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
   
}

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.firsttime, container, false);  
    final Button submitBtn = (Button) getView().findViewById(R.id.submitBtn);
    
    return rootView;
}
public void inputListener(){
final InputMethodManager imm =  (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
final EditText eText = (EditText) getView().findViewById(R.id.key);


eText.setOnEditorActionListener(new OnEditorActionListener() {

    @Override
    public boolean onEditorAction(TextView v, int keyCode, KeyEvent event) {
         if ( (event.getAction() == KeyEvent.ACTION_DOWN  ) && (keyCode == KeyEvent.KEYCODE_ENTER))
        {               
           // hide virtual keyboard
           imm.hideSoftInputFromWindow(eText.getWindowToken(), 0);
           return true;
        }
        return false;
    }
});
}
}