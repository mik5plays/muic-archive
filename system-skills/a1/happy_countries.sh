#!/bin/bash

tmp_dir=$(mktemp -d /tmp/dump.XXXX)
cd "$tmp_dir"
touch tmpraw
 
curl https://en.wikipedia.org/wiki/World_Happiness_Report?action=raw | sed -n '/===2018 report===/,/{{collapse bottom}}/p' > tmpraw
grep -Eo '\{flag\|([^}]*)\}' tmpraw | sed 's/{flag|//;s/}//' | head 

rm -rf "$tmp_dir"  # done with everything
