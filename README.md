# Guide utilisateur #
## Création du cluster ##
Les features cellar et cellar-http-balancer sont configurés pour être installés dans chaque nouvelle instance (cf. etc/org.apache.karaf.features.cfg).

Lancement du serveur karaf et ouverture d'une console cliente

    $KARAF_ROOT/bin/karaf 

Création de deux instances filles

    karaf@root> instance:create karaf#1
    karaf@root> instance:start karaf#1
    karaf@root> instance:create karaf#2
    karaf@root> instance:start karaf#2
    karaf@root> instance:list

    karaf@root> cluster:node-list
    karaf@root> cluster:group-list
    
## Mise en place de la console de supervision sur tous les noeuds du cluster ##
Installation du serveur HTTP sur tous les noeuds du cluster

    karaf@root> cluster:feature-install default http
    karaf@root> cluster:feature-install default http-whiteboard

Connection aux instances karaf#1 et karaf#1 pour décaler les ports d'écoute du serveur HTTP

    karaf@root> instance:connect karaf#1 config:property-set -p org.ops4j.pax.web  org.osgi.service.http.port 8182
    karaf@root> instance:connect karaf#2 config:property-set -p org.ops4j.pax.web org.osgi.service.http.port 8183    

NB: on peut aussi ouvrir une session cliente sur un noeud du cluster pour y exécuter des commandes directement : 

    $KARAF_ROOT/bin/client -a 8102 -h localhost
    karaf@karaf#1> config:property-set -p org.ops4j.pax.web org.osgi.service.http.port 8182

Installation de l'agent Jolokia sur les noeuds du cluster en dehors du noeud root
    
    karaf@karaf#1> bundle:install -s mvn:org.jolokia/jolokia-osgi/1.3.3
    karaf@karaf#2> bundle:install -s mvn:org.jolokia/jolokia-osgi/1.3.3

Installation de la console hawtio et des plugins utiles pour karaf 

    karaf@root> feature:install hawtio

On a maintenant accès aux 3 instances via la console hawtio [http://localhost:8181/hawtio/](http://localhost:8181/hawtio/ "Console Hawtio")

    karaf@karaf#1> feature:repo-add mvn:com.hq.poc.tasklist.ds/tasklist/1.0.0-SNAPSHOT/xml/features
    karaf@karaf#1> feature:install example-tasklist-ds
    karaf@karaf#2> feature:repo-add mvn:com.hq.poc.tasklist.ds/tasklist/1.0.0-SNAPSHOT/xml/features
    karaf@karaf#2> feature:install example-tasklist-ds



