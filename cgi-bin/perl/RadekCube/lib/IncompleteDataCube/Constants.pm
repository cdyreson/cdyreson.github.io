package IncompleteDataCube::Constants;

=head1 NAME IncompleteDataCube::Constants

EDIT THIS TO CONFIGURE THE CUBE.
The global constants in the cube.  

For more information on the cube see the 
<a href="IncompleteDataCube/Overview.html">Overview</a>.

Copyright &copy 1997 Curtis E. Dyreson. All rights reserved.
Please be aware of the 
<a href="IncompleteDataCube/License.html">license</a>
and 
<a href="IncompleteDataCube/License.html">version</a>.

=head1 DESCRIPTION

A list of global available constants.  

=over 4

=item * dimensions

- The number of dimensions in the cube.

=item * dimensionNames

- An array of strings, the names of the dimensions.

=item * logFileSplitFileName

- The name of the file that contains the flex specifications to split
the log file by dimension.  It is assumed to be relative to the 
readspec subdirectory.

=item * filterFileName

- The name of the file that contains the filter specifications. It is
assumed to be relative to the readspec subdirectory.

=item * databaseName

- In what directory will the database created by the cube live?

=item * stringTableName

- The table containing strings used by L<database::Convert>.

=item * idTableName

- The table containing Ids used by L<database::Convert>.

=item * finerUnitGraphName

- The finer unit graphs.

=item * coarserUnitGraphName

- The coarser unit graphs.

=item * finerMeasureGraphName

- The finer measure graph for each dimension.

=item * coarserMeasureGraphName

- The coarser measure graph for each dimension.

=item * unitToMeasureTableName

- The table that maps units to measures.

=item * measureTableName

- The table that lists the available measures in each dimension.

=item * filterTableName

- The table that lists the filters.

=item * filterUnitTableName

- The table of filter units.

=item * filterMeasureTableName

- The table of filter measures.

=item * countTableName

- The table that contains counts for each filter.

=back

=cut

#---------------------------------------------------------------------
  #
  # Configure this to change dimension information.
  #
  $dimensions = 3;
  $dimensionNameString = "Replica Time Status";
  #$dimensionNames = [ split(" ", $dimensionString) ];
  $dimensionNames = [ split(" ", $dimensionNameString) ];

  #
  # Default configuration is for (Big) DBM files.
  #
  $databaseMode = 'BSD';
  $databaseName = "dbs";

  #
  # What is the name of the file that contains the Filter specifications?
  # This file is assumed to live relative to readspec
  # 
  $filterFileName = "Filters";
  $logFileSplitFileName = "LogFileSplit";
  $stringTableName = "strings";
  $idTableName = "ids";
  $finerUnitGraphName = "finerUnits";
  $coarserUnitGraphName = "coarserUnits";
  $finerMeasureGraphName = "finerMeasures";
  $coarserMeasureGraphName = "coarserMeasures";
  $unitToMeasureTableName = "unitToMeasure";
  $measureTableName = "measures";
  $filterTableName = "filters";
  $filterUnitTableName = "filterUnits";
  $filterMeasureTableName = "filterMeasures";
  $countTableName = "count";
#---------------------------------------------------------------------

1;
