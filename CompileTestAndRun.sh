#!/bin/bash

set -e
cd src/Congestion
find . -name '*.class' -delete
javac $(find . -name '*.java' -not -type d)
cd ..
set +e

TestsFail="NO"  

echo "Beginning unit tests:"

java -ea congestion.PacketTest
if ! [ $? -eq 0 ]
then
	TestsFail="YES"
fi

java -ea congestion.CongTimerTest 
if ! [ $? -eq 0 ]
then
	TestsFail="YES"
fi

java -ea congestion.CongReceiverTest
if ! [ $? -eq 0 ]
then
	TestsFail="YES"
fi

java -ea congestion.CongBufferTest
if ! [ $? -eq 0 ]
then
	TestsFail="YES"
fi

java -ea congestion.CongSenderTest
if ! [ $? -eq 0 ]
then
	TestsFail="YES"
fi

echo '============'
if [ $TestsFail = "YES" ]
then 
	echo 'Tests Fail'
	exit 1
else 
	echo 'Tests Pass'
	exit 0
fi
cd ..
