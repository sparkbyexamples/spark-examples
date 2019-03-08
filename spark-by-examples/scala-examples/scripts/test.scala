import java.text.SimpleDateFormat
import java.util.Date

val asOfDateFormat = new SimpleDateFormat("yyyyMMdd")
val str = "file:/C:/Users/a03078a/Documents/DataFabric/Workspace/bureau-australia-data/DefaultListingExtract_Experian_20181008041614.txt"
println(str.lastIndexOf("Experian_"))
val dateStr = str.substring(str.lastIndexOf("Experian_")+9,str.lastIndexOf("Experian_")+9+8)
println("Extracted date:"+dateStr)
val parseDate = asOfDateFormat.parse(dateStr)
println("Parsed Date:"+parseDate)
val longDate = asOfDateFormat.parse(dateStr).getTime


println("Date in long:"+longDate)
val reformatDate:Date = new Date()
reformatDate.setTime(longDate);
println("Reformat Date:"+reformatDate)
val daStr = asOfDateFormat.format(reformatDate)


println("final Date :"+daStr)

