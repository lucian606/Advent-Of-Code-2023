import java.util.*

fun getDayNumberFromToday() = Calendar.getInstance().apply { time = Date() }[Calendar.DAY_OF_MONTH]