package subham.sinha.dev.trimurl.ui.notifications;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import subham.sinha.dev.trimurl.R;
import subham.sinha.dev.trimurl.databinding.FragmentHomeBinding;
import subham.sinha.dev.trimurl.databinding.FragmentNotificationsBinding;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.getRoot().post(() -> binding.getRoot().scrollTo(0, 0));
        binding.getRoot().setNestedScrollingEnabled(true);
        BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(requireContext());
        binding.credits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Inflate the credits layout
                View view1 = LayoutInflater.from(requireContext()).inflate(R.layout.credits, null);

                // Find TextView from inflated view (not the dialog!)
                TextView creditsTextView = view1.findViewById(R.id.credits_body);

                // HTML-formatted credits
                String htmlCredits = "<p>This app was proudly built with the help of the following tools, libraries, and APIs:</p>" +
                        "<ul>" +
                        "<li><a href='https://cleanuri.com/docs'>CleanURI API</a> – URL shortening</li>" +
                        "<li><a href='https://square.github.io/okhttp/'>OkHttp</a> – Networking</li>" +
                        "<li><a href='https://m3.material.io/'>Material Design 3</a> – UI system</li>" +
                        "<li><a href='https://airbnb.io/lottie/#/'>Lottie</a> – Animations</li>" +
                        "</ul>" +
                        "<p><b>👨‍💻 Developer:</b> Subham Kumar Sinha<br/>" +
                        "<b>📬 Email:</b> <a href='mailto:subhamsinha9206@gmail.com'>subhamsinha9206@gmail.com</a><br/>" +
//                        "<b>🔗 Portfolio:</b> <a href='https://subham.dev'>https://subham.dev</a><br/>" +
                        "<b>🔧 Source Code:</b> <a href='https://github.com/'>GitHub</a></p>" +
                        "<p>❤️ Thanks to the open-source community!</p>";

                // Set HTML text with link support
                creditsTextView.setText(Html.fromHtml(htmlCredits, Html.FROM_HTML_MODE_LEGACY));
                creditsTextView.setMovementMethod(LinkMovementMethod.getInstance());

                // Now set the content view of the dialog
                bottomSheetDialog.setContentView(view1);
                bottomSheetDialog.show();
            }
        });

        binding.github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent().setAction(Intent.ACTION_VIEW).setData(Uri.parse("https://github.com/subhamsinhadev"));
                requireActivity().startActivity(i);
            }
        });

        binding.email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent().setAction(Intent.ACTION_SENDTO).setData(Uri.parse("mailto:subhamsinha9206@gmail.com"));
                requireActivity().startActivity(i);
            }
        });

        binding.linkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent().setAction(Intent.ACTION_VIEW).setData(Uri.parse("https://linkedin.com/in/subhamsinhadev"));
                requireActivity().startActivity(i);
            }
        });

        binding.twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent().setAction(Intent.ACTION_VIEW).setData(Uri.parse("https://x.com/subhamsinhadev"));
                requireActivity().startActivity(i);
            }
        });


        binding.insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent().setAction(Intent.ACTION_VIEW).setData(Uri.parse("https://instagram.com/its_subhamsinha"));
                requireActivity().startActivity(i);
            }
        });






        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}