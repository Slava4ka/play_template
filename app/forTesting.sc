var temp1 :Seq[Int] = Seq(1,2,3,4,5)
temp1 = temp1.filterNot(_ == 5)

val a = temp1.mkString("\\n")