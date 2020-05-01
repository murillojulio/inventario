package com.murillojulio.inventario.mainModule.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.murillojulio.inventario.R;
import com.murillojulio.inventario.common.pojo.Product;
import com.murillojulio.inventario.mainModule.MainPresenter;
import com.murillojulio.inventario.mainModule.MainPresenterClass;
import com.murillojulio.inventario.mainModule.view.adapters.OnItemCliickListener;
import com.murillojulio.inventario.mainModule.view.adapters.ProductAdapter;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements OnItemCliickListener, MainView {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.contentMain)
    ConstraintLayout contentMain;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    
    private MainPresenter mainPresenter;
    private ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        
        configToolbar();
        configAdapter();
        configRecyclerView();

        mainPresenter = new MainPresenterClass(this);
        mainPresenter.onCreate();// Aqui se registra en EventBus
        
        //Toolbar toolbar = findViewById(R.id.toolbar);


        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    private void configToolbar() {
        setSupportActionBar(toolbar);
    }

    private void configAdapter(){
        productAdapter = new ProductAdapter(new ArrayList<Product>(), this);
    }

    private void configRecyclerView(){
        LinearLayoutManager linearLayoutManager;
        linearLayoutManager = new GridLayoutManager(this, getResources().getInteger(R.integer.main_columns));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(productAdapter);

    }

    @Override
    protected void onResume(){
        super.onResume();
        mainPresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mainPresenter.onPause();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mainPresenter.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
    }

    /*Metodos de la interface MainView*/

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgrees() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void add(Product product) {

    }

    @Override
    public void update(Product product) {

    }

    @Override
    public void remove(Product product) {

    }

    @Override
    public void removeFail() {
        Snackbar.make(contentMain, R.string.main_error_remove, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onShowError(int resMsg) {
        Snackbar.make(contentMain, resMsg, Snackbar.LENGTH_LONG).show();
    }

    /*Metodos de la interface OnItemCliickListener*/
    @Override
    public void onItemCliick(Product product) {

    }

    @Override
    public void onLongItemCliik(Product product) {

    }

}
