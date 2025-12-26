#!/bin/bash

file="$1"

while IFS= read -r line; do 
	firstname=$(echo $line | cut -d',' -f1)
	lastname=$(echo $line | cut -d',' -f2)
	dob=$(echo $line | cut -d',' -f3)

	yob="${dob:0:4}"
	age=$((2025 - $yob))

	if [ "$age" -ge 18 ]; then
		echo "Dear Mr/Mrs $lastname, $firstname"
	fi
done < "$file"


