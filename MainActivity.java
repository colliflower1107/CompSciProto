package com.robothamster.coleslaw.compsciproto;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);

        /**
        Create the first floating button
         */
        FloatingActionButton fab = findViewById(R.id.fab);
        /**
        Add the action listener so that the button saves on a short press
        and load on a long press
        */
        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                readFile();

                return true;
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {

                saveFile();

            }
        });
        /**
        Create the second floating button
         */
        FloatingActionButton fab2 = findViewById(R.id.readfab);
        /**
         make the button launch a YouTube search with the name of the element
        */
        fab2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                searchYT();
                Snackbar.make(view, "YouTube Link Generated", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        /**
        Set up the menu drawer that slides out
         */
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*
        add this line to display menu1 when the activity is loaded
        */
        displaySelectedScreen(R.id.nav_menu1);

    }

    @Override
    /**
    What to do when the back button is pressed and the menu is open
     */
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    /**
    Handle what happens with each menu item
     */
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
    /**
     * 
     * @param itemId
     * Displays the screen selected in the menu
     */
    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_menu1:
                fragment = new Menu1();
                break;
            case R.id.nav_menu2:
                fragment = new Menu2();
                break;
            case R.id.nav_menu3:
                fragment = new Menu3();
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    public boolean onNavigationItemSelected(MenuItem item) {

        //calling the method displayselectedscreen and passing the id of selected menu
        displaySelectedScreen(item.getItemId());
        //make this method blank
        return true;
    }
    /**
     * Used to save a file from each of the respective text fields.
     * Uses the name in the element name text field as the file name 
     */

    public void saveFile(){
        //check for storage permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            makeText(getBaseContext(), "You have not allowed storage permission. Go to Settings > " +
                    "Apps > Chem-mate-stry > Permissions, and allow storage permission.", LENGTH_LONG).show();

        }
        // get the contents of each text field and convert it to a string
        EditText editTextName = findViewById(R.id.editName);
        String editNameRes = editTextName.getText().toString();
        EditText editTextAMass = findViewById(R.id.editAMass);
        String editAMassRes = editTextAMass.getText().toString();
        EditText editTextRMass = findViewById(R.id.editRMass);
        String editRMassRes = editTextRMass.getText().toString();
        EditText editTextProp = findViewById(R.id.editProperties);
        String editPropRes = editTextProp.getText().toString();
        if(editNameRes.isEmpty ()){
            makeText(getBaseContext(), "Name Cannot Be Null", Toast.LENGTH_SHORT).show();
        }
        else {
            try {
                putHashName ( editNameRes );
                File savefile = new File ( Environment.getExternalStorageDirectory () + "/elements", editNameRes + ".txt" );
                savefile.delete ();
                if (!savefile.exists ()) {
                    savefile.createNewFile ();
                    // when the file is saving, make a toast message pop up confirming
                    // that it has been saved.
                    makeText ( getBaseContext (), "File Saved", Toast.LENGTH_SHORT ).show ();

                } else {
                    makeText ( getBaseContext (), "File Overwritten", Toast.LENGTH_SHORT ).show ();
                }
                Snackbar.make(getCurrentFocus (), "Changes Saved", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                BufferedWriter writer = new BufferedWriter ( new FileWriter ( savefile, true ) );
                writer.write ( editNameRes );
                writer.newLine ();
                writer.write ( editRMassRes );
                writer.newLine ();
                writer.write ( editAMassRes );
                writer.newLine ();
                writer.write ( editPropRes );
                writer.newLine ();

                writer.flush ();
                writer.close ();

            } catch (IOException e) {
                makeText ( getBaseContext (), e.getMessage (), LENGTH_LONG ).show ();
            }
        }
    }
    /**
     * Method to read the file by the name that is in the name text field
     */
    public void readFile() {
        EditText editTextName = findViewById(R.id.editName);
        String editNameRes = editTextName.getText().toString();
        EditText editTextAMass = findViewById(R.id.editAMass);
        EditText editTextRMass = findViewById(R.id.editRMass);
        EditText editTextProp = findViewById(R.id.editProperties);
        editTextProp.setText("");

        if(editNameRes.isEmpty ()){
            makeText(getBaseContext(), "Name Cannot Be Null", Toast.LENGTH_SHORT).show();
        }
        else {
            String filevalue;
            filevalue = getHashName ( editNameRes );
            File file = new File ( Environment.getExternalStorageDirectory () + "/elements", filevalue + ".txt" );
            //Read text from file
            try {
                BufferedReader br = new BufferedReader ( new FileReader ( file ) );
                String line;
                // the place the values in each specific line into their respective text boxes
                int linenum = 0;
                while ((line = br.readLine ()) != null) {
                    linenum++;
                    if (linenum == 2) {
                        editTextRMass.setText ( line );
                    } else if (linenum == 3) {
                        editTextAMass.setText ( line );
                    } else if (linenum > 3) {
                        editTextProp.append ( line + "\n" );
                    }
                }
                br.close ();
                Snackbar.make(getCurrentFocus (), "File Read", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } catch (IOException e) {
                makeText ( getBaseContext (), e.getMessage (), LENGTH_LONG ).show ();
            }
        }
    }
    /**
     * 
     * @param name
     * Takes a string and places it into a hash table
     */
    public void putHashName(String name){

        HashMap hash = new HashMap();
        int hashkey;
        hashkey = name.hashCode ()%128;
        if(hashkey < 0){
            hashkey = hashkey*-1;
        }
        hash.put(hashkey, name);
    }
    /**
     * 
     * @param name
     * @return
     * Returns the value of the key specified by the user from the hash table
     */
    public String getHashName(String name){
        HashMap hash = new HashMap ();

        String result;
        int hashkey;
        hashkey = name.hashCode ()%128;
        if(hashkey < 0){
            hashkey = hashkey*-1;
        }
        hash.put(hashkey, name);
        result = hash.get(hashkey);
        return result;
    }
    public void searchYT(){
        EditText editName = findViewById(R.id.editName);
        String ytQuery = "https://www.youtube.com/results?search_query="+editName.getText().toString();
        makeText(getBaseContext(), ytQuery, LENGTH_LONG).show();
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ytQuery));
        startActivity(browserIntent);
    }
