Camel Sample Project for Blueprint (OSGi)
=========================================

To build this project for Karaf container
=========================================
    mvn clean install -DskipTests -Pkaraf

To run and test the project, you can execute the following Maven goal

    mvn camel:run -Pkaraf

Start Apache Karaf (such as version 4.0.4)

    bin/karaf

Install the example feature from the Karaf shell :

    feature:repo-add mvn:com.hq.poc/camel-blueprint-cxfrs-activity/1.0.0-SNAPSHOT/xml/features

Install the example

    feature:install camel-blueprint-cxfrs-activity

Wait a while, and if all is okay one should see a Camel application if one type

    camel:context-list

List the Camel routes with:

    camel:route-list

From a command line, use curl to retrieve activities using:

    curl -i --header "Accept: application/json" http://localhost:8080/activities

More details of the Camel application and Karaf using the hawtio web console with use of `karaf/karaf` as username and password to login

    http://localhost:8181/hawtio

To build this project for Fuse container
========================================
    mvn clean install -DskipTests -Pfuse

To run and test the project, you can execute the following Maven goal

    mvn camel:run -Pfuse

To deploy this project into a local running fabric

    mvn fabric8:deploy -DskipTests -Pfuse

Login to fuse as admin, create a new container with profile horoquartz/cxfrs.sample

From a command line, use curl to retrieve activities using:

    curl -i --header "Accept: application/json" http://localhost:8080/activities

