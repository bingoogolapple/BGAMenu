package cn.bingoogolapple.bgamenu.demo.ui.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import cn.bingoogolapple.bgamenu.library.BGAMenu;
import cn.bingoogolapple.bgamenu.demo.R;
import cn.bingoogolapple.loon.library.Loon;
import cn.bingoogolapple.loon.library.LoonLayout;
import cn.bingoogolapple.loon.library.LoonView;


@LoonLayout(id = R.layout.activity_main)
public class MainActivity extends FragmentActivity {
    @LoonView(id = R.id.menu)
    private BGAMenu mMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Loon.injectView2Activity(this);
    }

    public void toggleMenu(View view) {
        mMenu.toggle();
    }
}