/**
 * create a file which contains the contents of all other text files in the directory
 */
    public void viewAll(){
        File allelements = new File(Environment.getExternalStorageDirectory()+"/elements", "allelements.txt");
        allelements.setReadable (false);
        File[] filelist = new File(Environment.getExternalStorageDirectory ()+"/elements").listFiles ();

        for (int i = 0; i<filelist.length;i++){

            String filename1 = filelist[i].getName();
            File test1 = new File(Environment.getExternalStorageDirectory()+"/elements", filename1);

            try {
                mergeFiles ( allelements, allelements, test1 );

            } catch (IOException e) {
                Toast.makeText(getBaseContext(), (CharSequence) e, LENGTH_LONG).show();
            }
        }

    }
    /**
     * 
     * @param output
     * @param inputfile1
     * @param inputfile2
     * @throws IOException
     * Method used to merge the contents of 2 text files
     */
    public void mergeFiles(File output, File inputfile1, File inputfile2) throws IOException{
        BufferedWriter writer=null;
        BufferedReader br = null;
        BufferedReader r = null;

        try{
            writer = new BufferedWriter(new FileWriter(output));
            br = new BufferedReader(new FileReader(inputfile1));
            r = new BufferedReader(new FileReader(inputfile2));
            String s1 =null;

            while ((s1 = br.readLine()) != null){
                writer.write(s1);
                writer.write("\n");
            }
            while((s1 = r.readLine()) != null){
                writer.write(s1);
                writer.write("\n");
            }
        }
        catch (IOException e){
            Toast.makeText(getBaseContext(), e.toString (), LENGTH_LONG).show();

        }finally{
            if(br != null)br.close();
            if(r != null)r.close();
            if(writer != null)writer.close();
        }
    }
}
