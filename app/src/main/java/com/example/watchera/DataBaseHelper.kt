package com.example.Watchera.Database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.watchera.Domain.BannerModel
import com.example.watchera.Domain.CategoryModel
import com.example.watchera.Domain.ItemsModel


class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "watchera.db"
        private const val DATABASE_VERSION = 1

        // Banner Table
        private const val TABLE_BANNER = "Banner"
        private const val COLUMN_BANNER_ID = "id"
        private const val COLUMN_BANNER_URL = "url"

        // Category Table
        private const val TABLE_CATEGORY = "Category"
        private const val COLUMN_CATEGORY_ID = "id"
        private const val COLUMN_CATEGORY_TITLE = "title"

        // Popular Table
        private const val TABLE_POPULAR = "Popular"
        private const val COLUMN_POPULAR_ID = "id"
        private const val COLUMN_POPULAR_DESCRIPTION = "description"
        private const val COLUMN_POPULAR_PRICE = "price"
        private const val COLUMN_POPULAR_RATING = "rating"
        private const val COLUMN_POPULAR_TITLE = "title"
        private const val COLUMN_POPULAR_THUMBNAIL = "thumbnail"

        // PopularImages Table
        private const val TABLE_POPULAR_IMAGES = "PopularImages"
        private const val COLUMN_POPULAR_IMAGES_ID = "id"
        private const val COLUMN_POPULAR_IMAGES_POPULAR_ID = "popular_id"
        private const val COLUMN_POPULAR_IMAGES_PIC_URL = "pic_url"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createBannerTable = """
            CREATE TABLE $TABLE_BANNER (
                $COLUMN_BANNER_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_BANNER_URL TEXT NOT NULL
            )
        """.trimIndent()

        val createCategoryTable = """
            CREATE TABLE $TABLE_CATEGORY (
                $COLUMN_CATEGORY_ID INTEGER PRIMARY KEY,
                $COLUMN_CATEGORY_TITLE TEXT NOT NULL
            )
        """.trimIndent()

        val createPopularTable = """
            CREATE TABLE $TABLE_POPULAR (
                $COLUMN_POPULAR_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_POPULAR_DESCRIPTION TEXT NOT NULL,
                $COLUMN_POPULAR_PRICE REAL NOT NULL,
                $COLUMN_POPULAR_RATING REAL NOT NULL,
                $COLUMN_POPULAR_TITLE TEXT NOT NULL,
                $COLUMN_POPULAR_THUMBNAIL TEXT NOT NULL
            )
        """.trimIndent()

        val createPopularImagesTable = """
            CREATE TABLE $TABLE_POPULAR_IMAGES (
                $COLUMN_POPULAR_IMAGES_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_POPULAR_IMAGES_POPULAR_ID INTEGER NOT NULL,
                $COLUMN_POPULAR_IMAGES_PIC_URL TEXT NOT NULL,
                FOREIGN KEY ($COLUMN_POPULAR_IMAGES_POPULAR_ID) REFERENCES $TABLE_POPULAR($COLUMN_POPULAR_ID)
            )
        """.trimIndent()

        db?.execSQL(createBannerTable)
        db?.execSQL(createCategoryTable)
        db?.execSQL(createPopularTable)
        db?.execSQL(createPopularImagesTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_BANNER")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_CATEGORY")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_POPULAR")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_POPULAR_IMAGES")
        onCreate(db)
    }

    // Insert Banner
    fun insertBanner(url: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_BANNER_URL, url)
        }
        return db.insert(TABLE_BANNER, null, values)
    }

    // Insert Category
    fun insertCategory(id: Int, title: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_CATEGORY_ID, id)
            put(COLUMN_CATEGORY_TITLE, title)
        }
        return db.insert(TABLE_CATEGORY, null, values)
    }

    // Insert Popular Item
    fun insertPopular(item: ItemsModel): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_POPULAR_DESCRIPTION, item.description)
            put(COLUMN_POPULAR_PRICE, item.price)
            put(COLUMN_POPULAR_RATING, item.rating)
            put(COLUMN_POPULAR_TITLE, item.title)
            put(COLUMN_POPULAR_THUMBNAIL, item.thumbnail)
        }
        return db.insert(TABLE_POPULAR, null, values)
    }

    // Insert Popular Image
    fun insertPopularImage(popularId: Long, picUrl: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_POPULAR_IMAGES_POPULAR_ID, popularId)
            put(COLUMN_POPULAR_IMAGES_PIC_URL, picUrl)
        }
        return db.insert(TABLE_POPULAR_IMAGES, null, values)
    }

    fun isDataPreloaded(): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT COUNT(*) FROM $TABLE_POPULAR", null)
        var count = 0
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0)
        }
        cursor.close()
        return count > 0
    }

    // Fetch All Banners
    fun getAllBanners(): List<BannerModel> {
        val banners = mutableListOf<BannerModel>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_BANNER"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            val url = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BANNER_URL))
            banners.add(BannerModel(url))
        }
        cursor.close()
        return banners
    }

    // Fetch All Categories
    fun getAllCategories(): List<CategoryModel> {
        val categories = mutableListOf<CategoryModel>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_CATEGORY"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY_TITLE))
            categories.add(CategoryModel(title, id))
        }
        cursor.close()
        return categories
    }

    // Fetch All Popular Items
    fun getAllPopularItems(): List<ItemsModel> {
        val popularItems = mutableListOf<ItemsModel>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_POPULAR"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_POPULAR_ID))
            val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_POPULAR_DESCRIPTION))
            val price = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_POPULAR_PRICE))
            val rating = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_POPULAR_RATING))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_POPULAR_TITLE))
            val thumbnail = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_POPULAR_THUMBNAIL))

            // Fetch associated images
            val imagesQuery = "SELECT $COLUMN_POPULAR_IMAGES_PIC_URL FROM $TABLE_POPULAR_IMAGES WHERE $COLUMN_POPULAR_IMAGES_POPULAR_ID = ?"
            val imagesCursor = db.rawQuery(imagesQuery, arrayOf(id.toString()))
            val picUrls = mutableListOf<String>()
            while (imagesCursor.moveToNext()) {
                picUrls.add(imagesCursor.getString(imagesCursor.getColumnIndexOrThrow(COLUMN_POPULAR_IMAGES_PIC_URL)))
            }
            imagesCursor.close()

            popularItems.add(ItemsModel(title, description, thumbnail, ArrayList(picUrls), price, rating, 0, ""))
        }
        cursor.close()
        return popularItems
    }
}