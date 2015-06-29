#!/bin/sh

TABLES="PAT PMT MGT STT RRT TVCT CVCT EIT ETT"

cd Generated

for files in $TABLES; do
	files+=_section.fl
	flavorc -gj -t -oj flavor.Generated ../FormalDefs/$files
	flavorc -gx ../FormalDefs/$files
done

