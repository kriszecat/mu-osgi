# Installation instructions for activity module
Install at least Karaf 4.0.0

## Build activity-parent module 
    cd activity-parent
    mvn clean install -DskipTests
    
## Build activity applicative modules 
    cd activity-features
    mvn clean install


## Copy DataSource config 
```
cat <WORKSPACE_LOCATION>/activity-model/org.ops4j.datasource-activity.cfg | tac -f etc/org.ops4j.datasource-activity.cfg
```

cf. [https://aries.apache.org/modules/jpaproject.html](https://aries.apache.org/modules/jpaproject.html "Aries JPA")

## Install test features
    feature:repo-add mvn:com.hq.poc/activity-features/1.0.0-SNAPSHOT/xml/features
    feature:install activity-tst

NB: 
<br>- Persistence bundles are installed along with test features 
<br>- Hawtio console is installed along with test features
 

## Checking
You should see a service for the persistence unit "activity" :
```
service:list EntityManagerFactory
```

You should see a service provided by the activity-persistence bundle :
```
service:list ActivityDaoService
```

If you open the following url in a web browser you should see a list with one activity : 

```
http://localhost:8181/activities/test
```

Now add an activity :

```
http://localhost:8181/activities/test?add&name=dev-osgi&duration=0.75
```

... and check it is added to the list

```
http://localhost:8181/activities/test?id=1
```

## Install application features
First get test bundle id

```
list |grep -i test
```

And uninstall bundle whose description is "Horoquartz :: PoC :: Activity :: Test"

```
uninstall <id>
```

Install application features

```
feature:install activity-features
```

## Use REST API
    curl http://localhost:8080/activities/
    curl -i -X POST -H "Content-Type: application/json" -d '{"name":"poc-hq-dev", "duration":7.25, "employeeId":111007}' http://localhost:8080/activities/
    curl -i -X POST -H "Content-Type: application/json" -d '{"name":"poc-hq-tst", "duration":0.80, "employeeId":111007}' http://localhost:8080/activities/
    curl -i -X PUT -H "Content-Type: application/json" -d '{"fkey":"2", "name":"poc-hq-tst", "duration":1.25, "employeeId":111007}' http://localhost:8080/activities/
    curl -i -X DELETE --header "Accept: application/json" http://localhost:8080/activities/2