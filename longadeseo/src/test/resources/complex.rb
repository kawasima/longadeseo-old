import "gridexcel.parser.Book"

sheet = @book.sheet("テスト仕様全体")
sheet.tableLabel("【管理画面　管理者管理】")
out_book = Book.create("out.xlsx")
scanner = out_book.sheet("Your Test").rowScanner
sheet.rows.each{|row|
  scanner.cell(0).value = row.cell(0)
  scanner.cell(1, row.cell("操作")).nextRow
}
out_book.save();
