#!/bin/bash

echo "Generating cobertura reports to be submited to coverall service"
mvn test cobertura:cobertura coveralls:cobertura -P coveralls
