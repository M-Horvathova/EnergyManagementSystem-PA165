#!/bin/bash
rm -rf src/main/webapp/static
mv build/index.html src/main/webapp/index.jsp
mv build/static/ src/main/webapp/static