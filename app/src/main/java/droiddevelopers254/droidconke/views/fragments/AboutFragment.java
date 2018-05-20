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

        return view;
    }

}
