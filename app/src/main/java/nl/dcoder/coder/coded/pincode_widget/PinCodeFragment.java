/*
Author: Garik Hakopian
Version: 1.0
Github: https://github.com/GHakopian/Android_Pincode_widget/tree/initialSetup
Licence: Apache 2.0
 */

package nl.dcoder.coder.coded.pincode_widget;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class PinCodeFragment extends Fragment{
    public final String TAG = "PinCodeFragment";
    public interface PinCodeListener{
        void onPinCodeConfirm(String pinCode);
    }

    private String pinCode;
    private String confirmCode;
    private CheckBox digit1;
    private CheckBox digit2;
    private CheckBox digit3;
    private CheckBox digit4;
    private CheckBox digit5;
    private TextView errorBox;
    private PinCodeListener mListener;
    private boolean confirmMode;
    public boolean newPinCodeMode;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (PinCodeListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement PinCodeListener");
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (PinCodeListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement PinCodeListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pin_code, container, false);
        rootView.setTag(TAG);
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button btn = (Button)view;
                if(pinCode.length() < 5){
                    String digit = btn.getText().toString();
                    pinCode += digit;
                    updateDigits();
                }
            }
        };
        errorBox = (TextView)rootView.findViewById(R.id.errorBox);
        Button btn1 = (Button)rootView.findViewById(R.id.num1);
        Button btn2 = (Button)rootView.findViewById(R.id.num2);
        Button btn3 = (Button)rootView.findViewById(R.id.num3);
        Button btn4 = (Button)rootView.findViewById(R.id.num4);
        Button btn5 = (Button)rootView.findViewById(R.id.num5);
        Button btn6 = (Button)rootView.findViewById(R.id.num6);
        Button btn7 = (Button)rootView.findViewById(R.id.num7);
        Button btn8 = (Button)rootView.findViewById(R.id.num8);
        Button btn9 = (Button)rootView.findViewById(R.id.num9);
        Button btn0 = (Button)rootView.findViewById(R.id.num0);
        Button deleteBtn = (Button)rootView.findViewById(R.id.deleteBtn);
        digit1 = (CheckBox)rootView.findViewById(R.id.digit1);
        digit2 = (CheckBox)rootView.findViewById(R.id.digit2);
        digit3 = (CheckBox)rootView.findViewById(R.id.digit3);
        digit4 = (CheckBox)rootView.findViewById(R.id.digit4);
        digit5 = (CheckBox)rootView.findViewById(R.id.digit5);

        btn1.setOnClickListener(clickListener);
        btn2.setOnClickListener(clickListener);
        btn3.setOnClickListener(clickListener);
        btn4.setOnClickListener(clickListener);
        btn5.setOnClickListener(clickListener);
        btn6.setOnClickListener(clickListener);
        btn7.setOnClickListener(clickListener);
        btn8.setOnClickListener(clickListener);
        btn9.setOnClickListener(clickListener);
        btn0.setOnClickListener(clickListener);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pinCode.length() > 0){
                    pinCode = pinCode.substring(0, pinCode.length()-1);
                    updateDigits();
                }
            }
        });

        return rootView;
    }

    private void updateDigits(){
        if(pinCode.length() == 0){
            digit1.setChecked(false);
            digit2.setChecked(false);
            digit3.setChecked(false);
            digit4.setChecked(false);
            digit5.setChecked(false);
        }
        if(pinCode.length() == 1){
            digit1.setChecked(true);
            digit2.setChecked(false);
            digit3.setChecked(false);
            digit4.setChecked(false);
            digit5.setChecked(false);
        }
        else if(pinCode.length() == 2){
            digit1.setChecked(true);
            digit2.setChecked(true);
            digit3.setChecked(false);
            digit4.setChecked(false);
            digit5.setChecked(false);
        }
        else if(pinCode.length() == 3){
            digit1.setChecked(true);
            digit2.setChecked(true);
            digit3.setChecked(true);
            digit4.setChecked(false);
            digit5.setChecked(false);
        }
        else if(pinCode.length() == 4){
            digit1.setChecked(true);
            digit2.setChecked(true);
            digit3.setChecked(true);
            digit4.setChecked(true);
            digit5.setChecked(false);
        }
        else if(pinCode.length() == 5){
            digit1.setChecked(true);
            digit2.setChecked(true);
            digit3.setChecked(true);
            digit4.setChecked(true);
            digit5.setChecked(true);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(newPinCodeMode){
                        if(!confirmMode){
                            confirmMode = true;
                            confirmCode = pinCode;
                            clearPinCode();
                            reportMessage("Please repeat the pin code.");
                        }else{
                            if(confirmCode.equals(pinCode)){
                                if(mListener != null){mListener.onPinCodeConfirm(pinCode);}
                            }else{
                                reportMessage("Pin codes did not match\n please try again.");
                                resetConfirm();
                            }
                        }
                    }else{
                        if(mListener != null){mListener.onPinCodeConfirm(pinCode);}
                    }
                }
            }, 150);

        }
    }
    public void resetConfirm(){
        confirmCode = "";
        confirmMode = false;
        clearPinCode();
        updateDigits();
    }

    public void clearPinCode(){
        pinCode = "";
        updateDigits();
    }

    public void reportMessage(String message){
        errorBox.setVisibility(View.VISIBLE);
        errorBox.setText(message);
        errorBox.setAlpha(0);
        errorBox.animate().alpha(1).setDuration(200);
        clearPinCode();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pinCode = "";
        Bundle args = getArguments();
        if (args != null) {

        }
    }
}