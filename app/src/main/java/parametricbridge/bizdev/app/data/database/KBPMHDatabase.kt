package parametricbridge.bizdev.app.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import parametricbridge.bizdev.app.data.dao.BookingDao
import parametricbridge.bizdev.app.data.database.converter.Converters
import parametricbridge.bizdev.app.data.entity.BookingEntity

@Database(
    entities = [BookingEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class KBPMHDatabase : RoomDatabase() {

    abstract fun bookingDao(): BookingDao
}

