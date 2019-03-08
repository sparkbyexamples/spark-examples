
import java.time.{LocalDate, Period}
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.time.ZoneId
val dateFormat = DateTimeFormatter.ofPattern("ddMMyyyy")
dateFormat.parse("").getTime
val da:LocalDate = LocalDate.parse("13041981",dateFormat)

val forDate = dateFormat.parse("13041981")

println("Date: "+forDate)
println("Date 2: "+da.toString)

val today = LocalDate.now

println("Now : "+today.format(dateFormat))

println("Years:"+ChronoUnit.YEARS.between(da,today))

println("Years:"+Period.between(da,today).getYears)

println("EpochDay:"+da.atStartOfDay(ZoneId.systemDefault).toInstant.getEpochSecond)
println("EpochDay:"+Calendar.getInstance().getTimeInMillis)


