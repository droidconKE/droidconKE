package droiddevelopers254.droidconke.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.webkit.WebView;

import droiddevelopers254.droidconke.R;

public class OpenSourceLicencesUtil {
    public static void showOpenSourceLicenses(FragmentActivity activity) {
        FragmentManager fm = activity.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("dialog_licenses");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        new OpenSourceLicensesDialog().show(ft, "dialog_licenses");
    }
    public static class OpenSourceLicensesDialog extends DialogFragment {

        public OpenSourceLicensesDialog() {
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            WebView webView = new WebView(getActivity());
            webView.loadUrl("file:///android_asset/licenses.html");

            return new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.about_licenses)
                    .setView(webView)
                    .setPositiveButton(R.string.ok,
                            (dialog, whichButton) -> dialog.dismiss()
                    )
                    .create();
        }
    }

}
