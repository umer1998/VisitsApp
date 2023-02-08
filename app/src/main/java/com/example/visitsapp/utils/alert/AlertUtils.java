package com.example.visitsapp.utils.alert;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.visitsapp.R;
import com.victor.loading.rotate.RotateLoading;

public class AlertUtils {
    private static RotateLoading rotateLoading;

    public static void showAlert(final Context context, String message) {

        if(message.contains("caused connection abort") ||
                message.contains("Connection refused") ||
                message.contains("Failed to connect to") ){

            try {
                LayoutInflater li = LayoutInflater.from(context);
                View view = li.inflate(R.layout.dialog_title_text_layout, null);

                TextView textView = view.findViewById(R.id.tv_dialog_title);
                textView.setText("Alert!");
                TextView tvMessage = view.findViewById(R.id.tv_message);
                tvMessage.setText("Internet connection failed !");

                AlertDialog dialog = new AlertDialog.Builder(context, R.style.MyDialogTheme)
                        .setCustomTitle(view)
                        //  .setIcon(R.mipmap.ic_launcher)
                        //.setMessage(message)
                        .setCancelable(false)
                        //.setPositiveButton("Ok", null)
                        .show();

                Button button = view.findViewById(R.id.btn_ok);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();
                    }
                });

            } catch (WindowManager.BadTokenException e) {
                Log.e("ahmad", e.getMessage());
            }

        } else {
            try {
                LayoutInflater li = LayoutInflater.from(context);
                View view = li.inflate(R.layout.dialog_title_text_layout, null);

                TextView textView = view.findViewById(R.id.tv_dialog_title);
                textView.setText("Alert!");
                TextView tvMessage = view.findViewById(R.id.tv_message);
                tvMessage.setText(message);

                AlertDialog dialog = new AlertDialog.Builder(context, R.style.MyDialogTheme)
                        .setCustomTitle(view)
                        //  .setIcon(R.mipmap.ic_launcher)
                        //.setMessage(message)
                        .setCancelable(false)
                        //.setPositiveButton("Ok", null)
                        .show();

                Button button = view.findViewById(R.id.btn_ok);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();
                    }
                });

            } catch (WindowManager.BadTokenException e) {
                Log.e("ahmad", e.getMessage());
            }
        }




    }

