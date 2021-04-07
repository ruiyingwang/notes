#### Fluentd
* https://docs.fluentd.org/deployment/plugin-management#if-using-td-agent-use-usr-sbin-td-agent-gem
* /Library/LaunchDaemons/td-agent.plist
* /var/log/td-agent/td-agent.log
* /etc/td-agent/td-agent.conf
* localhost:8888
```
curl -X POST -d 'json={"json":"message2020.12.13 11:30"}' http://localhost:8888/debug.test
tail -n 1 /var/log/td-agent/td-agent.log
```

#### ElasticSearch
* https://www.elastic.co/downloads/elasticsearch
* http://localhost:9200
```
bin/elasticsearch
curl http://localhost:9200
```

#### Kibana
* https://www.elastic.co/downloads/kibana
* config/kibana.yml
* http://localhost:5601
```
bin/kibana 
```

# GET /<logstash-{now/d-2d}>,<logstash-{now/d-1d}>,<logstash-{now/d}>/_search
curl -X GET "localhost:9200/%3Clogstash-%7Bnow%2Fd-2d%7D%3E%2C%3Clogstash-%7Bnow%2Fd-1d%7D%3E%2C%3Clogstash-%7Bnow%2Fd%7D%3E/_search?pretty" -H 'Content-Type: application/json' -d'
{
  "query" : {
    "match": {
      "query": "cpu"
    }
  }
}
'
