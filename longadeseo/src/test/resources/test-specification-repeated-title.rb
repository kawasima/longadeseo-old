import "gridexcel.parser.Cell"
import "gridexcel.parser.CellNotFoundException"

sheet = @book.sheet("テスト仕様全体")

begin
	while true
	  sheet.tableLabel = Cell.pattern("^【.*】$")

    puts "==========================="	  
	  puts sheet.tableHeader.label
	  puts "---------------------------"
	  sheet.rows.each{|row|
	    puts row.cell(0)
	    puts row.cell("操作")
	  }
	end
rescue CellNotFoundException => ex
end