//    public static void showAlertRed(final Context context, String message) {
//
//
//        try {
//            LayoutInflater li = LayoutInflater.from(context);
//            View view = li.inflate(R.layout.dialog_title_text_layout_red, null);
//
//
//            TextView tvMessage = view.findViewById(R.id.tv_message);
//            tvMessage.setText(message);
//
//            AlertDialog dialog = new AlertDialog.Builder(context, R.style.MyDialogTheme)
//                    .setCustomTitle(view)
//                    //  .setIcon(R.mipmap.ic_launcher)
//                    //.setMessage(message)
//                    .setCancelable(false)
//                    //.setPositiveButton("Ok", null)
//                    .show();
//
//            Button button = view.findViewById(R.id.btn_ok);
//            button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    dialog.dismiss();
//                }
//            });
//
//        } catch (WindowManager.BadTokenException e) {
//            Log.e("ahmad", e.getMessage());
//        }
//    }
//
//    public static void showAlertRed(final Context context, String message,
//                                    OnItemSelect onItemSelect) {
//
//
//        try {
//            LayoutInflater li = LayoutInflater.from(context);
//            View view = li.inflate(R.layout.dialog_title_text_layout_red, null);
//
//
//            TextView tvMessage = view.findViewById(R.id.tv_message);
//            tvMessage.setText(message);
//
//            AlertDialog dialog = new AlertDialog.Builder(context, R.style.MyDialogTheme)
//                    .setCustomTitle(view)
//                    //  .setIcon(R.mipmap.ic_launcher)
//                    //.setMessage(message)
//                    .setCancelable(false)
//                    //.setPositiveButton("Ok", null)
//                    .show();
//
//            Button button = view.findViewById(R.id.btn_ok);
//            button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    onItemSelect.onClick(view, 0);
//                }
//            });
//
//        } catch (WindowManager.BadTokenException e) {
//            Log.e("ahmad", e.getMessage());
//        }
//
//
//    }

    public static void showAlertSuccess(final Context context, String message,
                                        View.OnClickListener onClickListener) {


        try {
            LayoutInflater li = LayoutInflater.from(context);
            View view = li.inflate(R.layout.dialog_sucess_layout, null);

            TextView textView = view.findViewById(R.id.tv_dialog_title);
            textView.setText("Success!");
            TextView tvMessage = view.findViewById(R.id.tv_message);
            tvMessage.setText(message);

            AlertDialog dialog = new AlertDialog.Builder(context, R.style.MyDialogTheme)
                    .setCustomTitle(view)
                    //  .setIcon(R.mipmap.ic_launcher)
                    //.setMessage(message)
                    .setCancelable(false)
                    //.setPositiveButton("Ok", null)
                    .show();

            Button button = view.findViewById(R.id.btn_ok);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dialog.dismiss();
                    onClickListener.onClick(view);
                }
            });

        } catch (WindowManager.BadTokenException e) {
            Log.e("ahmad", e.getMessage());
        }


    }



    public static void showConfirmAlert(final Context context, String message,
                                        DialogInterface.OnClickListener onClickListener) {

        try {
            LayoutInflater li = LayoutInflater.from(context);
            View view = li.inflate(R.layout.dialog_title_text_layout, null);

            TextView textView = view.findViewById(R.id.tv_dialog_title);
            textView.setText("Success message.!");

            TextView tvMessage = view.findViewById(R.id.tv_message);
            tvMessage.setText(message);

            AlertDialog dialog = new AlertDialog.Builder(context, R.style.MyDialogTheme)
                    .setCustomTitle(view)
                    //  .setIcon(R.mipmap.ic_launcher)
                    // .setMessage(message)
                    .setCancelable(false).show();
            //.setPositiveButton("Ok", onClickListener).show();

            Button button = view.findViewById(R.id.btn_ok);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.onClick(dialog, 0);
                    dialog.dismiss();
                }
            });

        } catch (WindowManager.BadTokenException e) {
            Log.e("ahmad", e.getMessage());
        }


    }


    public static AlertDialog showLoader(final Context context) {

        try {
            LayoutInflater li = LayoutInflater.from(context);
            View view = li.inflate(R.layout.loadingloader, null);

            rotateLoading = (RotateLoading) view.findViewById(R.id.rotateloading);
            rotateLoading.start();

            AlertDialog dialog = new AlertDialog.Builder(context, R.style.CustomDialog)
                    .setCustomTitle(view)
                    //  .setIcon(R.mipmap.ic_launcher)

                    .setCancelable(false)
                    .show();

            return dialog;
        } catch (WindowManager.BadTokenException e) {
            Log.e("ahmad", e.getMessage());
        }

        return null;
    }

    public static void showAlert(final Context context, String message,
                                 DialogInterface.OnClickListener onClickListener) {

        try {
            LayoutInflater li = LayoutInflater.from(context);
            View view = li.inflate(R.layout.dialog_title_text_layout, null);

            TextView textView = view.findViewById(R.id.tv_dialog_title);
            textView.setText("Alert message");

            AlertDialog dialog = new AlertDialog.Builder(context, R.style.MyDialogTheme)
                    .setCustomTitle(view)
                    //  .setIcon(R.mipmap.ic_launcher)
                    .setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton("Ok", onClickListener)
                    .show();

        } catch (WindowManager.BadTokenException e) {
            Log.e("ahmad", e.getMessage());
        }


    }

    public static void showAlertYesNo(final Context context, String message,
                                      DialogInterface.OnClickListener onClickListener) {

        try {
            LayoutInflater li = LayoutInflater.from(context);
            View view = li.inflate(R.layout.dialog_title_text_layout_yes_no, null);

            TextView textView = view.findViewById(R.id.tv_dialog_title);
            textView.setText("Alert!");
            TextView tvMessage = view.findViewById(R.id.tv_message);
            tvMessage.setText(message);

            AlertDialog dialog = new AlertDialog.Builder(context, R.style.MyDialogTheme)
                    .setCustomTitle(view)
                    .setCancelable(false)
                    .show();

            Button btnYes = view.findViewById(R.id.btn_ok);
            Button btnNo = view.findViewById(R.id.btn_no);
            btnYes.setOnClickListener(view1 -> {
                onClickListener.onClick(dialog, 0);
                dialog.dismiss();
            });
            btnNo.setOnClickListener(view1 -> dialog.dismiss());

        } catch (WindowManager.BadTokenException e) {
            Log.e("ahmad", e.getMessage());
        }


    }


}
