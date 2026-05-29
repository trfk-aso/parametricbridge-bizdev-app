package parametricbridge.bizdev.app.di

import androidx.room.Room
import parametricbridge.bizdev.app.data.database.KBPMHDatabase
import org.koin.dsl.module

private const val DB_NAME = "kbpmh_db"

val databaseModule = module {
    single {
        Room.databaseBuilder(
            context = get(),
            klass = KBPMHDatabase::class.java,
        name = DB_NAME
        ).build()
    }

    single { get<KBPMHDatabase>().bookingDao()}

}