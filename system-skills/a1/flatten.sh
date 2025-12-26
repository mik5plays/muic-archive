#!/bin/bash

zip_file="$1"
new_directory="$2"
mkdir -p "$new_directory"
tmp_dir=$(mktemp -d /tmp/foo.XXXX)

unzip "$zip_file" -d "$tmp_dir" 
find "$tmp_dir" -type f -exec cp {} "$new_directory" \;
rm -r "$tmp_dir"
