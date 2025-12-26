#!/bin/bash

extension=$1
directory=$2

for FILE in "$directory"/*; do
	current_extension=$(echo $FILE | cut -d'.' -f2)
	current_name=$(echo $FILE | cut -d'.' -f1)
	if [[ "$current_extension" == "$extension" ]]; then
		mv "$FILE" "$current_name"
	fi
done


