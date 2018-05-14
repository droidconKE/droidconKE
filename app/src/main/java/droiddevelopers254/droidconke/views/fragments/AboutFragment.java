package droiddevelopers254.droidconke.views.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.net.URI;

import droiddevelopers254.droidconke.R;
import droiddevelopers254.droidconke.utils.OpenSourceLicencesUtil;

public class AboutFragment extends Fragment{
    TextView faqText,termsOfServiceText,privacyPolicyText,openLicensesText;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        faqText=view.findViewById(R.id.faqText);
        termsOfServiceText=view.findViewById(R.id.termsOfServiceText);
        privacyPolicyText=view.findViewById(R.id.privacyPolicyText);
        openLicensesText=view.findViewById(R.id.openLicensesText);

        //open the links in browser
        faqText.setOnClickListener(view1 -> {
            openInBrowser(getString(R.string.faqs_link));
        });
        termsOfServiceText.setOnClickListener(view1 ->{
            openInBrowser(getString(R.string.terms_of_service_link));
        } );
        privacyPolicyText.setOnClickListener(view1 -> {
            openInBrowser(getString(R.string.privacy_policy_link));
        });

        openLicensesText.setOnClickListener(view1 -> {
            OpenSourceLicencesUtil.showOpenSourceLicenses(getActivity());
        });

        return view;
    }

    private void openInBrowser(String string) {
        Intent openInBrowserIntent = new Intent(Intent.ACTION_VIEW);
        openInBrowserIntent.setData(Uri.parse(string));
        startActivity(openInBrowserIntent);
    }
}
