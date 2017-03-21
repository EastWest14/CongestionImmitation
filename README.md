# Congestion Immitation
#### DESCRIPTION:

A simple model of network congestion on a router. Packets arrive at a buffer randomly, at a given average rate. Router queues the packets and forwards them one by one.
As a result of the run we get statistical distribution - number of sent and received packets, maximum and average delay, queue size, etc.

#### INSTRUCTIONS:

`./CompileRunAndTest.sh $1 $2 $3`
Runs unit tests, a full system test and launches the immitation. Takes in 3 integer parameters: number of "ticks" in an immitation, number of sent packets and packet processing time on the buffer in ticks. Requirments: java, javac, bash.
Program provides a very simplistic immitation of congestion on an internet router.

#### NOTE:

Time is represented as a series of discrete "_ticks_".
