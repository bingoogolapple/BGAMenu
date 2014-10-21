package cn.bingoogolapple.bgamenu.demo.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import cn.bingoogolapple.bgamenu.demo.R;
import cn.bingoogolapple.bgamenu.library.BGAMenu;
import cn.bingoogolapple.loon.library.Loon;
import cn.bingoogolapple.loon.library.LoonLayout;
import cn.bingoogolapple.loon.library.LoonView;

@LoonLayout(id = R.layout.activity_main)
public class MainActivity extends FragmentActivity {
    @LoonView(id = R.id.menu1)
    private BGAMenu mMenu1;
    @LoonView(id = R.id.menu2)
    private BGAMenu mMenu2;
    @LoonView(id = R.id.menu3)
    private BGAMenu mMenu3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Loon.injectView2Activity(this);
    }

    public void toggleMenu1(View view) {
        mMenu1.toggle();
    }

    public void toggleMenu2(View view) {
        mMenu2.toggle();
    }

    public void toggleMenu3(View view) {
        mMenu3.toggle();
    }
}