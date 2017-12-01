#!/usr/bin/env bash
set -eu
./gradlew --daemon --parallel clean build test 2>&1

echo "*** Verify Validation built and tested successfully ***"
