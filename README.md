# Akka Typed Java Cluster Sharding with SpringBoot Example

This is an Akka Cluster project that includes an example of how to integrate akka typed library with springboot, 
akka actor model, akka sharding, akka cluster.

## About Akka Clustering Sharding

According to the [Akka documentation](https://doc.akka.io/docs/akka/current/cluster-sharding.html#introduction),
"*Cluster sharding is useful when you need to distribute actors across several nodes in the cluster and want to be able to interact with them using their logical identifier, but without having to care about their physical location in the cluster, which might also change over time.*"

The common usage for cluster sharding is to distribute and engage with individual actors across the cluster. Each of these distributed actors is used to handle messages that are intended for a specific entity. Each entity represents a thing, such as a bank account or a shopping cart. Entities each have a unique identifier, such as an account or shopping cart identifier.

In this project, we'll demonstrate how to create akka cluster and sharding with spring at server startup. Shared actor will be invoked basis on their entity id.

## Installation

~~~bash
git clone https://github.com/sachin2singh/akka-cluster-sharding.git
cd akka-cluster-sharding
mvn clean install
~~~
## Run application
~~~bash
Open SpringBoot startup class and run: AkkaClusterDemoApplication
Run from command line : java path/to/your/jarfile.jar com.akka.example.AkkaClusterDemoApplication
~~~
