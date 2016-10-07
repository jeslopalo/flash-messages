#!/bin/bash

echo "Working directory: [`pwd`]"

echo "Generating cobertura reports to be submited to coverall service"
mvn clean cobertura:cobertura coveralls:report -P coveralls $1
