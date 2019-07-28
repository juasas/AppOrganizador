package com.example.juasa.apporganizador;

        import android.os.Bundle;
        import android.support.v4.app.FragmentActivity;
        import android.support.v4.app.FragmentManager;
        import android.view.Menu;
        import android.view.View;
        import android.widget.Button;
public class MainActivity extends FragmentActivity {
    private Button btnAlerta = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAlerta = (Button)findViewById(R.id.BtnAlerta);
        btnAlerta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fragmentManager =
                        getSupportFragmentManager();
                DialogoAlerta dialogo = new DialogoAlerta();
                dialogo.show(fragmentManager, "tagAlerta");
            }
        });
    }
}