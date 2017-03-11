package nl.dcoder.coder.coded.pincode_widget;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements PinCodeFragment.PinCodeListener {


    private PinCodeFragment pinCodeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pinCodeFragment = new PinCodeFragment();
        pinCodeFragment.newPinCodeMode = true;
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        //transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        transaction.add(R.id.loginFragContainer,pinCodeFragment);
        transaction.commit();
        //getFragmentManager().executePendingTransactions();

    }

    @Override
    public void onPinCodeConfirm(String pinCode) {
        new AlertDialog.Builder(this)
                .setTitle("Code Confirmed!")
                .setMessage("your code: "+pinCode)
                .setCancelable(false)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        pinCodeFragment.resetConfirm();
                    }
                }).show();
    }
}
