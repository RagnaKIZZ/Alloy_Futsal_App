package com.amd.alloyfutsalapp.ui.fieldDetails

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.amd.alloyfutsalapp.FieldRepo
import com.amd.alloyfutsalapp.R
import com.amd.alloyfutsalapp.adapter.DetailPagerAdapter
import com.amd.alloyfutsalapp.databinding.ActivityFieldDetailsNewBinding
import com.amd.alloyfutsalapp.db.FieldDatabases
import com.amd.alloyfutsalapp.factory.DetailsProviderFactory
import com.amd.alloyfutsalapp.model.DataItem
import com.amd.alloyfutsalapp.model.FieldTypeItem
import com.amd.alloyfutsalapp.utils.Utility
import com.amd.alloyfutsalapp.viewModel.DetailsViewModel

class FieldDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFieldDetailsNewBinding
    private lateinit var viewModel: DetailsViewModel
    private lateinit var dataItem: DataItem
    private lateinit var bookmarkMenu: MenuItem
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFieldDetailsNewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        context = this
        val repo = FieldRepo(FieldDatabases(this))
        val factory = DetailsProviderFactory(repo)
        viewModel = ViewModelProvider(this, factory).get(DetailsViewModel::class.java)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            binding.toolbar
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = ""
        }

        setView()
    }

    @SuppressLint("SetTextI18n")
    private fun setView() {
        intent.getSerializableExtra("data")?.let {
            dataItem = it as DataItem
            dataItem.let { data ->
                binding.incPager.apply {
                    pagerImg.adapter = DetailPagerAdapter(context, data.imgSrc)
                    tabImg.setupWithViewPager(pagerImg)
                }
                binding.incContent.apply {
                    txtTitleField.text = data.nameField
                    txtAddress.text = data.address
                    txtAmountField.text =
                        "${if (data.isIndoor == "1") "Ya" else "Tidak"}/${data.amountField} Lapangan"
                    txtFacility.text = data.facility
                    txtPrice.text = getListFieldType(data.fieldType)
                    txtOperationalHour.text = data.operationalHour
                    fabMaps.setOnClickListener {
                        try {
                            val gmaps =
                                Uri.parse("geo:" + data.latLng + "?q=" + Uri.encode(data.address))
//                                Uri.parse("http://maps.google.com/maps?daddr=" + data.latLng)
                            val i = Intent(Intent.ACTION_VIEW, gmaps)
                            i.setPackage("com.google.android.apps.maps")
                            if (i.resolveActivity(packageManager) != null) {
                                startActivity(i)
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    btnBook.setOnClickListener {
                        try {
                            startActivity(
                                Intent(
                                    Intent.ACTION_DIAL,
                                    Uri.fromParts("tel", data.phone, null)
                                )
                            )
                        } catch (e: java.lang.Exception) {
                            Log.d(
                                "Phone error",
                                "onClick: openmessage" + e.message
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_bookmark, menu)
        bookmarkMenu = menu?.findItem(R.id.menuBookmark)!!
        checkBookmark()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }

            R.id.menuBookmark -> {
                actionBookmark()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getTypeOfField(param: String) : String{
        return when(param){
            "1" -> "Lapangan Plesteran"
            "2" -> "Lapangan Vinyl"
            else -> "Lapangan Sintetis"
        }
    }

    private fun getListFieldType(fieldType: List<FieldTypeItem>): String {
        val field = StringBuilder()
        if (fieldType.size > 1) {
            for (i in fieldType.indices) {
                field.append(getTypeOfField(fieldType[i].typeField)+" "+ Utility.KeRupiah(fieldType[i].price))
                if (i < fieldType.size - 1) {
                    field.append("\n")
                }
            }
            return field.toString()
        }
        return getTypeOfField(fieldType[0].typeField)+" "+ Utility.KeRupiah(fieldType[0].price)
    }

    private fun actionBookmark() {
        val intent = Intent()
        if (dataItem.isBookmarked) {
            dataItem.isBookmarked = false
            viewModel.deleteField(dataItem)
            Utility.showSnackBar(binding.root, getString(R.string.lapangan_terhapus))
            intent.putExtra("param", false)
            setResult(RESULT_OK, intent)
        } else {
            dataItem.isBookmarked = true
            viewModel.upsertField(dataItem)
            Utility.showSnackBar(binding.root, getString(R.string.lapangan_tersimpan))
            intent.putExtra("param", true)
            setResult(RESULT_OK, intent)
        }
        checkBookmark()
    }

    private fun checkBookmark() {
        if (dataItem.isBookmarked) {
            bookmarkMenu.setIcon(R.drawable.ic_baseline_bookmark_24)
        } else {
            bookmarkMenu.setIcon(R.drawable.ic_baseline_bookmark_border_24)
        }
    }
}