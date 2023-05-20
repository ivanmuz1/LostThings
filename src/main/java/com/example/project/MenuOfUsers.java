

        package com.example.project;

        import android.content.Intent;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.Menu;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;


        import com.firebase.ui.database.FirebaseRecyclerAdapter;
        import com.firebase.ui.database.FirebaseRecyclerOptions;
        import com.google.android.material.navigation.NavigationView;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.Query;
        import com.squareup.picasso.Picasso;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.ActionBarDrawerToggle;
        import androidx.core.view.GravityCompat;
        import androidx.navigation.ui.AppBarConfiguration;
        import androidx.drawerlayout.widget.DrawerLayout;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.appcompat.widget.Toolbar;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import org.jetbrains.annotations.NotNull;

        import de.hdodenhof.circleimageview.CircleImageView;

        public class MenuOfUsers extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private AppBarConfiguration mAppBarConfiguration;
    DatabaseReference ProductsRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private Button search;
    private EditText searchtext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_of_users);

        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Меню");
        setSupportActionBar(toolbar);

        search = (Button) findViewById(R.id.button2);
        searchtext = (EditText) findViewById(R.id.editTextTextMultiLine2);

        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }

        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView userNameTextView = headerView.findViewById(R.id.user_profile_name);
       CircleImageView profileImageView = headerView.findViewById(R.id.user_profile_image);

        //userNameTextView.setText(mAuth.getCurrentUser().getDisplayName());
        //Picasso.get().load(Prevalent.currentOnlineUser.getImage()).placeholder(R.drawable.logo).into(profileImageView);
        mAuth = FirebaseAuth.getInstance();


        recyclerView = findViewById(R.id.Recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchText = searchtext.getText().toString();
                firebasesearch(searchText);
            }
        });{

        };
    }

    private void firebasesearch(String searchText){

        Query firebasesearch = ProductsRef.orderByChild("pname").startAt(searchText).endAt(searchText+"\uf88f");

        FirebaseRecyclerOptions<Items> options = new FirebaseRecyclerOptions.Builder<Items>().setQuery(firebasesearch, Items.class).build();
        FirebaseRecyclerAdapter<Items, Veiwer> adapter = new FirebaseRecyclerAdapter<Items, Veiwer>(options) {

            protected void onBindViewHolder(Veiwer holder, int i, Items model) {
                holder.txtProductName.setText(model.getPname());
                holder.txtProductDescription.setText(model.getDescription());
                Picasso.get().load(model.getImage()).into(holder.imageView);
            }

           public Veiwer onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false);
                Veiwer holder = new Veiwer(view);
                return holder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
    @Override
    protected void onStart(){
        super.onStart();

        FirebaseRecyclerOptions<Items> options = new FirebaseRecyclerOptions.Builder<Items>()
                .setQuery(ProductsRef, Items.class).build();

        FirebaseRecyclerAdapter<Items, Veiwer> adapter = new FirebaseRecyclerAdapter<Items, Veiwer>(options) {
            @Override
            protected void onBindViewHolder(@NonNull @NotNull Veiwer holder, int i, @NonNull @NotNull Items model) {
                holder.txtProductName.setText(model.getPname());
                holder.txtProductDescription.setText(model.getDescription());
                Picasso.get().load(model.getImage()).into(holder.imageView);
            }

            @NonNull
            @NotNull
            @Override
            public Veiwer onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false);
                Veiwer holder = new Veiwer(view);
                return holder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_of_users, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.nav_cart){

        } else if(id == R.id.nav_add_advert){
            Intent add = new Intent(MenuOfUsers.this, AddNewLostThing.class);
            startActivity(add);

        } else if(id == R.id.nav_categories){
            Intent cat = new Intent(MenuOfUsers.this, CategoryActivity.class);
            startActivity(cat);
        }
        else if(id == R.id.nav_messenger){
            Intent mes = new Intent(MenuOfUsers.this, MainActivity2.class);
            startActivity(mes);
        }

        if(id == R.id.nav_logout){
            mAuth.signOut();
            Intent loginIntent = new Intent(MenuOfUsers.this, LoginActivity.class);
            startActivity(loginIntent);
        }

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }
}
