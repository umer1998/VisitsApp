package com.example.visitsapp.ui.fragments;


import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {
    /**
     * Could handle back press.
     *
     * @return true if back press was handled
     */
    public abstract boolean onBackPressed();
}
