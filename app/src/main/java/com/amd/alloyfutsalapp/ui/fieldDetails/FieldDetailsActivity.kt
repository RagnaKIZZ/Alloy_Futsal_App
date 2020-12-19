package com.amd.alloyfutsalapp.ui.fieldDetails

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.amd.alloyfutsalapp.FieldRepo
import com.amd.alloyfutsalapp.R
import com.amd.alloyfutsalapp.databinding.ActivityFieldDetailsBinding
import com.amd.alloyfutsalapp.db.FieldDatabases
import com.amd.alloyfutsalapp.factory.DetailsProviderFactory
import com.amd.alloyfutsalapp.model.DataItem
import com.amd.alloyfutsalapp.utils.Constants.Companion.IMG_URL
import com.amd.alloyfutsalapp.utils.Utility
import com.amd.alloyfutsalapp.viewModel.DetailsViewModel

class FieldDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFieldDetailsBinding
    private lateinit var viewModel: DetailsViewModel
    private lateinit var dataItem: DataItem
    private lateinit var bookmarkMenu: MenuItem
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFieldDetailsBinding.inflate(layoutInflater)
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

    private fun setView() {
        intent.getSerializableExtra("data")?.let {
            dataItem = it as DataItem
            dataItem.let { data ->
                binding.incContent.apply {
                    txtTitleField.text = data.nameField
                    txtAddress.text = data.address
                    Utility.loadImage(
                        this@FieldDetailsActivity,
                        IMG_URL + data.imgSrc[0].imgSrc,
                        binding.imgField
                    )
                    txtAmountField.text = data.amountField
                    txtFacility.text = data.facility
                    txtOperationalHour.text = data.operationalHour
                    txtPrice.text = "${Utility.KeRupiah(data.price)}/Jam"
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

                    btnBook.setOnClickListener { book ->
                        try {
                            val contact = "+6281317703568"
                            val url = "https://api.whatsapp.com/send?phone=$contact"
                            val pm = context.packageManager
                            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES)
                            val i = Intent(Intent.ACTION_VIEW)
                            i.data = Uri.parse(url)
                            context.startActivity(i)
                        }catch (e: java.lang.Exception){
                            Utility.showSnackBar(binding.root, "Aplikasi WhatsApp belum terinstal!")
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