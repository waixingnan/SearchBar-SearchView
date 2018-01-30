package com.lapism.searchview.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.lapism.searchview.R;
import com.lapism.searchview.Search;


public class SearchBar extends SearchLayout {

    public final static String TAG = SearchBar.class.getName();

    private Search.OnBarClickListener mOnBarClickListener;

    // ---------------------------------------------------------------------------------------------
    public SearchBar(@NonNull Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public SearchBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public SearchBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SearchBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    // ---------------------------------------------------------------------------------------------
    @Override
    protected void onTextChanged(CharSequence s) {
        if (mOnQueryTextListener != null) {
            mOnQueryTextListener.onQueryTextChange(s);
        }
    }

    @Override
    protected void addFocus() {
        if (mOnMicClickListener == null) {
            mImageViewMic.setVisibility(View.VISIBLE);
        }
        showKeyboard();
    }

    @Override
    protected void removeFocus() {
        if (mOnMicClickListener == null) {
            mImageViewMic.setVisibility(View.GONE);
        }
        hideKeyboard();
    }

    @Override
    protected boolean isView() {
        return false;
    }

    @Override
    protected int getLayout() {
        return R.layout.search_bar;
    }

    @Override
    public void open() {
        mSearchEditText.setVisibility(View.VISIBLE);
        mSearchEditText.requestFocus();
    }

    @Override
    public void close() {
        mSearchEditText.clearFocus();
        mSearchEditText.setVisibility(View.GONE);
    }

    // ---------------------------------------------------------------------------------------------
    @Override
    protected void init(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SearchBar, defStyleAttr, defStyleRes);
        final int layoutResId = getLayout();

        final LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(layoutResId, this, true);

        super.init(context, attrs, defStyleAttr, defStyleRes);

        setLogo(a.getInt(R.styleable.SearchBar_search_logo, Search.Logo.G));
        setShape(a.getInt(R.styleable.SearchBar_search_shape, Search.Shape.CLASSIC));
        setTheme(a.getInt(R.styleable.SearchBar_search_theme, Search.Theme.COLOR));

        if (a.hasValue(R.styleable.SearchBar_search_elevation)) {
            setElevation(a.getDimensionPixelSize(R.styleable.SearchBar_search_elevation, 0));
        }

        a.recycle();

        setOnClickListener(this);
    }

    // ---------------------------------------------------------------------------------------------
    // Listeners
    public void setOnBarClickListener(Search.OnBarClickListener listener) {
        mOnBarClickListener = listener;
    }

    // ---------------------------------------------------------------------------------------------
    @Override
    public void onClick(View v) {
        if (v == mImageViewLogo) {
            close();
        } else if (v == mImageViewMic) {
            if (mOnMicClickListener != null) {
                mOnMicClickListener.onMicClick();
            }
        } else if (v == mImageViewMenu) {
            if (mOnMenuClickListener != null) {
                mOnMenuClickListener.onMenuClick();
            }
        } else if (v == this) {
            if (mOnBarClickListener != null) {
                mOnBarClickListener.onBarClick();
            } else {
                open();
            }
        }
    }

}
