package subham.sinha.dev.trimurl.ui.home;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Dns;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import subham.sinha.dev.trimurl.R;
import subham.sinha.dev.trimurl.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
//        binding.fab.setOnClickListener(view -> {
//            Intent i=new Intent().setAction(Intent.ACTION_VIEW).setData(Uri.parse("https://github.com/subhamsinhadev"));
//            startActivity(i);
//
//        });

        binding.shorts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String url_input=binding.input.getText().toString().trim().toLowerCase();
                if(url_input.startsWith("http")&&!url_input.isEmpty()){
                    ShortUrl(url_input);
                    binding.shorts.setEnabled(false);
                    binding.loader.setVisibility(View.VISIBLE);
                    binding.loader.playAnimation();
                }
                else {
                    Snackbar.make(view,"Enter a valid url !!", Snackbar.LENGTH_SHORT).show();
                }



            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public  void  ShortUrl(String url){
        //creatinh client
        OkHttpClient client =new OkHttpClient();
        client.newBuilder().dns(Dns.SYSTEM).build();
        //builiding body of the post method
        FormBody body=new FormBody.Builder().add("url",url).build();
        //post method
        Request request=new Request.Builder().url("https://cleanuri.com/api/v1/shorten").post(body).build() ;
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.loader.cancelAnimation();
                        binding.loader.setVisibility(View.GONE);

                        //anim

                        FrameLayout container = new FrameLayout(requireContext());
                        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        );
                        int padding = (int) (20 * requireContext().getResources().getDisplayMetrics().density);
                        container.setPadding(padding, padding, padding, padding);
                        container.setLayoutParams(params);

                        // Create LottieAnimationView programmatically
                        LottieAnimationView lottieView = new LottieAnimationView(requireContext());
                        FrameLayout.LayoutParams lottieParams = new FrameLayout.LayoutParams(
                                (int) (150 * requireContext().getResources().getDisplayMetrics().density),
                                (int) (150 * requireContext().getResources().getDisplayMetrics().density)
                        );
                        lottieView.setLayoutParams(lottieParams);
                        lottieParams.gravity = Gravity.CENTER;

                        // Set your animation resource (make sure you have a .json in res/raw/)
                        lottieView.setAnimation(R.raw.failed);  // Replace with your animation file name
                        lottieView.loop(true);
                        lottieView.playAnimation();

                        // Add LottieAnimationView to container
                        container.addView(lottieView);


                        new MaterialAlertDialogBuilder(requireContext())
                                .setTitle("Short Link Generation Failed :(")
                                .setMessage(e.getMessage().toString())
                                .setView(container)
                                .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        String url_input=binding.input.getText().toString().trim();
                                        ShortUrl(url_input);
                                        binding.shorts.setEnabled(false);
                                    }
                                })
                                .setNeutralButton("Close", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                })
                                .show();
                        binding.shorts.setEnabled(true);
                    }
                });


            }


            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){

                    String data=response.body().string();
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            binding.loader.cancelAnimation();
                           binding.loader.setVisibility(View.GONE);
                            try {


                                JSONObject json=new JSONObject(data);
                                String url= json.getString("result_url");


                                binding.shorts.setEnabled(true);
                                FrameLayout container = new FrameLayout(requireContext());
                                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                                        ViewGroup.LayoutParams.WRAP_CONTENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT
                                );
                                int padding = (int) (20 * requireContext().getResources().getDisplayMetrics().density);
                                container.setPadding(padding, padding, padding, padding);
                                container.setLayoutParams(params);

                                // Create LottieAnimationView programmatically
                                LottieAnimationView lottieView = new LottieAnimationView(requireContext());
                                FrameLayout.LayoutParams lottieParams = new FrameLayout.LayoutParams(
                                        (int) (150 * requireContext().getResources().getDisplayMetrics().density),
                                        (int) (150 * requireContext().getResources().getDisplayMetrics().density)
                                );
                                lottieView.setLayoutParams(lottieParams);
                                lottieParams.gravity = Gravity.CENTER;

                                // Set your animation resource (make sure you have a .json in res/raw/)
                                lottieView.setAnimation(R.raw.complete);  // Replace with your animation file name
                                lottieView.loop(true);
                                lottieView.playAnimation();

                                // Add LottieAnimationView to container
                                container.addView(lottieView);



                                new MaterialAlertDialogBuilder(requireContext())
                                        .setTitle("Short Link Generated Succesfully:)")
                                        //.setMessage(url)
                                        .setView(container

                                        )
                                        .setPositiveButton("Copy To Clipboard", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                ClipboardManager cm=(ClipboardManager) requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
                                                cm.setText(url);
//                                                View parentview=findViewById(R.id.main);

                                                Snackbar.make(binding.getRoot(), "Short Link Copied To ClipBoard", Snackbar.LENGTH_SHORT).show();

                                            }
                                        })
                                        .setNeutralButton("Preview And Copy", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                ClipboardManager cm=(ClipboardManager) requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
                                                cm.setText(url);
//                                                View parentview=findViewById(R.id.main);

                                                Snackbar.make(binding.main, "Short Link Copied To ClipBoard", Snackbar.LENGTH_SHORT).show();

                                                Intent intent=new Intent().setAction(Intent.ACTION_VIEW).setData(Uri.parse(url));
                                                startActivity(intent);

                                            }
                                        })

                                        .setCancelable(false)
                                        .show();
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });


                }

            }
        });
}}