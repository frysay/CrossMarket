package com.crossshop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.crossshop.arrayadapter.MainAdvertiseArrayAdapter;
import com.crossshop.arrayadapter.MainCategoryArrayAdapter;
import com.crossshop.arrayadapter.MainLastshopArrayAdapter;
import com.crossshop.database.DictionaryOpenHelperConad;
import com.crossshop.database.DictionaryOpenHelperCoop;
import com.crossshop.database.DictionaryOpenHelperCrai;
import com.crossshop.database.DictionaryOpenHelperCrossDB;
import com.crossshop.database.ItemDB;
import com.crossshop.database.ItemDataSource;
import com.devsmart.android.ui.HorizontalListView;

public class Main extends Activity {

	List<ItemDB> items;

	ItemDataSource datasource;

	final Context context = this;

	TextView tv_counter;

	EditText searchBar;

	ImageView ib_cart;

	Button categories;
	Button lastshop;
	Button advertise;
	Button login;	

	List<Integer> selectedItems;
	List<ItemDB> lastshopItems;
	List<ItemDB> advertiseItems;

	private static final int CHECK_OUT = 2;
	public static final int LOG_IN = 1;
	private static final int SEARCH_ITEM = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		refreshDisplay();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == SEARCH_ITEM && resultCode == -1) {
			Bundle b = data.getExtras();
			List<Integer> tmp = b.getIntegerArrayList("com.smartshop.ListViewPrototype.selectedItems");
			try {
				for(int i = 0; i < tmp.size(); i++) {
					if(!selectedItems.contains(tmp.get(i))){
						selectedItems.add(tmp.get(i));
					}
				}
			} catch (Exception e) {
				selectedItems = tmp;			}
		} else if(requestCode == LOG_IN && resultCode == -1) {
			logout();
		} else if(requestCode == CHECK_OUT && resultCode == -2) {
			Bundle b = data.getExtras();
			selectedItems = b.getIntegerArrayList("com.smartshop.CartView.selectedItems");
		}
	}

	private void deleteDBs() {
		this.deleteDatabase("crossdb.db");
		this.deleteDatabase("coop.db");
		this.deleteDatabase("conad.db");
		this.deleteDatabase("crai.db");
	}

	private void initializeDBs() {
		List<ItemDB> tmp = new ArrayList<ItemDB>(); 

		tmp.addAll(initializeCoopDB());
		tmp.addAll(initializeConadDB());
		tmp.addAll(initializeCraiDB());

		Collections.sort(tmp, new Comparator<ItemDB>() {
			@Override
			public int compare(ItemDB one, ItemDB other) {
				return one.getName().compareTo(other.getName());
			}
		}); 

		Collections.sort(tmp, new Comparator<ItemDB>() {
			@Override
			public int compare(ItemDB one, ItemDB other) {
				return one.getCategory().compareTo(other.getCategory());
			}
		}); 

		datasource = new ItemDataSource(this, DictionaryOpenHelperCrossDB.getColumns());
		datasource.open();

		for(int i = 0; i < tmp.size(); i++) {
			datasource.create(tmp.get(i));
		}

		items = datasource.findAll();		
	}

	private void logout() {
		login.setText("Logout");
		lastshop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), LastShop.class);
				intent.putParcelableArrayListExtra("com.smartshop.Main.lastshopItems", (ArrayList<ItemDB>) lastshopItems);
				startActivityForResult(intent, SEARCH_ITEM);				
			}
		});

		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(Main.this)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle("Logout")
				.setMessage("Do you really want to logout?")
				.setNegativeButton("No", null)
				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						SharedPreferences prefs = getSharedPreferences("smartshop_pref", MODE_PRIVATE);
						Editor editor = prefs.edit();
						editor.remove("username");
						editor.remove("password");
						editor.commit();

						Intent intent  = new Intent(getApplicationContext(), Main.class);
						startActivity(intent);
						finish();
					}
				})
				.show();
			}
		});
	}

	private List<ItemDB> initializeCoopDB() {
		List<ItemDB> tmp = new ArrayList<ItemDB>(); 

		ItemDataSource datasourceTmp = new ItemDataSource(this, DictionaryOpenHelperCoop.getColumns());
		datasourceTmp.open();

		ItemDB item = new ItemDB();

		//Actilift Dash
		item.setItemId("DB123A1");
		item.setCategory(ItemDataSource.categories[2]);
		item.setNameAndMarket("Actilift Dash", DictionaryOpenHelperCoop.getColumns()[0]);
		item.setBrand("Dash");
		item.setImage(R.drawable.dash);
		item.setPrize(7.04);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 1.5 litri");
		datasourceTmp.create(item);	


		//Farina di Grano Tenero Barilla
		item.setItemId("DB123A2");
		item.setCategory(ItemDataSource.categories[5]);
		item.setNameAndMarket("Farina di Grano Tenero Barilla", DictionaryOpenHelperCoop.getColumns()[0]);
		item.setBrand("Barilla");
		item.setImage(R.drawable.farina);
		item.setPrize(0.79);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 1 Kg");
		datasourceTmp.create(item);

		//Pennette Rigate Barilla
		item.setItemId("DB123A3");
		item.setCategory(ItemDataSource.categories[3]);
		item.setNameAndMarket("Pennette Rigate Barilla", DictionaryOpenHelperCoop.getColumns()[0]);
		item.setBrand("Barilla");
		item.setImage(R.drawable.penne);
		item.setPrize(0.79);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 500 gr");
		datasourceTmp.create(item);

		//Pesto Barilla
		item.setItemId("DB123A4");
		item.setCategory(ItemDataSource.categories[4]);
		item.setNameAndMarket("Pesto Barilla", DictionaryOpenHelperCoop.getColumns()[0]);
		item.setBrand("Barilla");
		item.setImage(R.drawable.pesto);
		item.setPrize(2.47);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 190 gr");
		datasourceTmp.create(item);

		//Spaghetti N.5 Barilla
		item.setItemId("DB123A5");
		item.setCategory(ItemDataSource.categories[3]);
		item.setNameAndMarket("Spaghetti N.5 Barilla", DictionaryOpenHelperCoop.getColumns()[0]);
		item.setBrand("Barilla");
		item.setImage(R.drawable.spaghetti);
		item.setPrize(0.79);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 500 gr");
		datasourceTmp.create(item);

		//Sugo al Basilico con Pomodorini Datterini Barilla
		item.setItemId("DB123A6");
		item.setCategory(ItemDataSource.categories[4]);
		item.setNameAndMarket("Sugo al Basilico con Pomodorini Datterini Barilla", DictionaryOpenHelperCoop.getColumns()[0]);
		item.setBrand("Barilla");
		item.setImage(R.drawable.pomarola);
		item.setPrize(1.65);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 400 gr");
		datasourceTmp.create(item);

		//Maionese Calve'
		item.setItemId("DB123A7");
		item.setCategory(ItemDataSource.categories[4]);
		item.setNameAndMarket("Maionese Calve'", DictionaryOpenHelperCoop.getColumns()[0]);
		item.setBrand("Calve'");
		item.setImage(R.drawable.maionese);
		item.setPrize(1.41);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 225 ml");
		datasourceTmp.create(item);

		//Olio Extra Vergine Delicato Carapelli
		item.setItemId("DB123A8");
		item.setCategory(ItemDataSource.categories[5]);
		item.setNameAndMarket("Olio Extra Vergine Delicato Carapelli", DictionaryOpenHelperCoop.getColumns()[0]);
		item.setBrand("Carapelli");
		item.setImage(R.drawable.olio_oliva);
		item.setPrize(6.55);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 1 litro");
		datasourceTmp.create(item);

		//Coca Cola in Bottiglia x2
		item.setItemId("DB123A9");
		item.setCategory(ItemDataSource.categories[0]);
		item.setNameAndMarket("Coca Cola in Bottiglia x2", DictionaryOpenHelperCoop.getColumns()[0]);
		item.setBrand("Coca Cola");
		item.setImage(R.drawable.coca_cola);
		item.setPrize(3.05);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 1.5 litri x2");
		datasourceTmp.create(item);

		//Bagno Crema Dove
		item.setItemId("DB123B1");
		item.setCategory(ItemDataSource.categories[2]);
		item.setNameAndMarket("Bagno Crema Dove", DictionaryOpenHelperCoop.getColumns()[0]);
		item.setBrand("Dove");
		item.setImage(R.drawable.bagno_crema);
		item.setPrize(2.49);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 0.5 litri");
		datasourceTmp.create(item);

		//Olio di Semi per Friggere Friol
		item.setItemId("DB123B2");
		item.setCategory(ItemDataSource.categories[5]);
		item.setNameAndMarket("Olio di Semi per Friggere Friol", DictionaryOpenHelperCoop.getColumns()[0]);
		item.setBrand("Friol");
		item.setImage(R.drawable.olio_semi);
		item.setPrize(2.43);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 1 litro");
		datasourceTmp.create(item);

		//Birra in Bottiglia Heineken
		item.setItemId("DB123B3");
		item.setCategory(ItemDataSource.categories[0]);
		item.setNameAndMarket("Birra in Bottiglia Heineken", DictionaryOpenHelperCoop.getColumns()[0]);
		item.setBrand("Heineken");
		item.setImage(R.drawable.birra);
		item.setPrize(2.75);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 33 cl x3");
		datasourceTmp.create(item);

		//Caffe Lavazza Qualita' Rossa x2
		item.setItemId("DB123B4");
		item.setCategory(ItemDataSource.categories[1]);
		item.setNameAndMarket("Caffe Lavazza Qualita' Rossa x2", DictionaryOpenHelperCoop.getColumns()[0]);
		item.setBrand("Lavazza");
		item.setImage(R.drawable.caffe);
		item.setPrize(5.99);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 250 gr x2");
		datasourceTmp.create(item);

		//Acqua Naturale Levissima x6
		item.setItemId("DB123B5");
		item.setCategory(ItemDataSource.categories[0]);
		item.setNameAndMarket("Acqua Naturale Levissima x6", DictionaryOpenHelperCoop.getColumns()[0]);
		item.setBrand("Levissima");
		item.setImage(R.drawable.levissima);
		item.setPrize(3.00);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 1.5 litro x6");
		datasourceTmp.create(item);

		//Biscotti Macine Mulino Bianco
		item.setItemId("DB123B6");
		item.setCategory(ItemDataSource.categories[1]);
		item.setNameAndMarket("Biscotti Macine Mulino Bianco", DictionaryOpenHelperCoop.getColumns()[0]);
		item.setBrand("Mulino Bianco");
		item.setImage(R.drawable.biscotti_macine);
		item.setPrize(2.43);
		item.setDiscount(15);
		item.setDesciption("Descrizione: 800 gr");
		datasourceTmp.create(item);

		//Pane PanBauletto Bianco Mulino Bianco
		item.setItemId("DB123B8");
		item.setCategory(ItemDataSource.categories[3]);
		item.setNameAndMarket("Pane PanBauletto Bianco Mulino Bianco", DictionaryOpenHelperCoop.getColumns()[0]);
		item.setBrand("Mulino Bianco");
		item.setImage(R.drawable.pane_bauletto);
		item.setPrize(1.19);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 400 gr");
		datasourceTmp.create(item);

		//Detersivo Piatti al Limone Nelsen
		item.setItemId("DB123B9");
		item.setCategory(ItemDataSource.categories[2]);
		item.setNameAndMarket("Detersivo Piatti al Limone Nelsen", DictionaryOpenHelperCoop.getColumns()[0]);
		item.setBrand("Nelsen");
		item.setImage(R.drawable.detersivo_piatti);
		item.setPrize(2.19);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 1 litro");
		datasourceTmp.create(item);

		//Deodorante Spray Natural Dry Neutro Roberts
		item.setItemId("DB123C1");
		item.setCategory(ItemDataSource.categories[2]);
		item.setNameAndMarket("Deodorante Spray Natural Dry Neutro Roberts", DictionaryOpenHelperCoop.getColumns()[0]);
		item.setBrand("Neutro Roberts");
		item.setImage(R.drawable.deodorante);
		item.setPrize(2.82);
		item.setDiscount(15);
		item.setDesciption("Descrizione: 150 ml");
		datasourceTmp.create(item);

		//Nutella Ferrero
		item.setItemId("DB123C2");
		item.setCategory(ItemDataSource.categories[1]);
		item.setNameAndMarket("Nutella Ferrero", DictionaryOpenHelperCoop.getColumns()[0]);
		item.setBrand("Ferrero");
		item.setImage(R.drawable.nutella);
		item.setPrize(4.59);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 630 gr");
		datasourceTmp.create(item);

		//Carta Igienica Veli Maxi Rotoli Regina 4pz
		item.setItemId("DB123C3");
		item.setCategory(ItemDataSource.categories[2]);
		item.setNameAndMarket("Carta Igienica Veli Maxi Rotoli Regina 4pz", DictionaryOpenHelperCoop.getColumns()[0]);
		item.setBrand("Regina");
		item.setImage(R.drawable.carta_igienica);
		item.setPrize(4.25);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 4pz");
		datasourceTmp.create(item);

		//Tonno in Scatola Rio Mare x2
		item.setItemId("DB123C4");
		item.setCategory(ItemDataSource.categories[4]);
		item.setNameAndMarket("Tonno in Scatola Rio Mare x2", DictionaryOpenHelperCoop.getColumns()[0]);
		item.setBrand("Rio Mare");
		item.setImage(R.drawable.tonno);
		item.setPrize(4.89);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 160 gr x2");
		datasourceTmp.create(item);

		//Patatine Classiche San Carlo
		item.setItemId("DB123C5");
		item.setCategory(ItemDataSource.categories[6]);
		item.setNameAndMarket("Patatine Classiche San Carlo", DictionaryOpenHelperCoop.getColumns()[0]);
		item.setBrand("San Carlo");
		item.setImage(R.drawable.patatine);
		item.setPrize(1.85);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 180 gr");
		datasourceTmp.create(item);

		//Saponetta per Mani Dove x2
		item.setItemId("DB123C6");
		item.setCategory(ItemDataSource.categories[2]);
		item.setNameAndMarket("Saponetta per Mani Dove x2", DictionaryOpenHelperCoop.getColumns()[0]);
		item.setBrand("Dove");
		item.setImage(R.drawable.saponetta);
		item.setPrize(1.97);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 100 gr x2");
		datasourceTmp.create(item);

		//Shampoo 2in1 Capelli Normali Fructis
		item.setItemId("DB123C7");
		item.setCategory(ItemDataSource.categories[2]);
		item.setNameAndMarket("Shampoo 2in1 Capelli Normali Fructis", DictionaryOpenHelperCoop.getColumns()[0]);
		item.setBrand("Fructis");
		item.setImage(R.drawable.shampoo);
		item.setPrize(3.36);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 250 ml");
		datasourceTmp.create(item);

		//The' Earl Gray Twinings
		item.setItemId("DB123C8");
		item.setCategory(ItemDataSource.categories[1]);
		item.setNameAndMarket("The' Earl Gray Twinings", DictionaryOpenHelperCoop.getColumns()[0]);
		item.setBrand("Twinings");
		item.setImage(R.drawable.the);
		item.setPrize(2.34);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 25pz");
		datasourceTmp.create(item);

		//Zucchero Semolato Finissimo Zefiro
		item.setItemId("DB123C9");
		item.setCategory(ItemDataSource.categories[5]);
		item.setNameAndMarket("Zucchero Semolato Finissimo Zefiro", DictionaryOpenHelperCoop.getColumns()[0]);
		item.setBrand("Zefiro");
		item.setImage(R.drawable.zucchero_zefiro);
		item.setPrize(1.72);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 1 kg");
		datasourceTmp.create(item);

		//Marmellata alle Pesche Zuegg
		item.setItemId("DB123D1");
		item.setCategory(ItemDataSource.categories[1]);
		item.setNameAndMarket("Marmellata alle Pesche Zuegg", DictionaryOpenHelperCoop.getColumns()[0]);
		item.setBrand("Zuegg");
		item.setImage(R.drawable.marmellata_pesche);
		item.setPrize(2.39);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 320 gr");
		datasourceTmp.create(item);

		//Fette Biscottate Dorate Mulino Bianco
		item.setItemId("DB123D3");
		item.setCategory(ItemDataSource.categories[1]);
		item.setNameAndMarket("Fette Biscottate Dorate Mulino Bianco", DictionaryOpenHelperCoop.getColumns()[0]);
		item.setBrand("Mulino Bianco");
		item.setImage(R.drawable.fette_biscottate);
		item.setPrize(0.79);
		item.setDiscount(42);
		item.setDesciption("Descrizione: 80pz");
		datasourceTmp.create(item);

		tmp = datasourceTmp.findAll();

		datasourceTmp.close();

		return tmp;		
	}

	private List<ItemDB> initializeConadDB() {
		List<ItemDB> tmp = new ArrayList<ItemDB>(); 

		ItemDataSource datasourceTmp = new ItemDataSource(this, DictionaryOpenHelperConad.getColumns());
		datasourceTmp.open();

		ItemDB item = new ItemDB();

		//Actilift Dash
		item.setItemId("DB124A1");
		item.setCategory(ItemDataSource.categories[2]);
		item.setNameAndMarket("Actilift Dash", DictionaryOpenHelperConad.getColumns()[0]);
		item.setBrand("Dash");
		item.setImage(R.drawable.dash);
		item.setPrize(6.39);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 1.5 litri");
		datasourceTmp.create(item);	


		//Farina di Grano Tenero Barilla
		item.setItemId("DB124A2");
		item.setCategory(ItemDataSource.categories[5]);
		item.setNameAndMarket("Farina di Grano Tenero Barilla", DictionaryOpenHelperConad.getColumns()[0]);
		item.setBrand("Barilla");
		item.setImage(R.drawable.farina);
		item.setPrize(0.79);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 1 Kg");
		datasourceTmp.create(item);

		//Pennette Rigate Barilla
		item.setItemId("DB124A3");
		item.setCategory(ItemDataSource.categories[3]);
		item.setNameAndMarket("Pennette Rigate Barilla", DictionaryOpenHelperConad.getColumns()[0]);
		item.setBrand("Barilla");
		item.setImage(R.drawable.penne);
		item.setPrize(0.75);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 500 gr");
		datasourceTmp.create(item);

		//Pesto Barilla
		item.setItemId("DB124A4");
		item.setCategory(ItemDataSource.categories[4]);
		item.setNameAndMarket("Pesto Barilla", DictionaryOpenHelperConad.getColumns()[0]);
		item.setBrand("Barilla");
		item.setImage(R.drawable.pesto);
		item.setPrize(2.29);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 190 gr");
		datasourceTmp.create(item);

		//Spaghetti N.5 Barilla
		item.setItemId("DB124A5");
		item.setCategory(ItemDataSource.categories[3]);
		item.setNameAndMarket("Spaghetti N.5 Barilla", DictionaryOpenHelperConad.getColumns()[0]);
		item.setBrand("Barilla");
		item.setImage(R.drawable.spaghetti);
		item.setPrize(0.75);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 500 gr");
		datasourceTmp.create(item);

		//Sugo al Basilico con Pomodorini Datterini Barilla
		item.setItemId("DB124A6");
		item.setCategory(ItemDataSource.categories[4]);
		item.setNameAndMarket("Sugo al Basilico con Pomodorini Datterini Barilla", DictionaryOpenHelperConad.getColumns()[0]);
		item.setBrand("Barilla");
		item.setImage(R.drawable.pomarola);
		item.setPrize(1.79);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 400 gr");
		datasourceTmp.create(item);

		//Maionese Calve'
		item.setItemId("DB124A7");
		item.setCategory(ItemDataSource.categories[4]);
		item.setNameAndMarket("Maionese Calve'", DictionaryOpenHelperConad.getColumns()[0]);
		item.setBrand("Calve'");
		item.setImage(R.drawable.maionese);
		item.setPrize(1.29);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 225 ml");
		datasourceTmp.create(item);

		//Olio Extra Vergine Delicato Carapelli
		item.setItemId("DB124A8");
		item.setCategory(ItemDataSource.categories[5]);
		item.setNameAndMarket("Olio Extra Vergine Delicato Carapelli", DictionaryOpenHelperConad.getColumns()[0]);
		item.setBrand("Carapelli");
		item.setImage(R.drawable.olio_oliva);
		item.setPrize(5.20);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 1 litro");
		datasourceTmp.create(item);

		//Coca Cola in Bottiglia x2
		item.setItemId("DB124A9");
		item.setCategory(ItemDataSource.categories[0]);
		item.setNameAndMarket("Coca Cola in Bottiglia x2", DictionaryOpenHelperConad.getColumns()[0]);
		item.setBrand("Coca Cola");
		item.setImage(R.drawable.coca_cola);
		item.setPrize(2.99);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 1.5 litri x2");
		datasourceTmp.create(item);

		//Bagno Crema Dove
		item.setItemId("DB124B1");
		item.setCategory(ItemDataSource.categories[2]);
		item.setNameAndMarket("Bagno Crema Dove", DictionaryOpenHelperConad.getColumns()[0]);
		item.setBrand("Dove");
		item.setImage(R.drawable.bagno_crema);
		item.setPrize(2.99);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 0.5 litri");
		datasourceTmp.create(item);

		//Olio di Semi per Friggere Friol
		item.setItemId("DB124B2");
		item.setCategory(ItemDataSource.categories[5]);
		item.setNameAndMarket("Olio di Semi per Friggere Friol", DictionaryOpenHelperConad.getColumns()[0]);
		item.setBrand("Friol");
		item.setImage(R.drawable.olio_semi);
		item.setPrize(2.39);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 1 litro");
		datasourceTmp.create(item);

		//Birra in Bottiglia Heineken
		item.setItemId("DB124B3");
		item.setCategory(ItemDataSource.categories[0]);
		item.setNameAndMarket("Birra in Bottiglia Heineken", DictionaryOpenHelperConad.getColumns()[0]);
		item.setBrand("Heineken");
		item.setImage(R.drawable.birra);
		item.setPrize(2.69);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 33 cl x3");
		datasourceTmp.create(item);

		//Caffe Lavazza Qualita' Rossa x2
		item.setItemId("DB124B4");
		item.setCategory(ItemDataSource.categories[1]);
		item.setNameAndMarket("Caffe Lavazza Qualita' Rossa x2", DictionaryOpenHelperConad.getColumns()[0]);
		item.setBrand("Lavazza");
		item.setImage(R.drawable.caffe);
		item.setPrize(5.99);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 250 gr x2");
		datasourceTmp.create(item);

		//Acqua Naturale Levissima x6
		item.setItemId("DB124B5");
		item.setCategory(ItemDataSource.categories[0]);
		item.setNameAndMarket("Acqua Naturale Levissima x6", DictionaryOpenHelperConad.getColumns()[0]);
		item.setBrand("Levissima");
		item.setImage(R.drawable.levissima);
		item.setPrize(2.94);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 1.5 litro x6");
		datasourceTmp.create(item);

		//Biscotti Macine Mulino Bianco
		item.setItemId("DB124B6");
		item.setCategory(ItemDataSource.categories[1]);
		item.setNameAndMarket("Biscotti Macine Mulino Bianco", DictionaryOpenHelperConad.getColumns()[0]);
		item.setBrand("Mulino Bianco");
		item.setImage(R.drawable.biscotti_macine);
		item.setPrize(2.79);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 800 gr");
		datasourceTmp.create(item);

		//Cioccolato al Latte Milka
		item.setItemId("DB124B7");
		item.setCategory(ItemDataSource.categories[6]);
		item.setNameAndMarket("Cioccolato al Latte Milka", DictionaryOpenHelperConad.getColumns()[0]);
		item.setBrand("Milka");
		item.setImage(R.drawable.cioccolata);
		item.setPrize(1.45);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 100 gr");
		datasourceTmp.create(item);

		//Pane PanBauletto Bianco Mulino Bianco
		item.setItemId("DB124B8");
		item.setCategory(ItemDataSource.categories[3]);
		item.setNameAndMarket("Pane PanBauletto Bianco Mulino Bianco", DictionaryOpenHelperConad.getColumns()[0]);
		item.setBrand("Mulino Bianco");
		item.setImage(R.drawable.pane_bauletto);
		item.setPrize(1.09);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 400 gr");
		datasourceTmp.create(item);

		//Detersivo Piatti al Limone Nelsen
		item.setItemId("DB124B9");
		item.setCategory(ItemDataSource.categories[2]);
		item.setNameAndMarket("Detersivo Piatti al Limone Nelsen", DictionaryOpenHelperConad.getColumns()[0]);
		item.setBrand("Nelsen");
		item.setImage(R.drawable.detersivo_piatti);
		item.setPrize(2.19);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 1 litro");
		datasourceTmp.create(item);

		//Deodorante Spray Natural Dry Neutro Roberts
		item.setItemId("DB124C1");
		item.setCategory(ItemDataSource.categories[2]);
		item.setNameAndMarket("Deodorante Spray Natural Dry Neutro Roberts", DictionaryOpenHelperConad.getColumns()[0]);
		item.setBrand("Neutro Roberts");
		item.setImage(R.drawable.deodorante);
		item.setPrize(3.49);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 150 ml");
		datasourceTmp.create(item);

		//Nutella Ferrero
		item.setItemId("DB124C2");
		item.setCategory(ItemDataSource.categories[1]);
		item.setNameAndMarket("Nutella Ferrero", DictionaryOpenHelperConad.getColumns()[0]);
		item.setBrand("Ferrero");
		item.setImage(R.drawable.nutella);
		item.setPrize(4.39);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 630 gr");
		datasourceTmp.create(item);

		//Carta Igienica Veli Maxi Rotoli Regina 4pz
		item.setItemId("DB124C3");
		item.setCategory(ItemDataSource.categories[2]);
		item.setNameAndMarket("Carta Igienica Veli Maxi Rotoli Regina 4pz", DictionaryOpenHelperConad.getColumns()[0]);
		item.setBrand("Regina");
		item.setImage(R.drawable.carta_igienica);
		item.setPrize(3.99);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 4pz");
		datasourceTmp.create(item);

		//Tonno in Scatola Rio Mare x2
		item.setItemId("DB124C4");
		item.setCategory(ItemDataSource.categories[4]);
		item.setNameAndMarket("Tonno in Scatola Rio Mare x2", DictionaryOpenHelperConad.getColumns()[0]);
		item.setBrand("Rio Mare");
		item.setImage(R.drawable.tonno);
		item.setPrize(3.99);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 160 gr x2");
		datasourceTmp.create(item);

		//Patatine Classiche San Carlo
		item.setItemId("DB124C5");
		item.setCategory(ItemDataSource.categories[6]);
		item.setNameAndMarket("Patatine Classiche San Carlo", DictionaryOpenHelperConad.getColumns()[0]);
		item.setBrand("San Carlo");
		item.setImage(R.drawable.patatine);
		item.setPrize(1.79);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 180 gr");
		datasourceTmp.create(item);

		//Saponetta per Mani Dove x2
		item.setItemId("DB124C6");
		item.setCategory(ItemDataSource.categories[2]);
		item.setNameAndMarket("Saponetta per Mani Dove x2", DictionaryOpenHelperConad.getColumns()[0]);
		item.setBrand("Dove");
		item.setImage(R.drawable.saponetta);
		item.setPrize(1.95);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 100 gr x2");
		datasourceTmp.create(item);

		//Shampoo 2in1 Capelli Normali Fructis
		item.setItemId("DB124C7");
		item.setCategory(ItemDataSource.categories[2]);
		item.setNameAndMarket("Shampoo 2in1 Capelli Normali Fructis", DictionaryOpenHelperConad.getColumns()[0]);
		item.setBrand("Fructis");
		item.setImage(R.drawable.shampoo);
		item.setPrize(3.39);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 250 ml");
		datasourceTmp.create(item);

		//The' Earl Gray Twinings
		item.setItemId("DB124C8");
		item.setCategory(ItemDataSource.categories[1]);
		item.setNameAndMarket("The' Earl Gray Twinings", DictionaryOpenHelperConad.getColumns()[0]);
		item.setBrand("Twinings");
		item.setImage(R.drawable.the);
		item.setPrize(2.99);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 25pz");
		datasourceTmp.create(item);

		//Zucchero Semolato Finissimo Zefiro
		item.setItemId("DB124C9");
		item.setCategory(ItemDataSource.categories[5]);
		item.setNameAndMarket("Zucchero Semolato Finissimo Zefiro", DictionaryOpenHelperConad.getColumns()[0]);
		item.setBrand("Zefiro");
		item.setImage(R.drawable.zucchero_zefiro);
		item.setPrize(1.69);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 1 kg");
		datasourceTmp.create(item);

		//Marmellata alle Pesche Zuegg
		item.setItemId("DB124D1");
		item.setCategory(ItemDataSource.categories[1]);
		item.setNameAndMarket("Marmellata alle Pesche Zuegg", DictionaryOpenHelperConad.getColumns()[0]);
		item.setBrand("Zuegg");
		item.setImage(R.drawable.marmellata_pesche);
		item.setPrize(2.39);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 320 gr");
		datasourceTmp.create(item);

		//Latte Zymil Alta Digeribilita' Parzialmente Scremato Parmalat
		item.setItemId("DB124D2");
		item.setCategory(ItemDataSource.categories[1]);
		item.setNameAndMarket("Latte Zymil Alta Digeribilita' Parzialmente Scremato Parmalat", DictionaryOpenHelperConad.getColumns()[0]);
		item.setBrand("Parmalat");
		item.setImage(R.drawable.latte_zymil);
		item.setPrize(1.67);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 1 litro");
		datasourceTmp.create(item);

		tmp = datasourceTmp.findAll();

		datasourceTmp.close();

		return tmp;		
	}

	private List<ItemDB> initializeCraiDB() {
		List<ItemDB> tmp = new ArrayList<ItemDB>(); 

		ItemDataSource datasourceTmp = new ItemDataSource(this, DictionaryOpenHelperCrai.getColumns());
		datasourceTmp.open();

		ItemDB item = new ItemDB();

		//Actilift Dash
		item.setItemId("DB125A1");
		item.setCategory(ItemDataSource.categories[2]);
		item.setNameAndMarket("Actilift Dash", DictionaryOpenHelperCrai.getColumns()[0]);
		item.setBrand("Dash");
		item.setImage(R.drawable.dash);
		item.setPrize(6.59);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 1.5 litri");
		datasourceTmp.create(item);	


		//Farina di Grano Tenero Barilla
		item.setItemId("DB125A2");
		item.setCategory(ItemDataSource.categories[5]);
		item.setNameAndMarket("Farina di Grano Tenero Barilla", DictionaryOpenHelperCrai.getColumns()[0]);
		item.setBrand("Barilla");
		item.setImage(R.drawable.farina);
		item.setPrize(0.51);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 1 Kg");
		datasourceTmp.create(item);

		//Pennette Rigate Barilla
		item.setItemId("DB125A3");
		item.setCategory(ItemDataSource.categories[3]);
		item.setNameAndMarket("Pennette Rigate Barilla", DictionaryOpenHelperCrai.getColumns()[0]);
		item.setBrand("Barilla");
		item.setImage(R.drawable.penne);
		item.setPrize(0.48);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 500 gr");
		datasourceTmp.create(item);

		//Pesto Barilla
		item.setItemId("DB125A4");
		item.setCategory(ItemDataSource.categories[4]);
		item.setNameAndMarket("Pesto Barilla", DictionaryOpenHelperCrai.getColumns()[0]);
		item.setBrand("Barilla");
		item.setImage(R.drawable.pesto);
		item.setPrize(2.09);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 190 gr");
		datasourceTmp.create(item);

		//Spaghetti N.5 Barilla
		item.setItemId("DB125A5");
		item.setCategory(ItemDataSource.categories[3]);
		item.setNameAndMarket("Spaghetti N.5 Barilla", DictionaryOpenHelperCrai.getColumns()[0]);
		item.setBrand("Barilla");
		item.setImage(R.drawable.spaghetti);
		item.setPrize(0.79);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 500 gr");
		datasourceTmp.create(item);

		//Sugo al Basilico con Pomodorini Datterini Barilla
		item.setItemId("DB125A6");
		item.setCategory(ItemDataSource.categories[4]);
		item.setNameAndMarket("Sugo al Basilico con Pomodorini Datterini Barilla", DictionaryOpenHelperCrai.getColumns()[0]);
		item.setBrand("Barilla");
		item.setImage(R.drawable.pomarola);
		item.setPrize(1.39);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 400 gr");
		datasourceTmp.create(item);

		//Maionese Calve'
		item.setItemId("DB125A7");
		item.setCategory(ItemDataSource.categories[4]);
		item.setNameAndMarket("Maionese Calve'", DictionaryOpenHelperCrai.getColumns()[0]);
		item.setBrand("Calve'");
		item.setImage(R.drawable.maionese);
		item.setPrize(1.39);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 225 ml");
		datasourceTmp.create(item);

		//Olio Extra Vergine Delicato Carapelli
		item.setItemId("DB125A8");
		item.setCategory(ItemDataSource.categories[5]);
		item.setNameAndMarket("Olio Extra Vergine Delicato Carapelli", DictionaryOpenHelperCrai.getColumns()[0]);
		item.setBrand("Carapelli");
		item.setImage(R.drawable.olio_oliva);
		item.setPrize(5.25);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 1 litro");
		datasourceTmp.create(item);

		//Coca Cola in Bottiglia x2
		item.setItemId("DB125A9");
		item.setCategory(ItemDataSource.categories[0]);
		item.setNameAndMarket("Coca Cola in Bottiglia x2", DictionaryOpenHelperCrai.getColumns()[0]);
		item.setBrand("Coca Cola");
		item.setImage(R.drawable.coca_cola);
		item.setPrize(3.19);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 1.5 litri x2");
		datasourceTmp.create(item);

		//Bagno Crema Dove
		item.setItemId("DB125B1");
		item.setCategory(ItemDataSource.categories[2]);
		item.setNameAndMarket("Bagno Crema Dove", DictionaryOpenHelperCrai.getColumns()[0]);
		item.setBrand("Dove");
		item.setImage(R.drawable.bagno_crema);
		item.setPrize(2.99);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 0.5 litri");
		datasourceTmp.create(item);

		//Olio di Semi per Friggere Friol
		item.setItemId("DB125B2");
		item.setCategory(ItemDataSource.categories[5]);
		item.setNameAndMarket("Olio di Semi per Friggere Friol", DictionaryOpenHelperCrai.getColumns()[0]);
		item.setBrand("Friol");
		item.setImage(R.drawable.olio_semi);
		item.setPrize(2.05);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 1 litro");
		datasourceTmp.create(item);

		//Birra in Bottiglia Heineken
		item.setItemId("DB125B3");
		item.setCategory(ItemDataSource.categories[0]);
		item.setNameAndMarket("Birra in Bottiglia Heineken", DictionaryOpenHelperCrai.getColumns()[0]);
		item.setBrand("Heineken");
		item.setImage(R.drawable.birra);
		item.setPrize(3.29);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 33 cl x3");
		datasourceTmp.create(item);

		//Caffe Lavazza Qualita' Rossa x2
		item.setItemId("DB125B4");
		item.setCategory(ItemDataSource.categories[1]);
		item.setNameAndMarket("Caffe Lavazza Qualita' Rossa x2", DictionaryOpenHelperCrai.getColumns()[0]);
		item.setBrand("Lavazza");
		item.setImage(R.drawable.caffe);
		item.setPrize(5.99);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 250 gr x2");
		datasourceTmp.create(item);

		//Acqua Naturale Levissima x6
		item.setItemId("DB125B5");
		item.setCategory(ItemDataSource.categories[0]);
		item.setNameAndMarket("Acqua Naturale Levissima x6", DictionaryOpenHelperCrai.getColumns()[0]);
		item.setBrand("Levissima");
		item.setImage(R.drawable.levissima);
		item.setPrize(3.89);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 1.5 litro x6");
		datasourceTmp.create(item);

		//Biscotti Macine Mulino Bianco
		item.setItemId("DB125B6");
		item.setCategory(ItemDataSource.categories[1]);
		item.setNameAndMarket("Biscotti Macine Mulino Bianco", DictionaryOpenHelperCrai.getColumns()[0]);
		item.setBrand("Mulino Bianco");
		item.setImage(R.drawable.biscotti_macine);
		item.setPrize(2.79);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 800 gr");
		datasourceTmp.create(item);

		//Cioccolato al Latte Milka
		item.setItemId("DB125B7");
		item.setCategory(ItemDataSource.categories[6]);
		item.setNameAndMarket("Cioccolato al Latte Milka", DictionaryOpenHelperCrai.getColumns()[0]);
		item.setBrand("Milka");
		item.setImage(R.drawable.cioccolata);
		item.setPrize(2.26);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 100 gr");
		datasourceTmp.create(item);

		//Pane PanBauletto Bianco Mulino Bianco
		item.setItemId("DB125B8");
		item.setCategory(ItemDataSource.categories[3]);
		item.setNameAndMarket("Pane PanBauletto Bianco Mulino Bianco", DictionaryOpenHelperCrai.getColumns()[0]);
		item.setBrand("Mulino Bianco");
		item.setImage(R.drawable.pane_bauletto);
		item.setPrize(1.19);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 400 gr");
		datasourceTmp.create(item);

		//Detersivo Piatti al Limone Nelsen
		item.setItemId("DB125B9");
		item.setCategory(ItemDataSource.categories[2]);
		item.setNameAndMarket("Detersivo Piatti al Limone Nelsen", DictionaryOpenHelperCrai.getColumns()[0]);
		item.setBrand("Nelsen");
		item.setImage(R.drawable.detersivo_piatti);
		item.setPrize(1.79);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 1 litro");
		datasourceTmp.create(item);

		//Deodorante Spray Natural Dry Neutro Roberts
		item.setItemId("DB125C1");
		item.setCategory(ItemDataSource.categories[2]);
		item.setNameAndMarket("Deodorante Spray Natural Dry Neutro Roberts", DictionaryOpenHelperCrai.getColumns()[0]);
		item.setBrand("Neutro Roberts");
		item.setImage(R.drawable.deodorante);
		item.setPrize(3.63);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 150 ml");
		datasourceTmp.create(item);

		//Nutella Ferrero
		item.setItemId("DB125C2");
		item.setCategory(ItemDataSource.categories[1]);
		item.setNameAndMarket("Nutella Ferrero", DictionaryOpenHelperCrai.getColumns()[0]);
		item.setBrand("Ferrero");
		item.setImage(R.drawable.nutella);
		item.setPrize(3.39);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 630 gr");
		datasourceTmp.create(item);

		//Carta Igienica Veli Maxi Rotoli Regina 4pz
		item.setItemId("DB125C3");
		item.setCategory(ItemDataSource.categories[2]);
		item.setNameAndMarket("Carta Igienica Veli Maxi Rotoli Regina 4pz", DictionaryOpenHelperCrai.getColumns()[0]);
		item.setBrand("Regina");
		item.setImage(R.drawable.carta_igienica);
		item.setPrize(3.59);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 4pz");
		datasourceTmp.create(item);

		//Tonno in Scatola Rio Mare x2
		item.setItemId("DB125C4");
		item.setCategory(ItemDataSource.categories[4]);
		item.setNameAndMarket("Tonno in Scatola Rio Mare x2", DictionaryOpenHelperCrai.getColumns()[0]);
		item.setBrand("Rio Mare");
		item.setImage(R.drawable.tonno);
		item.setPrize(4.79);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 160 gr x2");
		datasourceTmp.create(item);

		//Patatine Classiche San Carlo
		item.setItemId("DB125C5");
		item.setCategory(ItemDataSource.categories[6]);
		item.setNameAndMarket("Patatine Classiche San Carlo", DictionaryOpenHelperCrai.getColumns()[0]);
		item.setBrand("San Carlo");
		item.setImage(R.drawable.patatine);
		item.setPrize(2.19);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 180 gr");
		datasourceTmp.create(item);

		//Saponetta per Mani Dove x2
		item.setItemId("DB125C6");
		item.setCategory(ItemDataSource.categories[2]);
		item.setNameAndMarket("Saponetta per Mani Dove x2", DictionaryOpenHelperCrai.getColumns()[0]);
		item.setBrand("Dove");
		item.setImage(R.drawable.saponetta);
		item.setPrize(1.99);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 100 gr x2");
		datasourceTmp.create(item);

		//Shampoo 2in1 Capelli Normali Fructis
		item.setItemId("DB125C7");
		item.setCategory(ItemDataSource.categories[2]);
		item.setNameAndMarket("Shampoo 2in1 Capelli Normali Fructis", DictionaryOpenHelperCrai.getColumns()[0]);
		item.setBrand("Fructis");
		item.setImage(R.drawable.shampoo);
		item.setPrize(3.32);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 250 ml");
		datasourceTmp.create(item);

		//The' Earl Gray Twinings
		item.setItemId("DB125C8");
		item.setCategory(ItemDataSource.categories[1]);
		item.setNameAndMarket("The' Earl Gray Twinings", DictionaryOpenHelperCrai.getColumns()[0]);
		item.setBrand("Twinings");
		item.setImage(R.drawable.the);
		item.setPrize(2.79);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 25pz");
		datasourceTmp.create(item);

		//Zucchero Semolato Eridania
		item.setItemId("DB125C9");
		item.setCategory(ItemDataSource.categories[5]);
		item.setNameAndMarket("Zucchero Semolato Eridania", DictionaryOpenHelperCrai.getColumns()[0]);
		item.setBrand("Eridania");
		item.setImage(R.drawable.zucchero_eridania);
		item.setPrize(0.99);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 1 kg");
		datasourceTmp.create(item);

		//Marmellata alle Pesche Zuegg
		item.setItemId("DB125D1");
		item.setCategory(ItemDataSource.categories[1]);
		item.setNameAndMarket("Marmellata alle Pesche Zuegg", DictionaryOpenHelperCrai.getColumns()[0]);
		item.setBrand("Zuegg");
		item.setImage(R.drawable.marmellata_pesche);
		item.setPrize(2.15);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 320 gr");
		datasourceTmp.create(item);

		//Latte Zymil Alta Digeribilita' Parzialmente Scremato Parmalat
		item.setItemId("DB125D2");
		item.setCategory(ItemDataSource.categories[1]);
		item.setNameAndMarket("Latte Zymil Alta Digeribilita' Parzialmente Scremato Parmalat", DictionaryOpenHelperCrai.getColumns()[0]);
		item.setBrand("Parmalat");
		item.setImage(R.drawable.latte_zymil);
		item.setPrize(1.85);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 1 litro");
		datasourceTmp.create(item);

		//Fette Biscottate Dorate Mulino Bianco
		item.setItemId("DB125D3");
		item.setCategory(ItemDataSource.categories[1]);
		item.setNameAndMarket("Fette Biscottate Dorate Mulino Bianco", DictionaryOpenHelperCrai.getColumns()[0]);
		item.setBrand("Mulino Bianco");
		item.setImage(R.drawable.fette_biscottate);
		item.setPrize(1.39);
		item.setDiscount(0);
		item.setDesciption("Descrizione: 80pz");
		datasourceTmp.create(item);

		tmp = datasourceTmp.findAll();

		datasourceTmp.close();

		return tmp;		
	}

	public void refreshDisplay() {
		setContentView(R.layout.cross_shop);

		datasource = new ItemDataSource(this, DictionaryOpenHelperCrossDB.getColumns());
		datasource.open();

		tv_counter = (TextView) findViewById(R.id.txtCount);

		searchBar = (EditText) findViewById(R.id.view5);
		searchBar.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				boolean handled = false;
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {					
					Intent intent = new Intent(getApplicationContext(), ListViewPrototype.class);
					intent.putExtra("com.smartshop.ListViewPrototype.filter", "name");
					intent.putExtra("com.smartshop.ListViewPrototype.name", v.getText().toString());
					startActivityForResult(intent, SEARCH_ITEM);
					handled = true;
				}
				return handled;
			}
		});

		ib_cart = (ImageView) findViewById(R.id.view3);
		ib_cart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, CartView.class);
				intent.putIntegerArrayListExtra("com.smartshop.CartView.cart", (ArrayList<Integer>) selectedItems);
				startActivityForResult(intent, CHECK_OUT);
			}
		});

		categories = (Button) findViewById(R.id.btn_categories);
		lastshop = (Button) findViewById(R.id.btn_lastshop);

		categories.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), Category.class);
				startActivity(intent);				
			}
		});

		login = (Button) findViewById(R.id.button_login);

		//NOTA: Inizializzazione Database
		boolean initialize = false;

		if(initialize) {
			deleteDBs();
			initializeDBs();
		}

		SharedPreferences prefs = getSharedPreferences("smartshop_pref", MODE_PRIVATE);
		String savedUsername = prefs.getString("username", null);
		String savedPassword = prefs.getString("password", null);

		MainCategoryArrayAdapter mainCategoryArrayAdapter = new MainCategoryArrayAdapter(this);
		List<ItemDB> tmp = datasource.findAll();
		lastshopItems = new ArrayList<ItemDB>();
		lastshopItems.add(tmp.get(0));
		int j = 0;
		for(int i = 1; i < tmp.size(); i++) {
			if(!lastshopItems.get(j).getName().equals(tmp.get(i).getName())) {
				lastshopItems.add(tmp.get(i));
				j++;
			}
		}

		MainLastshopArrayAdapter mainLastshopArrayAdapter = new MainLastshopArrayAdapter(this, lastshopItems);
		advertiseItems = new ArrayList<ItemDB>();
		for(int i = 1; i < tmp.size(); i++) {
			if(tmp.get(i).getDiscount() > 0) {
				advertiseItems.add(tmp.get(i));
			}
		}
		MainAdvertiseArrayAdapter mainAdvertiseArrayAdapter  = new MainAdvertiseArrayAdapter (this, advertiseItems);

		if((savedUsername != null) && (savedPassword != null)) {
			HorizontalListView listview1 = (HorizontalListView) findViewById(R.id.listview1);
			listview1.setAdapter(mainCategoryArrayAdapter);
			HorizontalListView listview2 = (HorizontalListView) findViewById(R.id.listview2);
			listview2.setAdapter(mainLastshopArrayAdapter);
			HorizontalListView listview3 = (HorizontalListView) findViewById(R.id.listview3);
			listview3.setAdapter(mainAdvertiseArrayAdapter);

			logout();

		}else {
			HorizontalListView listview1 = (HorizontalListView) findViewById(R.id.listview1);
			listview1.setAdapter(mainCategoryArrayAdapter);
			HorizontalListView listview2 = (HorizontalListView) findViewById(R.id.listview2);
			listview2.setAdapter(mainLastshopArrayAdapter);
			HorizontalListView listview3 = (HorizontalListView) findViewById(R.id.listview3);
			listview3.setAdapter(mainAdvertiseArrayAdapter);

			lastshop.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getApplicationContext(), UsersShop.class);
					//NOTA: MODIFICARE
					startActivity(intent);				
				}
			});

			login.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getApplicationContext(), Dialog.class);
					startActivityForResult(intent, LOG_IN); 
				}
			});
		}

		advertise = (Button) findViewById(R.id.btn_advertise);
		advertise.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), LastShop.class);
				intent.putParcelableArrayListExtra("com.smartshop.Main.lastshopItems", (ArrayList<ItemDB>) advertiseItems);
				startActivity(intent);
			}
		});

		try {
			if(!selectedItems.isEmpty()) {
				tv_counter.setText("" + selectedItems.size());
			}
		} catch (Exception e) {

		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		datasource.open();
		refreshDisplay();
	}

	@Override
	protected void onPause() {
		super.onPause();
		datasource.close();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		datasource.close();
	}
}
