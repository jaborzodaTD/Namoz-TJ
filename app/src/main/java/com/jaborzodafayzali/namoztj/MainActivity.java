package com.jaborzodafayzali.namoztj;

import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.card.MaterialCardView;

public class MainActivity extends AppCompatActivity {
    private int dp(float v){ return (int)(v*getResources().getDisplayMetrics().density+0.5f); }
    private TextView text(String s,float size,int color,boolean bold){
        TextView t=new TextView(this); t.setText(s); t.setTextSize(size); t.setTextColor(color);
        t.setTypeface(Typeface.DEFAULT,bold?Typeface.BOLD:Typeface.NORMAL); t.setGravity(Gravity.CENTER_VERTICAL);
        return t;
    }
    private MaterialCardView card(String title,String subtitle){
        MaterialCardView c=new MaterialCardView(this);
        c.setRadius(dp(24)); c.setCardBackgroundColor(Color.rgb(16,27,43)); c.setStrokeWidth(dp(1)); c.setStrokeColor(Color.rgb(35,55,75));
        c.setUseCompatPadding(false);
        LinearLayout box=new LinearLayout(this); box.setOrientation(LinearLayout.VERTICAL); box.setPadding(dp(18),dp(16),dp(18),dp(16));
        box.addView(text(title,18,Color.WHITE,true),new LinearLayout.LayoutParams(-1,dp(32)));
        box.addView(text(subtitle,13,Color.rgb(170,183,199),false),new LinearLayout.LayoutParams(-1,dp(42)));
        c.addView(box); return c;
    }
    @Override protected void onCreate(Bundle b){
        super.onCreate(b);
        getWindow().setStatusBarColor(Color.rgb(7,17,31)); getWindow().setNavigationBarColor(Color.rgb(7,17,31));
        LinearLayout root=new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL); root.setPadding(dp(20),dp(18),dp(20),dp(20)); root.setBackgroundColor(Color.rgb(7,17,31));
        TextView brand=text("Namoz TJ",28,Color.WHITE,true); root.addView(brand,new LinearLayout.LayoutParams(-1,dp(44)));
        TextView sub=text("Намоз • Қуръон • Дуо • Зикр",14,Color.rgb(170,183,199),false); root.addView(sub,new LinearLayout.LayoutParams(-1,dp(34)));
        MaterialCardView hero=card("Сегодня","Выберите город в настройках для точного расчёта времени намаза.");
        hero.setCardBackgroundColor(Color.rgb(12,45,47)); root.addView(hero,new LinearLayout.LayoutParams(-1,dp(108)));
        LinearLayout grid=new LinearLayout(this); grid.setOrientation(LinearLayout.VERTICAL); grid.setPadding(0,dp(14),0,0);
        String[][] items={{"🕌  Время намаза","Фаджр • Зухр • Аср • Магриб • Иша"},{"📖  Коран","114 сур • арабский • тоҷикӣ • русский"},{"🤲  Дуа","Ежедневные дуа и избранное"},{"🧭  Кыбла","Направление к Каабе"},{"📿  Тасбих","Зикр с сохранением счётчика"},{"⚙  Настройки","Язык • город • расчёт • уведомления"}};
        for(String[] x:items){ MaterialCardView c=card(x[0],x[1]); grid.addView(c,new LinearLayout.LayoutParams(-1,dp(88))); ((LinearLayout.LayoutParams)c.getLayoutParams()).bottomMargin=dp(10); }
        root.addView(grid,new LinearLayout.LayoutParams(-1,0,1));
        TextView footer=text("Версия 1.0 • Namoz TJ",12,Color.rgb(120,140,160),false); footer.setGravity(Gravity.CENTER); root.addView(footer,new LinearLayout.LayoutParams(-1,dp(28)));
        setContentView(root);
    }
}