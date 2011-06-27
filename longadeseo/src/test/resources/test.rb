require 'erb'

class ColoredCellStop
  include Java::gridexcel.parser.ParseCondition

  def stop(row)
    /^[0-9]+$/ !~ row.cell("No").to_s
  end
end

sheet = @book.sheet("画面仕様書")
sheet.tableLabel = "画面遷移"

sheet.rows({
  'exceptGrayout'=>true,
  'stopCondition'=>ColoredCellStop.new
}).each{|row|
  puts row.cell("遷移元")
  puts row.cell("No")
}

