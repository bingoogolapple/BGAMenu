package cn.bingoogolapple.bgamenu.demo.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import cn.bingoogolapple.bgaannotation.BGAA;
import cn.bingoogolapple.bgaannotation.BGAALayout;
import cn.bingoogolapple.bgaannotation.BGAAView;
import cn.bingoogolapple.bgamenu.BGAMenu;
import cn.bingoogolapple.bgamenu.demo.R;


@BGAALayout(R.layout.activity_main)
public class MainActivity extends ActionBarActivity {
    @BGAAView(R.id.menu1)
    private BGAMenu mMenu1;
    @BGAAView(R.id.menu2)
    private BGAMenu mMenu2;
    @BGAAView(R.id.menu3)
    private BGAMenu mMenu3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BGAA.injectView2Activity(this);
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