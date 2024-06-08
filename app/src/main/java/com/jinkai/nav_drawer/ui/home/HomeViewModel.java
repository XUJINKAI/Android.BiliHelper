package com.jinkai.nav_drawer.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("创建b站视频、收藏夹等的桌面快捷方式\n" +
                "通过分享链接到本应用即可\n" +
                "也可手动输入链接或自动获取剪贴板链接");
    }

    public LiveData<String> getText() {
        return mText;
    }
}