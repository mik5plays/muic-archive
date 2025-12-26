#!/bin/bash

directory=$1
size=$(du -sk "$directory" | cut -f1) 
#using kb because <1MB directories default to just 1, assume 1MB=1024KB
if [ "$size" -lt 1024 ]; then
	echo "Low"
elif [ "$size" -gt 10240 ]; then
	echo "High"
else 
	echo "Medium"
fi

