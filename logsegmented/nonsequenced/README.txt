To create the schema use
  schema.sql
  indexes.sql
To generate the data use the perl program 
  genDBNS.pl
as shown in 
  doitNS.bat
That batch file generated the test code files
  ns10.txt through ns50.txt
Size estimate of the data is in
  size.sql
The code for the queries is
  overlaps.sql - period timestamped overlaps
  nsoverlaps.sql - log-segmented timestamped overlaps
  contains.sql
  nscontains.sql
  start.sql
  nsstart.sql